package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

public class CalendarActivity extends AppCompatActivity {
   // private Button button_Show;
    private static final String TAG = "CalendarActivity";
    private CalendarView calendarView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_form);
        //button_Show = (Button) findViewById(R.id.button_Show);
        //button_Show.setOnClickListener(new View.OnClickListener() {
           // @Override
           // public void onClick(View v) {
           //     openSchedule_form();
            //}

        calendarView= (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            @Override

            public void onSelectedDayChange(CalendarView calendarView ,int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year; // month+1 γιατι μετραει τους μηνες απο 0 δλδ ιανουαριος=0
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy: " +date);

                Intent intent = new Intent(CalendarActivity.this, ScheduleActivity.class);
                intent.putExtra("date", date);
                startActivity(intent);
            }
        });
    }


    public  void openSchedule_form() {

        Intent intent =new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}

