package com.ecell.icamp.Main;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Niklaus on 28-Feb-17.
 */

public class Fragment_Message_List extends Fragment {

    private String id, title, message, time, target;
    private List<String> li_id, li_title, li_message, li_time, li_target;

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
                collection = database.getCollection("Message");

                List<Document> lists = (List<Document>)collection.find().into(new ArrayList<Document>());
                li_id = new ArrayList<>();
                li_title = new ArrayList<>();
                li_message = new ArrayList<>();
                li_time = new ArrayList<>();
                li_target = new ArrayList<>();

                for(Document document : lists){

                    JSONObject jsonObject = new JSONObject(document.toJson());

                    id = jsonObject.getString("id");
                    title = jsonObject.getString("title");
                    message = jsonObject.getString("message");
                    time = jsonObject.getString("time");
                    target = jsonObject.getString("target");

                    li_id.add(id);
                    li_title.add(title);
                    li_message.add(message);
                    li_time.add(time);
                    li_target.add(target);
                }
                adapter = new Fragment_Message_Adapter(li_id, li_title, li_message, li_time, li_target);
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
