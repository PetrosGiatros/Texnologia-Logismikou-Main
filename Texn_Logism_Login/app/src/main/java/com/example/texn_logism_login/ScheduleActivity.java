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

/**
 *Schedule Activity generates the completed schedule according to the admin's wants an needs.
 */
public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_back,buttonLoadList;
    public static String date;
    public boolean failFlag = false;
    public TextView dateView /*,  morningEmployees , afternoonEmployees , nightEmployees*/;
    //public TextView startingDate;
    HashMap<String, String> scheduleMap = new HashMap<>();
    HashMap<String, String> scheduleEmployeesMap = new HashMap<>();


    String HttpURL = "http://priapic-blower.000webhostapp.com/getSchedule.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/getNewScheduleEmployees.php";
    static String[] employeeArray = {};
    ArrayAdapter adapterMorning,adapterAfternoon,adapterNight;
    String finalResult,finalResult2;
    HttpParse httpParse = new HttpParse();
    HttpParse httpParse2 = new HttpParse();
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_form);
        System.out.println("LGUN: " +loggedInUsername);
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

        String selectedDate = selectedday + "-" + (selectedmonth + 1) + "-" + selectedyear;

        getScheduleInfo(loggedInUsername, startingDate);

        getScheduleEmployees(loggedInUsername, selectedDate, morningView, afternoonView, nightView);

        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Intent intent = new Intent(ScheduleActivity.this, CalendarActivity.class);
                startActivity(intent);
            }

        });
    }

    /**
     *<h1>Get Schedule Info </h1>
     * This method is responsible for getting the logged in username and starting date of the schedule in order to print it later on.
     * @param loggedInUsername is the username of the person that is currently logged into the app.
     * @param startingDate is the first date of the schedule that was created.
     */

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

    /**
     *<h1>Get Date</h1>
     *
     * @return
     */
    public String getDate(){
        return date;
    }

    /**
     *<h1>Set Date</h1>
     * This method is used to insert the content of the variable startedDate into the variable date.
     * @param startedDate is the first date of the schedule that was created.
     */
    public void setDate(String startedDate){
        date = startedDate;
    }

    /**
     *<h1>Get Schedule Employees</h1>
     * This method is responsible of taking the logged in user and the selected day of the calendar and using this parameters to retrieve
     * the correct data from the database in order to print out the schedule of that date.
     * @param loggedInUsername is the username of the person that is currently logged into the app.
     * @param selectedDate is the date that was selected by the user in the calendar.
     * @param morningView is the textView that will print all the employees of the morning shift for the specific date.
     * @param afternoonView is the textView that will print all the employees of the afternoon shift for the specific date.
     * @param nightView is the textView that will print all the employees of the night shift for the specific date.
     */
    public void getScheduleEmployees(String loggedInUsername,String selectedDate,ListView morningView, ListView afternoonView, ListView nightView){

        class GetScheduleEmployeesClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                if (failFlag == false)
                {
                    System.out.println("Response Message " + httpResponseMsg);

                    String[] employeeDummy;
                    String[] employeeShifts= new String[employeeArray.length];
                    String[] employeeIDs= new String[employeeArray.length];
                    int j=0,k=0,i=0;
                    System.out.println("I made it before the employee ID's.");
                    System.out.println("EmployeeID's Length"+employeeArray.length);
                    for (i=0;i<employeeArray.length;i++) {
                        employeeDummy = employeeArray[i].split(" ");
                        employeeIDs[i] = employeeDummy[0];
                        employeeShifts[i] = employeeDummy[1];
                    }

                    int morningcount=0;
                    int afternooncount=0;
                    int nightcount=0;

                    for (int l=0;l<employeeIDs.length;l++){

                        if (employeeShifts[l].compareTo("Morning")==0){
                            morningcount++;
                        }
                        else if (employeeShifts[l].compareTo("Afternoon")==0){
                            afternooncount++;
                        }
                        else if (employeeShifts[l].compareTo("Midnight")==0){
                            nightcount++;
                        }
                    }

                    String[] employeeArrayMorning =new String[morningcount];
                    String[] employeeArrayAfternoon = new String[afternooncount];
                    String[] employeeArrayNight= new String[nightcount];

                    morningcount=0;
                    afternooncount=0;
                    nightcount=0;

                    for (int l=0;l<employeeIDs.length;l++){

                        if (employeeShifts[l].compareTo("Morning")==0){
                            employeeArrayMorning[morningcount]=employeeIDs[l];
                            morningcount++;
                        }
                        else if (employeeShifts[l].compareTo("Afternoon")==0){
                            employeeArrayAfternoon[afternooncount]=employeeIDs[l];
                            afternooncount++;
                        }
                        else if (employeeShifts[l].compareTo("Midnight")==0){
                            employeeArrayNight[nightcount]=employeeIDs[l];
                            nightcount++;
                        }
                    }

                    if (morningcount!=0) {
                        adapterMorning = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.label, employeeArrayMorning);
                        morningView.setAdapter(adapterMorning);
                    }

                    if (afternooncount!=0){
                        adapterAfternoon = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.label, employeeArrayAfternoon);
                        afternoonView.setAdapter(adapterAfternoon);
                    }
                    if (nightcount!=0) {
                        adapterNight = new ArrayAdapter<String>(getApplicationContext(), R.layout.activity_listview, R.id.label, employeeArrayNight);
                        nightView.setAdapter(adapterNight);
                    }

                }
                else
                {
                    System.out.println("YEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEEET");
                }
            }

            @Override
            protected String doInBackground(String... params) {
                scheduleEmployeesMap.put("loggedInUsername",params[0]);
                scheduleEmployeesMap.put("selectedDate",params[1]);

                finalResult2 = httpParse2.postRequest(scheduleEmployeesMap, HttpURL2);
                employeeArray = finalResult2.split("-");
                System.out.println("Employee Array Length" + employeeArray.length);
                System.out.println("Final Result 2: " + finalResult2);
                if (employeeArray.length < 2)
                {
                    for (int i = 0; i < employeeArray.length; i++)
                    {
                        if (!employeeArray[i].contains("Morning"))
                        {
                            failFlag = true;
                        }
                    }
                }
                return finalResult2;
            }
        }
        GetScheduleEmployeesClass getScheduleEmployeesClass = new GetScheduleEmployeesClass();
        getScheduleEmployeesClass.execute(loggedInUsername,selectedDate);
    }

    
}


