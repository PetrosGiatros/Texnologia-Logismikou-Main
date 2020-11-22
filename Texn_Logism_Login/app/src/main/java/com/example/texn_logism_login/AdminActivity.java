package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;


public class AdminActivity extends AppCompatActivity {
     private Button button_view;


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);
        button_view = (Button) findViewById(R.id.button_view);
        button_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openCalendar_form();
            }
        });

    }
    public  void openCalendar_form() {

        Intent intent =new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
