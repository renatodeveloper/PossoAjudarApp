package com.possoajudar.app.application.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.possoajudar.app.R;
import com.possoajudar.app.infrastructure.helper.BaseActivity;

public class Report extends BaseActivity {
    private static final String TAG = "MainActivity";

    private TextView thedate;
    private ImageView viewGoCalendar;

    @Override
    protected int getContentView() {
        return R.layout.activity_report;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        thedate = (TextView) findViewById(R.id.date);
        viewGoCalendar = (ImageView) findViewById(R.id.viewGoCalendar);

        Intent incoming = getIntent();
        String date = incoming.getStringExtra("date");
        thedate.setText(date);

        viewGoCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Report.this,CalendarActivity.class);
                startActivity(intent);
            }
        });
    }
}
