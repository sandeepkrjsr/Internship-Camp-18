package com.ecell.icamp.Main;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.TextView;

import com.ecell.icamp.R;

/**
 * Created by 1505560 on 03-Jan-18.
 */

public class Activity_GotoDashboard extends Activity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gotodashboard);

        TextView allset = (TextView) findViewById(R.id.allset);
        allset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getBaseContext(), Activity_Landing.class);
                startActivity(intent);
                finish();
            }
        });
    }
}
