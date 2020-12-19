package com.example.texn_logism_login;



import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;


public class RequestActivity extends AppCompatActivity
{
    private Button submitLeaveRequestButton,BackButton;
    private EditText leaveDays;
    private Spinner spinnerWorkDates;

    String finalResult ,finalresult2;
    HashMap<String,String> hashMap = new HashMap<>();
    HashMap<String,String> hashMap2 = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getRequest.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/getUserWorkingDates.php";

    String[] workingDates={};



    public void leaveFunction(String username,String days,String startingDate) {
        class leaveClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                System.out.println(httpResponseMsg);
            }


            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username", params[0]);
                hashMap.put("leaveDays", params[1]);
                hashMap.put("startingDate", params[2]);
                finalresult2 = httpParse.postRequest(hashMap, HttpURL);

                System.out.println("apotelesmata php: "+finalresult2);
                //checkLeaveFunction(finalResult);

                return finalresult2;
            }
        }
        leaveClass leaveObject = new leaveClass();
        leaveObject.execute(username,days,startingDate);
        System.out.println("finale result ejw apo clss : "+finalresult2);

    }

    public void userWorkingDates(String username) {
        class userWorkingDatesClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                System.out.println(httpResponseMsg);
                spinnerWorkDates.setAdapter(new ArrayAdapter<String>(RequestActivity.this, android.R.layout.simple_spinner_dropdown_item, workingDates));
            }

            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username", params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL2);
                workingDates=finalResult.split(" ");
                return finalResult;
            }
        }
        userWorkingDatesClass userWorkObj = new userWorkingDatesClass();
        userWorkObj.execute(username);
    }



    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_form);
        BackButton = (Button)findViewById(R.id.buttonBack);
        submitLeaveRequestButton=(Button)findViewById(R.id.leaveButton);
        EditText leaveDays = (EditText)findViewById(R.id.leaveDays);
        String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
        spinnerWorkDates=(Spinner)findViewById(R.id.spinnerWorkDates);
        userWorkingDates(loggedInUsername);




        submitLeaveRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {

                String requestedLeaveDays = leaveDays.getText().toString();
                String startingDate= spinnerWorkDates.getSelectedItem().toString();
                leaveFunction(loggedInUsername,requestedLeaveDays,startingDate);


            }
        });

        BackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Toast.makeText(RequestActivity.this,"It's Not Working Yet, so sad :(.",Toast.LENGTH_LONG).show();
            }
        });




    }

}
