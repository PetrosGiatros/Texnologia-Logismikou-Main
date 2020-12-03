package com.example.texn_logism_login;



import android.os.AsyncTask;

import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

public class SaveScheduleActivity extends AppCompatActivity {
    HashMap<String,String> hireMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getEmployees.php";
    String finalResult ;




    public void saveSchedule(int[][] schedule,int scheduleLength){

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
                hireMap.put("schedule",params[0]);
                finalResult = httpParse.postRequest(hireMap, HttpURL);
                //System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        SaveScheduleClass saveScheduleClass = new SaveScheduleClass();
        saveScheduleClass.execute(String.valueOf(schedule));

    }


}