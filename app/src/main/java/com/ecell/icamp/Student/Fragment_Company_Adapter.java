package com.ecell.icamp.Student;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Company.Fragment_Student_Adapter;
import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import java.util.List;

import static com.ecell.icamp.Student.Student_Dashboard.user;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.size;

/**
 * Created by 1505560 on 09-Dec-17.
 */

public class Fragment_Company_Adapter extends RecyclerView.Adapter<Fragment_Company_Adapter.ViewHolder> {

    private LinearLayout co_card;
    private TextView  co_name, co_skillset, co_location, co_duration, co_stipend;
    private List<String> li_id, li_name, li_skillset, li_location, li_duration, li_stipend;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> student, company;

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ViewHolder(View view) {
            super(view);
            co_card = (LinearLayout)view.findViewById(R.id.co_card);
            co_name = (TextView)view.findViewById(R.id.co_name);
            co_skillset = (TextView)view.findViewById(R.id.co_requirements);
            co_location = (TextView) view.findViewById(R.id.co_location);
            co_duration = (TextView)view.findViewById(R.id.co_duration);
            co_stipend = (TextView)view.findViewById(R.id.co_stipend);
        }
    }

    public Fragment_Company_Adapter(List<String> li_id, List<String> li_name, List<String> li_skillset, List<String> li_location, List<String>li_duration, List<String>li_stipend) {
        this.li_id = li_id;
        this.li_name = li_name;
        this.li_skillset = li_skillset;
        this.li_location = li_location;
        this.li_duration = li_duration;
        this.li_stipend = li_stipend;
    }

    @Override
    public Fragment_Company_Adapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.card_company, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        co_name.setText(li_name.get(position));
        co_skillset.setText(li_skillset.get(position));
        co_location.setText("   " + li_location.get(position));
        co_duration.setText("   " + li_duration.get(position) + " month");
        co_stipend.setText("   " + li_stipend.get(position) + " pm");

        co_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                builder.setTitle("Do you want to apply for this company?");
                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        new connection(li_id.get(position)).execute();
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        /*co_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dashboard_Admin.fragmentChange(3);
            }
        });*/
    }

    @Override
    public int getItemCount() {
        return li_id.size();
       // return 3;
    }

    public class connection extends AsyncTask<String , Void, String> {

        String id;

        public connection(String id) {
            this.id = id;
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                student = database.getCollection(user);
                company = database.getCollection("Company");

                Document document = student.find(eq("id", Student_Dashboard.id)).first();
                List<Document> applied = (List<Document>)document.get("applied");

                if (!applied.contains(id)){
                    if (applied.size() < 3 || applied.contains(null)){
                        student.updateOne(eq("id", Student_Dashboard.id), Updates.push("applied", id));
                        company.updateOne(eq("id", id), Updates.push("applicants", "01"));
                    }
                }
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
