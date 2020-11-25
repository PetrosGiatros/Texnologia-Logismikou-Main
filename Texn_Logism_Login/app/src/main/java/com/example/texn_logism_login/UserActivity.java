package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class UserActivity extends AppCompatActivity {

    private Button exitButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);
        exitButton= (Button)findViewById(R.id.buttonExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });
    private Button button_view2;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);
        button_view2 = (Button) findViewById(R.id.button_view2);
        button_view2.setOnClickListener(new View.OnClickListener() {
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

