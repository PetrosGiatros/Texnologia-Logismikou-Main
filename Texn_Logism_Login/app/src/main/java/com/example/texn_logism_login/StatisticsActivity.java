package com.example.texn_logism_login;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

public class StatisticsActivity extends AppCompatActivity
{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_form);
        Stats statObj = new Stats();
        if (!statObj.failFlag)
        {
            statObj.calculateHoursPerProfession();
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

    }
}
