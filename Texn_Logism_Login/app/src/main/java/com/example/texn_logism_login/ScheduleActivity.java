package com.example.texn_logism_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import org.w3c.dom.ls.LSOutput;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Set;

public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_back,buttonLoadList;
    public static String date;
    public TextView dateView /*,  morningEmployees , afternoonEmployees , nightEmployees*/;
    //public TextView startingDate;
    HashMap<String, String> scheduleMap = new HashMap<>();
    HashMap<String, String> scheduleEmployeesMap = new HashMap<>();


    String HttpURL = "http://priapic-blower.000webhostapp.com/getSchedule.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/getScheduleEmployees.php";
    static String[] employeeArray = {};
    ArrayAdapter adapterMorning,adapterAfternoon,adapterNight;
    String finalResult;
    HttpParse httpParse = new HttpParse();
    HttpParse httpParse2 = new HttpParse();
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_form);
        Bundle bundle = getIntent().getExtras();
        ListView morningView = (ListView) findViewById(R.id.morninglist);
        ListView afternoonView = (ListView) findViewById(R.id.afternoonlist);
        ListView nightView = (ListView) findViewById(R.id.nightlist);
        TextView startingDate = findViewById(R.id.startingDate);
        String firstDateString = null;
        int selectedday = bundle.getInt("day");
        int selectedmonth = bundle.getInt("month");
        int selectedyear = bundle.getInt("year");
        dateView = (TextView) findViewById(R.id.dateView);
        dateView.setText(selectedday + "/" + (selectedmonth + 1) + "/" + selectedyear);

        getScheduleInfo(loggedInUsername,startingDate);

        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ScheduleActivity.this, CalendarActivity.class);
                startActivity(intent);
            }

        });
        buttonLoadList = (Button) findViewById(R.id.buttonLoadList);
        buttonLoadList.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int dateDifference = 0;

                System.out.println("PLS WORK:"+date);
                String firstDate[]= date.split(" ");
                firstDate[1]=String.valueOf(Integer.valueOf(firstDate[1])-1);
                System.out.println("FIRSTDAY:" + Integer.valueOf(firstDate[0]));

                if (selectedday > Integer.valueOf(firstDate[0]) || selectedmonth > Integer.valueOf(firstDate[1]) || selectedyear > Integer.valueOf(firstDate[2])) {
                    Calendar cal1 = Calendar.getInstance();
                    Calendar cal2 = Calendar.getInstance();
                    cal1.set(Integer.parseInt(firstDate[2]), Integer.parseInt(firstDate[1]), Integer.parseInt(firstDate[0]));
                    cal2.set(selectedyear, selectedmonth, selectedday);
                    long millis1 = cal1.getTimeInMillis();
                    long millis2 = cal2.getTimeInMillis();
                    long diff = millis2 - millis1;
                    long diffDays = diff / (24 * 60 * 60 * 1000); //diafora merwn
                    dateDifference = (int) diffDays+1;
                } else if (selectedday == Integer.valueOf(firstDate[0]) && selectedmonth == Integer.valueOf(firstDate[1]) && selectedyear == Integer.valueOf(firstDate[2])) {
                    dateDifference = 1;
                } else {
                    System.out.println("KENO PROGRAMMA");
                }
                System.out.println("dateDifference:"+ dateDifference);
                getScheduleEmployees(loggedInUsername,dateDifference,morningView,afternoonView,nightView);
            }

        });
    }



    public void getScheduleInfo(String loggedInUsername,TextView startingDate) {

        class GetScheduleInfoClass extends AsyncTask<String, Void, String> {
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
                scheduleMap.put("loggedinUsername", params[0]);
                finalResult = httpParse.postRequest(scheduleMap, HttpURL);
                startingDate.setText(finalResult);
                setDate(startingDate.getText().toString());
                return finalResult;
            }
        }
        GetScheduleInfoClass getScheduleInfoClass = new GetScheduleInfoClass();
        getScheduleInfoClass.execute(loggedInUsername);
    }

    public String getDate(){
        return date;
    }

    public void setDate(String startedDate){
        System.out.println("AAAAAAAAAAAA: "+startedDate);
        date = startedDate;
    }


    public void getScheduleEmployees(String loggedInUsername,int dateDifference,ListView morningView, ListView afternoonView, ListView nightView){

        class GetScheduleEmployeesClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                String[] employeeArrayMorning= {};
                String[] employeeArrayAfternoon= {};
                String[] employeeArrayNight= {};

                employeeArrayMorning= employeeArray[0].split(" ");
                employeeArrayAfternoon= employeeArray[1].split(" ");
                employeeArrayNight= employeeArray[2].split(" ");



                adapterMorning=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayMorning);
                morningView.setAdapter(adapterMorning);

                adapterAfternoon=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayAfternoon);
                afternoonView.setAdapter(adapterAfternoon);

                adapterNight=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayNight);
                nightView.setAdapter(adapterNight);
            }

            @Override
            protected String doInBackground(String... params) {
                scheduleEmployeesMap.put("loggedInUsername",params[0]);
                scheduleEmployeesMap.put("dateDifference",params[1]);


                finalResult = httpParse2.postRequest(scheduleEmployeesMap, HttpURL2);
                employeeArray = finalResult.split("-");
                System.out.println("result:"+finalResult);
                System.out.println("prwi:"+employeeArray[0]);
                System.out.println("meshmeri:"+employeeArray[1]);
                System.out.println("vrady:"+employeeArray[2]);
                return finalResult;
            }
        }
        GetScheduleEmployeesClass getScheduleEmployeesClass = new GetScheduleEmployeesClass();
        getScheduleEmployeesClass.execute(loggedInUsername,String.valueOf(dateDifference));
    }

    
}


