package com.example.texn_logism_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class StatisticsActivity extends AppCompatActivity
{
    public HashMap<String,String> statsMapPull = new HashMap<>();
    private Button backStatsButton;
    public HttpParse httpParse = new HttpParse();
    public String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    public String HttpURLPull = "http://priapic-blower.000webhostapp.com/pullStatistics.php";
    public String resultArray[] = new String[9];
    TextView textView;
    String finalResultPull;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.statistics_form);
        pullStats(loggedInUsername);

        backStatsButton= (Button)findViewById(R.id.buttonBackStatistics);
        backStatsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StatisticsActivity.this, AdminActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);

            }
        });

        Stats statObj = new Stats();



    }
    public void pullStats(String loggedInUsername)
    {
        class pullStatsClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                resultArray = httpResponseMsg.split(" ");
                System.out.println("HHTATTATAESDT" + httpResponseMsg);

                textView = (TextView) findViewById(R.id.programmerHoursTextView);
                textView.setText("Programmer: " + resultArray[3] + " hours assigned.");

                textView = (TextView) findViewById(R.id.AnalystHoursTextView);
                textView.setText("Analyst: " + resultArray[4] + " hours assigned.");

                textView = (TextView) findViewById(R.id.ManagerHoursTextView);
                textView.setText("Manager: " + resultArray[5] + " hours assigned.");

                textView = (TextView) findViewById(R.id.programmerCountTextview);
                textView.setText("Programmers:" + resultArray[6]);

                textView = (TextView) findViewById(R.id.analystCountTextview);
                textView.setText("Analysts:" + resultArray[7]);

                textView = (TextView) findViewById(R.id.managerCountTextview);
                textView.setText("Managers:" + resultArray[8]);

                textView = (TextView) findViewById(R.id.employeesPerShiftTextview);
                textView.setText("Employees per shift: " + resultArray[2]);

                textView = (TextView) findViewById(R.id.scheduleTypeTextview);
                textView.setText("Schedule Type: " + resultArray[0]);

                textView = (TextView) findViewById(R.id.businessTypeTextView);
                textView.setText("Business Type: " + resultArray[1]);
            }

            @Override
            protected String doInBackground(String... params) {

                statsMapPull.put("username",loggedInUsername);
                finalResultPull = httpParse.postRequest(statsMapPull, HttpURLPull);
                return finalResultPull;
            }
        }
        pullStatsClass setObject = new pullStatsClass();  //I have no idea if this'll work but I sure fucking hope so.
        setObject.execute();
    }
}