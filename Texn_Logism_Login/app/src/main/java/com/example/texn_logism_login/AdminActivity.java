package com.example.texn_logism_login;

import android.app.ApplicationExitInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


public class AdminActivity extends AppCompatActivity {
    private Button button_view;
    private Button hireFireButton;
    private Button exitButton;



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
        hireFireButton = (Button)findViewById(R.id.buttonHireFire);
        exitButton= (Button)findViewById(R.id.buttonExit);
        hireFireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this, HireFireActivity.class);
                startActivity(intent);

            }
        });
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
    });
}}

    }
}

