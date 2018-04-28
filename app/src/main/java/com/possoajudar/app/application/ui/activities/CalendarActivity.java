package com.possoajudar.app.application.ui.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.CalendarView;

import com.possoajudar.app.R;

public class CalendarActivity extends AppCompatActivity {

    private  static final String TAG = "CalendarActivity";
    private CalendarView mCalendarView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_layout);
        mCalendarView = (CalendarView) findViewById(R.id.simpleCalendarView);
        mCalendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override
            public void onSelectedDayChange(CalendarView calendarView, int year, int month, int dayOfMonth) {
                try{
                    String date = "";
                    String mes  = "";
                    if(month == 0 || month == 1 || month == 2 || month == 3 || month == 4 || month == 5 || month == 6 || month == 7 || month == 8 ){
                        mes = "0" + (month+1);
                        date = dayOfMonth+ "/"+ mes+ "/"+year;
                    }else{
                        date = dayOfMonth+ "/"+(month+1)+ "/"+year;
                    }

                    Log.d(TAG, "onSelectedDayChange: yyyy/mm/dd:" + date);
                    Intent intent = new Intent(CalendarActivity.this,Report.class);
                    intent.putExtra("date",date);
                    startActivity(intent);
                    finish();
                }catch (Exception e){
                    e.getMessage().toString();
                }
            }
        });
    }
}