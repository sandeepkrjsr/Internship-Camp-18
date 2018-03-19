package com.ecell.icamp.Main;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;

import com.ecell.icamp.Company.Company_Dashboard;
import com.ecell.icamp.R;
import com.ecell.icamp.Student.Student_Dashboard;

/**
 * Created by 1505560 on 07-Jan-18.
 */

public class Activity_Splash extends Activity {

    private static Context context;

    private static SharedPreferences preferences;
    private String prefName = "MyPref";
    private static final String UID = "UID";
    private static final String USER = "USER";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        context = this.getApplicationContext();

        preferences = getSharedPreferences(prefName, MODE_PRIVATE);
        final String id = preferences.getString(UID, "UID");
        final String user = preferences.getString(USER, "USER");

        new CountDownTimer(2500, 1000) {
            public void onTick(long millisUntilFinished) {
            }
            public void onFinish() {
                nextActivity(id, user);
                finish();
            }
        }.start();
    }

    public static void saved(String id, String user, Activity activity){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UID, id);
        editor.putString(USER, user);
        editor.commit();

        nextActivity(id, user);
        activity.finish();
    }

    public static void logout(Activity activity){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(UID, "UID");
        editor.putString(USER, "USER");
        editor.commit();

        nextActivity("UID", "USER");
        activity.finish();
    }

    private static void nextActivity(String id, String user) {
        if (id != null){
            Intent intent = null;
            if (user.compareTo("Student")==0)
                intent = new Intent(context, Student_Dashboard.class);
            else if (user.compareTo("Company")==0)
                intent = new Intent(context, Company_Dashboard.class);
            else
                intent = new Intent(context, Activity_Landing.class);

            intent.putExtra("id", id);
            intent.putExtra("user", user);
            context.startActivity(intent);
        }
    }
}
