package com.example.texn_logism_login;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_form);
        TextView textView;
        Stats statObj = new Stats();
        if (!statObj.failFlag)
        {
            statObj.calculateHoursPerProfession();
            statObj.calculateUsersPerProfession();
            for (int i = 0; i < statObj.professions.length; i++)
            {
                System.out.println("Profession: " + statObj.professions[i] + " Hours in schedule: " + statObj.professionHours[i]);
            }
            for(int i =0;i<statObj.users.length;i++)
            {
                System.out.println("User: "+statObj.users[i].FirstName+" has "+statObj.users[i].totalHours+" hours left. Has worked "+statObj.users[i].timesWorked + " times and "+ statObj.users[i].hoursWorked + " hours.");
            }
        }
        else
        {
            System.out.println("No stats to show. Class does not contain data.");
        }

        if (!statObj.failFlag)              //As clearly demonstrated by this block, I'm losing my sanity.
        {
            textView = (TextView) findViewById(R.id.textView12);
            textView.setText(statObj.professions[0] + ": " + statObj.professionHours[0] + " hours assigned.");

            textView = (TextView) findViewById(R.id.textView14);
            textView.setText(statObj.professions[1] + ": " + statObj.professionHours[1]+ " hours assigned.");

            textView = (TextView) findViewById(R.id.textView15);
            textView.setText(statObj.professions[2] + ": " + statObj.professionHours[2]+ " hours assigned.");

            textView = (TextView) findViewById(R.id.textView17);
            textView.setText(statObj.professions[0] + "s: " + statObj.userProfessionCount[0]);

            textView = (TextView) findViewById(R.id.textView18);
            textView.setText(statObj.professions[1] + "s: " + statObj.userProfessionCount[1]);

            textView = (TextView) findViewById(R.id.textView19);
            textView.setText(statObj.professions[2] + "s: " + statObj.userProfessionCount[2]);

            textView = (TextView) findViewById(R.id.textView21);
            textView.setText("Schedule Type: " + statObj.scheduleType);

            textView = (TextView) findViewById(R.id.textView22);
            textView.setText("Business Type: " + statObj.businessType);
        }



    }
}
