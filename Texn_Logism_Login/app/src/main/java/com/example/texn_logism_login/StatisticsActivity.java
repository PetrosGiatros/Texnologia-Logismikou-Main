package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class StatisticsActivity extends AppCompatActivity
{
    private Button backStatsButton;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_form);
        backStatsButton= (Button)findViewById(R.id.buttonBackStatistics);
        backStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, AdminActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);

            }
        });
        TextView textView;
        Stats statObj = new Stats();
        if (!statObj.failFlag)
        {
            //These need to get the fuck out of here and into create activity along with a call to pushStatsToDB when the code is complete.
            statObj.calculateHoursPerProfession();
            statObj.calculateUsersPerProfession();
            statObj.setLoggedInUsername();

            for (int i = 0; i < statObj.professions.length; i++)
            {
                System.out.println("Profession: " + statObj.professions[i] + " Hours in schedule: " + statObj.professionHours[i]);
            }
            for(int i =0;i<statObj.users.length;i++)
            {
                System.out.println("User: "+statObj.users[i].id+" has "+statObj.users[i].totalHours+" hours left. Has worked "+statObj.users[i].timesWorked + " times and "+ statObj.users[i].hoursWorked + " hours.");
            }
        }
        else
        {
            // Replace sout and add function to pull shit from DB if class does not already contain data.
            // If there are no data to pull, THEN output the message in the following sout.
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
