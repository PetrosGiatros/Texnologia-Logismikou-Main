package com.example.texn_logism_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class NotificationsActivity extends AppCompatActivity {
    static String[] employeeArray={};
    ArrayAdapter adapter;
    ListView listView;

    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    String HttpURL = "http://priapic-blower.000webhostapp.com/setNotifications.php";
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


    public void setFunction (String loggedInUsername, ListView listView){
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
                employeeArray = httpResponseMsg.split(" ");
                String[][] returnedEmployeesFinal = new String[employeeArray.length/4][4];
                ListView list;
                for (int i = 0; i < employeeArray.length/4; i++)
                {
                    for (int j = 0; j < 4; j++)
                    {
                        returnedEmployeesFinal[i][j] = employeeArray[k];
                        k++;
                    }
                }
                String format[] = new String[4];
                format[0] = " UN ";
                format[1] = " ID ";
                format[2] = " EID ";
                format[3] = " RLD ";
                int j =0;
                for(int i = 0;i<employeeArray.length;i = i + 4){
                    employeeArray[j]=format[0] + employeeArray[i] +format[1] +employeeArray[i+1]+ format[2]+employeeArray[i+2]+format[3]+employeeArray[i+3];
                    j=j+1;
                }

                int temp=j;
                for(int i=temp;i<employeeArray.length;i++){
                    employeeArray[i]="";
                }
                String[] employeeArrayNew=new String[employeeArray.length/4];
                for(int i=0;i<temp;i++){
                    employeeArrayNew[i]=employeeArray[i];
                }

                adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArray);
                listView.setAdapter(adapter);
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
        ListView listView = (ListView) findViewById(R.id.notificationsListView);
        setFunction(loggedInUsername,listView);
        System.out.println("Logged in username " + loggedInUsername);
    }
}
