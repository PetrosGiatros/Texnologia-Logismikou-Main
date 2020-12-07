package com.example.texn_logism_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class NotificationsActivity extends AppCompatActivity {
    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/setNotifications.php";
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    public void setFunction (String loggedInUsername){
        class setClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                System.out.println(httpResponseMsg);
                int k = 0;
                String[] returnedString = httpResponseMsg.split(" ");
                String[][] returnedEmployeesFinal = new String[returnedString.length/4][4];
                for (int i = 0; i < returnedString.length/4; i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        returnedEmployeesFinal[i][j] = returnedString[k];
                        k++;
                    }
                }
            }


            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username", params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        setClass setObject = new setClass();
        setObject.execute(loggedInUsername);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_form);
        setFunction(loggedInUsername);
        System.out.println("Logged in username " + loggedInUsername);
    }
}
