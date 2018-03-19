package com.ecell.icamp.Student;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
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

import com.ecell.icamp.Main.Activity_Landing;
import com.ecell.icamp.Main.Database;
import com.ecell.icamp.R;
import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;

import java.util.Random;

import static com.ecell.icamp.Student.Student_Dashboard.user;

/**
 * Created by 1505560 on 04-Jan-18.
 */

public class Student_Registration extends Activity{

    private LinearLayout st_personal, st_professional;
    private EditText st_name, st_email, st_mobile, st_hometown, st_college, st_roll, st_password;
    private TextView st_branch, st_year, st_batch;
    private RadioGroup st_gender;

    private int count_next;

    private MongoClient mongo;
    private MongoDatabase database;
    private MongoCollection<Document> collection;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_registration);

        st_personal = (LinearLayout)findViewById(R.id.st_personal);
        st_professional = (LinearLayout)findViewById(R.id.st_professional);
        st_name = (EditText) findViewById(R.id.st_name);
        st_email = (EditText) findViewById(R.id.st_email);
        st_mobile = (EditText) findViewById(R.id.st_mobile);
        st_hometown = (EditText) findViewById(R.id.st_hometown);
        st_gender = (RadioGroup)findViewById(R.id.st_gender);
        st_college = (EditText) findViewById(R.id.st_college);
        st_roll = (EditText) findViewById(R.id.st_roll);
        st_branch = (TextView) findViewById(R.id.st_branch);
        st_year = (TextView) findViewById(R.id.st_year);
        st_batch = (TextView) findViewById(R.id.st_batch);
        st_password = (EditText)findViewById(R.id.st_password);

        st_branch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence choices[] = getResources().getStringArray(R.array.branch);
                Select_Option("Select Branch", choices);
            }
        });

        st_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence choices[] = getResources().getStringArray(R.array.year);
                Select_Option("Select Year", choices);
            }
        });

        st_batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CharSequence choices[] = getResources().getStringArray(R.array.batch);
                Select_Option("Select Batch", choices);
            }
        });

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
                }
                count_next++;
            }
        });
    }

    private void Select_Option(final String header, final CharSequence[] choices) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(header);
        builder.setItems(choices, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (header.compareTo("Select Branch")==0){
                    st_branch.setText(choices[which]);
                }else if (header.compareTo("Select Year")==0){
                    st_year.setText(choices[which]);
                }else if (header.compareTo("Select Batch")==0){
                    st_batch.setText(choices[which]);
                }
            }
        });
        builder.create().show();
    }

    public class connection extends AsyncTask<String , Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            try {
                mongo = new MongoClient("13.127.214.190", 27017);
                MongoCredential.createCredential("techies", "admin", "ZT1OmEHPDO*#uq".toCharArray());
                database = mongo.getDatabase("admin");
                collection = database.getCollection(Activity_Landing.user);

                int id_gender = st_gender.getCheckedRadioButtonId();
                RadioButton gender = (RadioButton)findViewById(id_gender);

                Long tsLong = System.currentTimeMillis()/1000;
                String ts = tsLong.toString();

                Document document = new Document("id", ts+""+(new Random().nextInt(8999)+1000))
                        .append("name", st_name.getText().toString())
                        .append("email", st_email.getText().toString())
                        .append("mobile", st_mobile.getText().toString())
                        .append("hometown", st_hometown.getText().toString())
                        .append("gender", gender.getText().toString())
                        .append("college", st_college.getText().toString())
                        .append("roll", st_roll.getText().toString())
                        .append("branch", st_branch.getText().toString())
                        .append("year", st_year.getText().toString())
                        .append("batch", st_batch.getText().toString())
                        .append("paid", "no")
                        .append("password", st_password.getText().toString());
                collection.insertOne(document);

                Intent intent = new Intent(getBaseContext(), Activity_Payment.class);
                intent.putExtra("id", ts+""+(new Random().nextInt(8999)+1000));
                startActivity(intent);
                finish();
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