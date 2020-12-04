package com.example.texn_logism_login;



import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class SaveScheduleActivity extends AppCompatActivity {
    HashMap<String,String> scheduleMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/saveSchedule.php";
    String finalResult ;




    public void saveScheduleActivity(String shiftName,String textToSend){

        class SaveScheduleClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(HireActivity.this,"Loading Data",null,true,true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
            }
            @Override
            protected String doInBackground(String... params) {
                scheduleMap.put("userID",params[0]);
                scheduleMap.put("scheduleLength",params[1]);
                scheduleMap.put("numberOfShifts",params[2]);

                finalResult = httpParse.postRequest(scheduleMap, HttpURL);
                //System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        SaveScheduleClass saveScheduleClass = new SaveScheduleClass();
        saveScheduleClass.execute(String.valueOf(shiftName));

    }


    /*public void createTableSchedule(int employeesPerShift){

        class CreateTableScheduleClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                //progressDialog = ProgressDialog.show(HireActivity.this,"Loading Data",null,true,true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
            }
            @Override
            protected String doInBackground(String... params) {
                scheduleMap.put("EmployeesPerShift",params[0]);


                finalResult = httpParse.postRequest(scheduleMap, HttpURL);
                //System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        CreateTableScheduleClass createTableScheduleClass = new CreateTableScheduleClass();
        createTableScheduleClass.execute(String.valueOf(employeesPerShift));

    }*/





}