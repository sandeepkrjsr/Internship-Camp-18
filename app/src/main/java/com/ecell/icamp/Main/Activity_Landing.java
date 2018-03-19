package com.ecell.icamp.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ecell.icamp.Company.Company_Registration;
import com.ecell.icamp.R;
import com.ecell.icamp.Student.Student_Dashboard;
import com.ecell.icamp.Student.Student_Registration;

/**
 * Created by 1505560 on 03-Jan-18.
 */

public class Activity_Landing extends Activity {

    public static String user;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing);

        ImageView logo = (ImageView)findViewById(R.id.logo);
        logo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Intent intent = new Intent(getBaseContext(), Student_Dashboard.class);
                //startActivity(intent);
                //Toast.makeText(getBaseContext(),"Internship Camp 2018",Toast.LENGTH_SHORT).show();
            }
        });

        final LinearLayout student_company = (LinearLayout)findViewById(R.id.student_company);
        final LinearLayout login_register = (LinearLayout)findViewById(R.id.login_register);

        TextView student = (TextView) findViewById(R.id.student);
        student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = "Student";
                student_company.setVisibility(View.GONE);
                login_register.setVisibility(View.VISIBLE);
            }
        });

        TextView company = (TextView)findViewById(R.id.company);
        company.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                user = "Company";
                student_company.setVisibility(View.GONE);
                login_register.setVisibility(View.VISIBLE);
            }
        });

        TextView login = (TextView) findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_Login.class);
                intent.putExtra("user", user);
                startActivity(intent);
                finish();
            }
        });

        TextView register = (TextView)findViewById(R.id.register);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = null;
                if (user.compareTo("Student")==0)
                    intent = new Intent(getBaseContext(), Student_Registration.class);
                else
                    intent = new Intent(getBaseContext(), Company_Registration.class);
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
