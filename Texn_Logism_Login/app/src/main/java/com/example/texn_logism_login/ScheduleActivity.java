package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Date;

import static java.lang.Integer.*;

public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_back;
    public TextView dateView, morningEmployees , afternoonEmployees , nightEmployees;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_form);
        Bundle bundle = getIntent().getExtras();



        String morningEmp=" petros \n maria \n sia \n kostas", morningEmp2=" dimitris \n andreas \n mhtsos \n giannis";
        String afternoonEmp= " mhtsos\n andreas\n", afternoonEmp2=" petros \n sia\n";
        String nightEmp= " dimitris \n giannis\n",nightEmp2= " maria\n kostas \n ";

        dateView= (TextView) findViewById(R.id.dateView);
        morningEmployees= (TextView) findViewById(R.id.morningEmployees);
        afternoonEmployees= (TextView) findViewById(R.id.afternoonEmployees);
        nightEmployees= (TextView) findViewById(R.id.nightEmployees);

        Intent incomingIntent=getIntent();
        String date= incomingIntent.getStringExtra("date");
        int day= bundle.getInt ("day");

        dateView.setText(date);



       if(day <15)
           {   morningEmployees.setText(morningEmp);
               afternoonEmployees.setText(afternoonEmp);
               nightEmployees.setText(nightEmp);
           }
       else
           {  morningEmployees.setText(morningEmp2);
              afternoonEmployees.setText(afternoonEmp2);
              nightEmployees.setText(nightEmp2);
           }


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
