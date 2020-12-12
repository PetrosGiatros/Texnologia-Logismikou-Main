package com.example.texn_logism_login;

import android.app.ApplicationExitInfo;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


public class AdminActivity extends AppCompatActivity {
    private Button button_view;
    private Button hireFireButton;
    private Button exitButton,buttonCreate;
    private Button notificationsButton;
    private Button statisticsButton;
    public static int userAmount;
    User usersArray[];

    String finalResult;
    HashMap<String, String> adminMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    String HttpURL = "http://priapic-blower.000webhostapp.com/getUsersPerAdmin.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/getUserCount.php";
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


    protected void onCreate(Bundle savedInstanceState) {  //Hocus pocus gamw ton xristo tous. Ai sto diaolo me afti tin gamimeni glwssa.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);
        getUserCountFunction(loggedInUsername);

        buttonCreate= (Button)findViewById(R.id.buttonCreate);
        button_view = (Button) findViewById(R.id.button_view);
        button_view.setOnClickListener(new View.OnClickListener()
        {

            public void onClick(View v) {
                Intent intent =new Intent(AdminActivity.this, CalendarActivity.class);
                startActivity(intent);

            }
        });

        buttonCreate.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent =new Intent(AdminActivity.this, CreateActivity.class);
                startActivity(intent);

            }
        });



        hireFireButton = (Button)findViewById(R.id.buttonHireFire);
        exitButton= (Button)findViewById(R.id.buttonExit);
        hireFireButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                Intent intent = new Intent(AdminActivity.this, HireFireActivity.class);
                startActivity(intent);

            }
        });
        notificationsButton = (Button)findViewById(R.id.notificationsButton);
        notificationsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, NotificationsActivity.class);
                startActivity(intent);

            }
        });

        statisticsButton = (Button)findViewById(R.id.buttonStatistics);
        statisticsButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent intent =new Intent(AdminActivity.this, StatisticsActivity.class);
                startActivity(intent);

            }
        });


        exitButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                System.exit(0);
            }
        });
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void getUserCountFunction(String loggedInUsername)
    {
        class getUserCountClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                userAmount = Integer.parseInt(httpResponseMsg);
                System.out.println("User Amount: " + userAmount);
                usersArray = new User[userAmount];
                System.out.println("Employee Array length: " + usersArray.length);

            }

            @Override
            protected String doInBackground(String... params) {
                adminMap.put("username", params[0]);
                finalResult = httpParse.postRequest(adminMap, HttpURL2);
                return finalResult;
            }
        }
        getUserCountClass getUserCountObject = new getUserCountClass();
        getUserCountObject.execute(loggedInUsername);
    }

    public void getUsersFunction (String loggedInUsername)
    {
        class getUsersClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                System.out.println(httpResponseMsg);
                System.out.println("HTTP Response Message: " + httpResponseMsg);

            }


            @Override
            protected String doInBackground(String... params) {
                adminMap.put("username", params[0]);
                finalResult = httpParse.postRequest(adminMap, HttpURL);
                return finalResult;
            }
        }
        getUsersClass getUsersObject = new getUsersClass();
        getUsersObject.execute(loggedInUsername);
    }


}




