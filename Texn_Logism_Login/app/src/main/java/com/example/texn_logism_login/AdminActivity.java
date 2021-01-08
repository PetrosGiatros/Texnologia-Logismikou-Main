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
/**
 * Admin activity is the main form that appears only to the admins connected.
 */

public class AdminActivity extends AppCompatActivity {
    private Button button_view;
    private Button hireFireButton;
    private Button exitButton,buttonCreate,button_logout;
    private Button notificationsButton;
    private Button statisticsButton;
    public static int userAmount;
    public static String primitiveUserArray[] = {};
    User usersArray[];


    String finalResult;
    HashMap<String, String> adminMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    String HttpURL = "http://priapic-blower.000webhostapp.com/getUsersPerAdminExperimental.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/getUserCount.php";
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


    protected void onCreate(Bundle savedInstanceState) {  //Hocus pocus gamw ton xristo tous. Ai sto diaolo me afti tin gamimeni glwssa.
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);
        getUserCountFunction(loggedInUsername);
        getUsersFunction(loggedInUsername);
        button_logout = (Button) findViewById(R.id.buttonLogoutAdmin);
        button_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminActivity.this, LoginActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);

            }
        });
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
    }

    /**
     * <h1>Get User Count Function</h1>
     * Used to get the Users count that belong to the admin currently logged in
     * @param loggedInUsername Refers to the username of the account currently logged in.
     */
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

    /**
     * <h1>Get Users Function</h1>
     * Used to get all the Users that belong to the admin that is currently logged in
     * @param loggedInUsername Refers to the username of the account currently logged in
     */
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
                if (userAmount== 0) {
                    System.out.println("YEET");
                }
                else
                {
                    System.out.println("Users " + userAmount);
                    usersArray = new User[userAmount];
                    primitiveUserArray = httpResponseMsg.split(" ");
                    int k = 0;
                    /*for (int i = 0; i < primitiveUserArray.length; i++)
                    {
                        System.out.println(primitiveUserArray[i]);
                    }*/
                    System.out.println("Employee Array length: " + usersArray.length);
                    for (int i = 0; i < usersArray.length; i++) {
                        usersArray[i] = new User(Integer.parseInt(primitiveUserArray[k]),primitiveUserArray[k+1],
                                primitiveUserArray[k+2],primitiveUserArray[k+3],Integer.parseInt(primitiveUserArray[k+4]),
                                Boolean.parseBoolean(primitiveUserArray[k+5]),Boolean.parseBoolean(primitiveUserArray[k+6]),Boolean.parseBoolean(primitiveUserArray[k+7]));
                        k = k + 8;
                    }

                    /*for (int i = 0; i < usersArray.length; i++)
                    {
                        System.out.println("ID in Array Loop: " + usersArray[i].id + "  First Name: "+ usersArray[i].firstName);
                    }*/
                    Utilities utilObj = new Utilities();
                    utilObj.initializeUserObjects(userAmount);      //These are the two functions that make the users globally available.
                    utilObj.copyUsersArray(usersArray);             //Please do not touch my garbage.

                }

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




