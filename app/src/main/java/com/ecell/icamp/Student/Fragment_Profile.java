package com.ecell.icamp.Student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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

import static com.ecell.icamp.Student.Student_Dashboard.user;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Fragment_Profile extends Fragment {

    private FlexboxLayout flexboxLayout;
    private TextView st_skillset, logout;

    private List<String> li_skillset;
    private boolean[] itemsChecked;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection, student;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_student_profile, container,false);

        flexboxLayout = (FlexboxLayout)view.findViewById(R.id.flexboxlayout);
        st_skillset = (TextView)view.findViewById(R.id.st_skillset);
        logout = (TextView)view.findViewById(R.id.logout);

        li_skillset = new ArrayList<>();
        st_skillset.setOnClickListener(new View.OnClickListener() {
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

        //new connection().execute();

        return view;
    }

    private void Select_Option(final String header, final CharSequence[] choices) {
        for(int i = 0; i < itemsChecked.length; i++){
            itemsChecked[i] = false;
            if(li_skillset.contains(choices[i]))
                itemsChecked[i] = true;
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(header);
        builder.setMultiChoiceItems(choices, itemsChecked, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                if (isChecked){
                    itemsChecked[which] = false;
                    li_skillset.add(choices[which].toString());
                }else {
                    itemsChecked[which] = false;
                    li_skillset.remove(choices[which]);
                }
            }
        });
        builder.setPositiveButton("Apply", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                new connection().execute();

                dialog.dismiss();
                st_skillset.setVisibility(View.GONE);
                Display_Keywords();
            }
        });
        builder.create().show();
    }

    private void Display_Keywords() {
        flexboxLayout.removeAllViews();
        FlexboxLayout.LayoutParams layoutParams = new FlexboxLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins(8, 8, 8, 8);
        for (int i=0; i < li_skillset.size(); i++){
            final TextView textView = new TextView(getContext());
            textView.setLayoutParams(layoutParams);
            textView.setText(li_skillset.get(i));
            textView.setTextColor(Color.GRAY);
            textView.setPadding(20,12,20,12);
            textView.setBackgroundResource(R.drawable.bg_curve);
            flexboxLayout.addView(textView);
        }
        if (li_skillset.size() == 0)
            st_skillset.setVisibility(View.VISIBLE);
        else {
            final TextView addskills = new TextView(getContext());
            addskills.setLayoutParams(layoutParams);
            addskills.setText("+ Add More Skills");
            addskills.setTextColor(Color.GRAY);
            addskills.setPadding(20,12,20,12);
            addskills.setBackgroundResource(R.drawable.bg_curve);
            flexboxLayout.addView(addskills);
            addskills.setOnClickListener(new View.OnClickListener() {
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

                collection.updateOne(eq("id", Student_Dashboard.id), Updates.set("skillset", li_skillset));
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