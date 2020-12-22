package com.example.texn_logism_login;



import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class SaveScheduleActivity extends AppCompatActivity {
    HashMap<String,String> scheduleMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/saveNewSchedule.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/deleteSchedule.php";
    String finalResult ;






    public void saveNewScheduleActivity(String shiftName,String employeeID,String hasMorning,String hasAfternoon,String hasMidnight,String date,String loggedInUsername){

        class SaveNewScheduleClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
            }
            @Override
            protected String doInBackground(String... params) {
                // System.out.println("shiftname received: "+ shiftName);
                // System.out.println("textToSend received: "+ textToSend);

                scheduleMap.put("shiftName",params[0]);
                scheduleMap.put("employeeID",params[1]);
                scheduleMap.put("hasMorning",params[2]);
                scheduleMap.put("hasAfternoon",params[3]);
                scheduleMap.put("hasMidnight",params[4]);
                scheduleMap.put("date",params[5]);
                scheduleMap.put("loggedInUsername",params[6]);




                finalResult = httpParse.postRequest(scheduleMap, HttpURL);
                System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        SaveNewScheduleClass saveNewScheduleClass = new SaveNewScheduleClass();
        saveNewScheduleClass.execute(shiftName,employeeID,hasMorning,hasAfternoon,hasMidnight,date,loggedInUsername);

    }

    public void deleteScheduleActivity(String loggedInUsername){

        class DeleteScheduleClass extends AsyncTask<String,Void,String> {
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
                // System.out.println("shiftname received: "+ shiftName);
                // System.out.println("textToSend received: "+ textToSend);
                scheduleMap.put("loggedInUsername",params[0]);
                finalResult = httpParse.postRequest(scheduleMap, HttpURL2);


                return finalResult;
            }
        }
        DeleteScheduleClass deleteScheduleClass = new DeleteScheduleClass();
        deleteScheduleClass.execute(loggedInUsername);

    }






}