package com.example.texn_logism_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
//import android.support.annotation.Nullable;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CalendarView;
import android.widget.CalendarView.OnDateChangeListener;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.jetbrains.annotations.Nullable;

import java.io.ObjectOutputStream;
import java.util.HashMap;
/**
 * Calendar that lets the user select a date and open a new form
 */
public class CalendarActivity extends AppCompatActivity {
    private static final String TAG = "CalendarActivity";
    private CalendarView calendarView;
    HashMap<String,String> backMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/backButtonCalendar.php";
    String finalResult;
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.calendar_form);



        calendarView= (CalendarView) findViewById(R.id.calendarView);
        calendarView.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {

            @Override

            /**
             *
             *  * <h1>onSelectedDayChange</h1>
             *  * This method is responsible to open the new form based on the Date picked
             *  * @param calendarView Is our Calendar
             *  * @param year Int that specifies the year selected
             *  * @param month Int that specifies the month selected
             *  * @param day Int that specifies the day selected
             */

            public void onSelectedDayChange(CalendarView calendarView ,int year, int month, int day) {
                String date = day + "/" + (month + 1) + "/" + year;
                Log.d(TAG, "onSelectedDayChange: dd/mm/yyyy: " +date);

                Intent intent = new Intent(CalendarActivity.this, ScheduleActivity.class);
                intent.putExtra("date",date);
                intent.putExtra("day",day);
                intent.putExtra("month",month);
                intent.putExtra("year",year);
                startActivity(intent);
            }
        });
        Button buttonBackCalendar = (Button) findViewById(R.id.buttonBackCalendar);
        buttonBackCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                BackButtonFunction(loggedInUsername);
            }

        });
    }

    /**
     * <h1>Opens the schedule form</h1>
     * When a data is chosen, it opens schedule form, with the selected date.
     */
    public  void openSchedule_form() {

        Intent intent =new Intent(this, ScheduleActivity.class);
        startActivity(intent);
    }

    public void BackButtonFunction(String username){
        class BackButtonClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                if (finalResult.compareTo("User")==0){
                    Intent intent = new Intent(CalendarActivity.this, UserActivity.class);
                    startActivity(intent);
                }
                else if(finalResult.compareTo("Admin")==0){
                    Intent intent = new Intent(CalendarActivity.this, AdminActivity.class);
                    startActivity(intent);
                }
            }
            @Override
            protected String doInBackground(String... params) {

                backMap.put("username",params[0]);
;
                finalResult = httpParse.postRequest(backMap, HttpURL);
                System.out.println(finalResult);
                return finalResult;
            }
        }
        BackButtonClass backButtonClass = new BackButtonClass();
        backButtonClass.execute(username);
    }







}

