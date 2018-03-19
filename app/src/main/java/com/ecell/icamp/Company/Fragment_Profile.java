package com.ecell.icamp.Company;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ecell.icamp.Main.Activity_Splash;
import com.ecell.icamp.R;
import com.google.android.flexbox.FlexboxLayout;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;

import static com.ecell.icamp.Company.Company_Dashboard.user;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Fragment_Profile extends Fragment {

    private FlexboxLayout flexboxLayout;
    private TextView co_requirements, logout;

    private List<String> li_requirements;
    private boolean[] itemsChecked;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_company_profile, container,false);

        flexboxLayout = (FlexboxLayout)view.findViewById(R.id.flexboxlayout);
        co_requirements = (TextView)view.findViewById(R.id.co_requirements);
        logout = (TextView)view.findViewById(R.id.logout);

        li_requirements = new ArrayList<>();

        co_requirements.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                itemsChecked = new boolean[getResources().getStringArray(R.array.skills).length];
                CharSequence choices[] = getResources().getStringArray(R.array.skills);
                Select_Option("Select Skills", choices);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Activity_Splash.logout(getActivity());
            }
        });

        return view;
    }

    private void Select_Option(final String header, final CharSequence[] choices) {
        for(int i = 0; i < itemsChecked.length; i++){
            itemsChecked[i] = false;
            if(li_requirements.contains(choices[i]))
                itemsChecked[i] = true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(header);
        builder.setMultiChoiceItems(choices, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    itemsChecked[which] = false;
                    li_requirements.add(choices[which].toString());
                }else {
                    itemsChecked[which] = false;
                    li_requirements.remove(choices[which]);
                }
            }
        });
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new connection().execute();

                dialog.dismiss();
                co_requirements.setVisibility(View.GONE);
                Display_Keywords();
            }
        });
        builder.create().show();
    }

    private void Display_Keywords() {
        flexboxLayout.removeAllViews();
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 8, 8, 8);
        for (int i=0; i<li_requirements.size(); i++){
            final TextView textView = new TextView(getContext());
            textView.setLayoutParams(layoutParams);
            textView.setText(li_requirements.get(i));
            textView.setTextColor(Color.GRAY);
            textView.setPadding(20,12,20,12);
            textView.setBackgroundResource(R.drawable.bg_curve);
            flexboxLayout.addView(textView);
        }
        if (li_requirements.size() == 0)
            co_requirements.setVisibility(View.VISIBLE);
        else {
            final TextView adskill = new TextView(getContext());
            adskill.setLayoutParams(layoutParams);
            adskill.setText("+ Add More Skills");
            adskill.setTextColor(Color.GRAY);
            adskill.setPadding(20,12,20,12);
            adskill.setBackgroundResource(R.drawable.bg_curve);
            flexboxLayout.addView(adskill);
            adskill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemsChecked = new boolean[getResources().getStringArray(R.array.skills).length];
                    CharSequence choices[] = getResources().getStringArray(R.array.skills);
                    Select_Option("Select Skills", choices);
                }
            });
        }
    }

    public class connection extends AsyncTask<String , Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                collection = database.getCollection(user);

                collection.updateOne(eq("id", Company_Dashboard.id), Updates.set("requirements", li_requirements));
            }
            catch (Exception e){
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}