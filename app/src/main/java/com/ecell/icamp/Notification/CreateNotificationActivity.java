package com.ecell.icamp.Notification;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Random;

public class CreateNotificationActivity extends AppCompatActivity {

    private EditText title, message;
    private Button buttom2;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private MongoCredential credential;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notification_main);

        title = (EditText)findViewById(R.id.title);
        message = (EditText)findViewById(R.id.message);
        buttom2 = (Button)findViewById(R.id.button2);
        buttom2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mongo = new MongoClient("ecell.org.in", 27017);
                new connection().execute();
            }
        });
    }

    public class connection extends AsyncTask<String , Void, String> {

        @Override
        protected String doInBackground(String... strings) {
            try {
                credential = MongoCredential.createCredential("admin", "esummit_18", "techies".toCharArray());
                database = mongo.getDatabase("esummit_18");
                collection = database.getCollection("notification");

                Document document = new Document()
                        .append("target", "all")
                        .append("title", title.getText().toString())
                        .append("message", message.getText().toString());

                collection.insertOne(document);
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