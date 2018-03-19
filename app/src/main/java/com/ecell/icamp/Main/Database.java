package com.ecell.icamp.Main;

import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.RadioButton;

import com.ecell.icamp.Student.Activity_Payment;
import com.ecell.icamp.Student.Student_Registration;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Random;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by 1505560 on 16-Jan-18.
 */

public class Database {

    private static MongoClient mongo;
    private static MongoDatabase database;
    private MongoCollection<Document> collection;

    public static class connection extends AsyncTask<String , Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "admin", "techies".toCharArray());
                database = mongo.getDatabase("admin");

                Log.d("check","check");
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
