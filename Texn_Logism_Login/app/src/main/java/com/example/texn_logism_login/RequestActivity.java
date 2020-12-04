package com.example.texn_logism_login;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class RequestActivity extends AppCompatActivity
{

    private Button submitLeaveRequestButton;
    private EditText leaveDays;

    String HttpURL = "http://priapic-blower.000webhostapp.com/getRequest.php";
    String finalResult ;
    HttpParse httpParse = new HttpParse();
    HashMap<String,String> dataMap = new HashMap<>();
    static String[] employeeArray={};
    ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_form);
        submitLeaveRequestButton=(Button)findViewById(R.id.leaveButton);
        EditText leaveDays = (EditText)findViewById(R.id.leaveDays);
        String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


        submitLeaveRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int number = Integer.parseInt(leaveDays.getText().toString());
                System.out.println("Leave Days: " + number + "Username " + loggedInUsername);
            }
        });

    }

    public String[] getIDInfo(String loggedInUsername, ListView listView){

        class GetIDInfoClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);


                int j =0;
                for(int i =0;i<employeeArray.length;i=i+2){
                    employeeArray[j]=employeeArray[i] +" "+employeeArray[i+1];
                    j=j+1;
                }
                int temp=j;
                for(int i=temp;i<employeeArray.length;i++){
                    employeeArray[i]="";
                }
                String[] employeeArrayNew=new String[employeeArray.length/2];
                for(int i=0;i<temp;i++){
                    employeeArrayNew[i]=employeeArray[i];
                }

                adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayNew);
                listView.setAdapter(adapter);


            }

            @Override
            protected String doInBackground(String... params) {
                dataMap.put("loggedinUsername",params[0]);
                System.out.println(params[0]);
                finalResult = httpParse.postRequest(dataMap, HttpURL);
                employeeArray = finalResult.split(" ");

                dataMap.put("employees",finalResult);
                System.out.println("result: "+finalResult);
                return finalResult;
            }
        }
        GetIDInfoClass getIDInfoClass = new GetIDInfoClass();
        getIDInfoClass.execute(loggedInUsername);
        return employeeArray;
    }
}
