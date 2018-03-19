package com.ecell.icamp.Student;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;

import com.ecell.icamp.Main.Activity_GotoDashboard;
import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Updates;

import org.bson.Document;

import static com.ecell.icamp.Student.Student_Dashboard.user;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by 1505560 on 03-Jan-18.
 */

public class Activity_Payment extends Activity {

    private String id;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_payment);

        Bundle bundle = getIntent().getExtras();
        id = bundle.getString("id");

        ImageView paytm = (ImageView) findViewById(R.id.paytm);
        paytm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new connection().execute();

                Intent intent = new Intent(getBaseContext(), Activity_GotoDashboard.class);
                startActivity(intent);
                finish();
            }
        });
    }

    public class connection extends AsyncTask<String , Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                collection = database.getCollection(user);

                collection.updateOne(eq("id", id), Updates.set("paid", "yes"));
                collection.updateOne(eq("id", id), Updates.set("resume", ""));
                collection.updateOne(eq("id", id), Updates.set("skillset", ""));
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
