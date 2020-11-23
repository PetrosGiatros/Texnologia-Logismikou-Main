package com.example.texn_logism_login;

import android.app.ApplicationExitInfo;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;


public class AdminActivity extends AppCompatActivity {

    private Button hireFireButton;
    private Button exitButton;



    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);

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
