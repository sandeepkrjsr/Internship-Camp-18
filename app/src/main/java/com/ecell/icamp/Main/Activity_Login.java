package com.ecell.icamp.Main;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;

/**
 * Created by 1505560 on 03-Jan-18.
 */

public class Activity_Login extends Activity {

    private EditText email, password;

    private String user;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Bundle bundle = getIntent().getExtras();
        user = bundle.getString("user");

        email = (EditText)findViewById(R.id.email);
        password = (EditText)findViewById(R.id.password);

        final TextView student_company = (TextView)findViewById(R.id.student_company);
        student_company.setText(user);

        TextView signin = (TextView) findViewById(R.id.signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Database.connection().execute();
                new connection().execute();
            }
        });
    }

    public class connection extends AsyncTask<String , Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("ecell.org.in", 27017);
                MongoCredential.createCredential("admin", "admin", "techies".toCharArray());
                database = mongo.getDatabase("admin");

                collection = database.getCollection(user);

                Document document = collection.find(and(
                        eq("email", email.getText().toString()),
                        eq("password", password.getText().toString())
                )).first();

                Activity_Splash.saved(document.getString("id"), user, Activity_Login.this);
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
