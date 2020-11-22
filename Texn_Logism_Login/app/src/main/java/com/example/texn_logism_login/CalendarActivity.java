package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class CalendarActivity extends AppCompatActivity {
    private Button button_Show;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_form);
        button_Show = (Button) findViewById(R.id.button_Show);
        button_Show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSchedule_form();
            }
        });

    }
    public  void openSchedule_form() {

        Intent intent =new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }
}

