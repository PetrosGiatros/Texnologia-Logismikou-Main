package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_back;
    public TextView dateView, morningEmployees , afternoonEmployees , nightEmployees;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_form);
        dateView= (TextView) findViewById(R.id.dateView);
        morningEmployees= (TextView) findViewById(R.id.morningEmployees);
        afternoonEmployees= (TextView) findViewById(R.id.afternoonEmployees);
        nightEmployees= (TextView) findViewById(R.id.nightEmployees);

        Intent incomingIntent=getIntent();
        String date= incomingIntent.getStringExtra("date");
        dateView.setText(date);
        morningEmployees.setText(" petros \n maria \n sia \n kostas");
        afternoonEmployees.setText(" mhtsos\n andreas\n");
        nightEmployees.setText(" dimitris \n giannis\n");


        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {


                    Intent intent =new Intent(ScheduleActivity.this, CalendarActivity.class);
                    startActivity(intent);
            }

    });







}
}
