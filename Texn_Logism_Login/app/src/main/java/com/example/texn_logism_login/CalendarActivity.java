package com.example.texn_logism_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private CalendarView calendarView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_form);


        calendarView= (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override

            public void onSelectedDayChange(CalendarView calendarView ,int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy: " +date);

                Intent intent = new Intent(CalendarActivity.this, ScheduleActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("day",day);
                intent.putExtra("year",year);
                startActivity(intent);
            }
        });
    }


    public  void openSchedule_form() {

        Intent intent =new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }







}

