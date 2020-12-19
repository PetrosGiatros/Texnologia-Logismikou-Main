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
    /**
     * Button that Shows the calendar to the admin. Will transfer you to another form with a calendar
     */
    private Button button_view;
    /**
     * Button that when pressed, it will transfer you to another form to hire/fire users that belong to the admin currently logged in
     */
    private Button hireFireButton;
    /**
     * Button that will create a new schedule
     */
    private Button buttonCreate;
    /**
     * Button that will force the application to exit
     */
    private Button exitButton;
    /**
     * Button that will open a new form with the admin's notifications
     */
    private Button notificationsButton;
    /**
     * Button that will open a new form with the statistics
     */
    private Button statisticsButton;
    /**
     * Variable used to store the number of users that belong to the specific admin
     */
    public static int userAmount;
    /**
     * Primitive User Array
     */
    public static String primitiveUserArray[] = {};
    User usersArray[];


    String finalResult;
    HashMap<String, String> adminMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    /**
     * A String that stores the URL needed for php (Getting users per admin)
     */
    String HttpURL = "http://priapic-blower.000webhostapp.com/getUsersPerAdmin.php";
    /**
     * A String that stores the URL needed for php (Getting users count)
     */
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/getUserCount.php";
    /**
     * Refers to the username of the account currently logged in
     */
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_form);
        getUserCountFunction(loggedInUsername);
        getUsersFunction(loggedInUsername);

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
     * <h1>getUserCountFunction</h1>
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
     * <h1>getUsersFunction</h1>
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
                    for (int i = 0; i < primitiveUserArray.length; i++)
                    {
                        System.out.println(primitiveUserArray[i]);
                    }
                    System.out.println("Employee Array length: " + usersArray.length);
                    for (int i = 0; i < usersArray.length; i++) {
                        usersArray[i] = new User(Integer.parseInt(primitiveUserArray[k]),primitiveUserArray[k+1],
                                primitiveUserArray[k+2],primitiveUserArray[k+3],Integer.parseInt(primitiveUserArray[k+4]));
                        k = k + 5;
                    }

                    for (int i = 0; i < usersArray.length; i++)
                    {
                        System.out.println("ID in Array Loop: " + usersArray[i].id + "  First Name: "+ usersArray[i].firstName);
                    }
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




