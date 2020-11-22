package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;



public class HireFireActivity extends AppCompatActivity {
    private Button hireButton,backHireButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hirefire_form);
        backHireButton= (Button)findViewById(R.id.buttonBackHireFire);
        hireButton= (Button)findViewById(R.id.buttonHire);
        hireButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(HireFireActivity.this, HireActivity.class);
                startActivity(intent);

            }
        });


        backHireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(HireFireActivity.this, AdminActivity.class);
                startActivity(intent);

            }
        });


    }
}
