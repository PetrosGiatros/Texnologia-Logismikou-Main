package com.example.texn_logism_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Arrays;
import java.util.HashMap;


public class HireFireActivity extends AppCompatActivity {
    private Button hireButton,backHireButton;
    HashMap<String,String> hireMap = new HashMap<>();
    String finalResult ;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getEmployees.php";

     static String[] employeeArray={};

     ArrayAdapter adapter;

    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hirefire_form);

        backHireButton= (Button)findViewById(R.id.buttonBackHireFire);
        hireButton= (Button)findViewById(R.id.buttonHire);


        System.out.println(loggedInUsername);

        getEmployeesInfo(loggedInUsername);









        hireButton.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                Intent intent = new Intent(HireFireActivity.this, HireActivity.class);
                startActivity(intent);
            }
        });

        backHireButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HireFireActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });

    }

     public String[] getEmployeesInfo(String loggedInUsername){

        class GetEmployeesInfoClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(HireActivity.this,"Loading Data",null,true,true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();
                //httpResponseMsg = id tou admin, autou poy ekane login //
                //System.out.println("username login:"+loggedInUsername);
                //Toast.makeText(HireFireActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

                /*for(int i = 0 ;i<employeeArray.length;i++){
                    System.out.println("Employees: "+ employeeArray[i]);
                }*/

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

                ListView listView = (ListView) findViewById(R.id.employeeList);
                adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayNew);
                listView.setAdapter(adapter);
            }

            @Override
            protected String doInBackground(String... params) {
                hireMap.put("loggedinUsername",params[0]);

                finalResult = httpParse.postRequest(hireMap, HttpURL);
                System.out.println("result = "+ finalResult);

                employeeArray = finalResult.split(" ");

                hireMap.put("employees",finalResult);
                return finalResult;
            }
        }
        GetEmployeesInfoClass getEmployeesInfoClass = new GetEmployeesInfoClass();
        getEmployeesInfoClass.execute(loggedInUsername);
        return employeeArray;
    }





}
