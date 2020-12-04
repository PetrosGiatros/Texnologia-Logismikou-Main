package com.example.texn_logism_login;



import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class SaveScheduleActivity extends AppCompatActivity {
    HashMap<String,String> scheduleMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/saveSchedule.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/deleteSchedule.php";
    String finalResult ;




    public void saveScheduleActivity(String shiftName,String textMorning,String textAfternoon,String textMidnight,boolean hasMorning,boolean hasAfternoon,boolean hasMidnight){

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
               // System.out.println("shiftname received: "+ shiftName);
               // System.out.println("textToSend received: "+ textToSend);

                scheduleMap.put("shiftName",params[0]);
                scheduleMap.put("textMorning",params[1]);
                scheduleMap.put("textAfternoon",params[2]);
                scheduleMap.put("textMidnight",params[3]);
                scheduleMap.put("hasMorning",params[4]);
                scheduleMap.put("hasAfternoon",params[5]);
                scheduleMap.put("hasMidnight",params[6]);




                finalResult = httpParse.postRequest(scheduleMap, HttpURL);
                System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        SaveScheduleClass saveScheduleClass = new SaveScheduleClass();
        saveScheduleClass.execute(shiftName,textMorning,textAfternoon,textMidnight);

    }

    public void deleteScheduleActivity(String shiftName){

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
                scheduleMap.put("shiftName",params[0]);
                finalResult = httpParse.postRequest(scheduleMap, HttpURL2);


                return finalResult;
            }
        }
        DeleteScheduleClass deleteScheduleClass = new DeleteScheduleClass();
        deleteScheduleClass.execute(shiftName);

    }






}