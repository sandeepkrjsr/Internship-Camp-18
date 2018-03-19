package com.ecell.icamp.Company;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Main.Activity_GotoDashboard;
import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static com.ecell.icamp.Company.Company_Dashboard.user;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.exists;

/**
 * Created by 1505560 on 04-Jan-18.
 */

public class Company_Registration extends Activity{

    private LinearLayout st_personal, st_professional;

    private int count_next;
    private RadioGroup co_duration;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;
    private EditText co_name, co_location, co_stipend,co_email,co_password,co_phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_company_registration);

        st_personal = (LinearLayout)findViewById(R.id.st_personal);
        st_professional = (LinearLayout)findViewById(R.id.st_professional);

        co_email = (EditText)findViewById(R.id.co_email);
        co_phone = (EditText)findViewById(R.id.co_phone);
        co_password = (EditText)findViewById(R.id.co_password);
        co_duration = (RadioGroup)findViewById(R.id.co_duration);
        co_name = (EditText)findViewById(R.id.co_name);
        co_location = (EditText)findViewById(R.id.co_location);

        co_stipend = (EditText)findViewById(R.id.co_stipend);


        count_next = 0;

        TextView next = (TextView) findViewById(R.id.next);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (count_next%2==0){
                    st_personal.setVisibility(View.GONE);
                    st_professional.setVisibility(View.VISIBLE);
                }else {
                    new connection().execute();

                    Intent intent = new Intent(getBaseContext(), Activity_GotoDashboard.class);
                    startActivity(intent);
                    finish();
                }
                count_next++;
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

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                int id_gender = co_duration.getCheckedRadioButtonId();
                RadioButton duration = (RadioButton)findViewById(id_gender);

                Document document = new Document("id", ts+""+(new Random().nextInt(8999)+1000))
                        .append("email", co_email.getText().toString())
                        .append("status",0)
                        .append("phone", co_phone.getText().toString())
                        .append("password", co_password.getText().toString())
                        .append("name", co_name.getText().toString())
                        .append("location", co_location.getText().toString())
                        .append("stipend", co_stipend.getText().toString())
                        .append("duration", duration.getText().toString())
                        .append("status", "0")
                        .append("applicants", "")
                        .append("selected", "")
                        .append("onhold", "")
                        .append("rejected", "");
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
