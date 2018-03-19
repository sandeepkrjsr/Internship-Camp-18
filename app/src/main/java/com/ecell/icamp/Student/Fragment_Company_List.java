package com.ecell.icamp.Student;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecell.icamp.Company.Company_Dashboard;
import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Fragment_Company_List extends Fragment {

    private String id, name, skillset, location, duration, stipend;
    private List<String> li_id, li_name, li_skillset, li_location, li_duration, li_stipend;

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private RecyclerView.LayoutManager layoutManager;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_recycler, container,false);

        recyclerView = (RecyclerView)view.findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(view.getContext());
        recyclerView.setLayoutManager(layoutManager);

        new connection().execute();

        return view;
    }

    public class connection extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                collection = database.getCollection("Company");

                List<Document> lists = (List<Document>)collection.find(eq("status","0")).into(new ArrayList<Document>());

                li_id = new ArrayList<>();
                li_name = new ArrayList<>();
                li_skillset = new ArrayList<>();
                li_location = new ArrayList<>();
                li_duration = new ArrayList<>();
                li_stipend = new ArrayList<>();

                for(Document document : lists){

                    JSONObject jsonObject = new JSONObject(document.toJson());

                    id = jsonObject.getString("id");
                    name = jsonObject.getString("name");
                    skillset = jsonObject.getString("skillset");
                    location = jsonObject.getString("location");
                    duration = jsonObject.getString("duration");
                    stipend = jsonObject.getString("stipend");

                    li_id.add(id);
                    li_name.add(name);
                    li_skillset.add(skillset);
                    li_location.add(location);
                    li_duration.add(duration);
                    li_stipend.add(stipend);
                }
                adapter = new Fragment_Company_Adapter(li_id, li_name, li_skillset, li_location, li_duration, li_stipend);
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
            recyclerView.setAdapter(adapter);
        }
    }
}
