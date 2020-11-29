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

import java.util.HashMap;

public class ScheduleActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private Button button_back;
    public TextView dateView /*,  morningEmployees , afternoonEmployees , nightEmployees*/;
    HashMap<String,String> scheduleMap = new HashMap<>();

    String HttpURL = "http://priapic-blower.000webhostapp.com/getSchedule.php";
    static String[] employeeArray={};
    ArrayAdapter adapter;
    String finalResult ;
    HttpParse httpParse = new HttpParse();
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.schedule_form);
        Bundle bundle = getIntent().getExtras();

        ListView listView = (ListView) findViewById(R.id.userslist);

        getEmployeesInfo(loggedInUsername,listView);

    /*    String morningEmp=" petros \n maria \n sia \n kostas", morningEmp2=" dimitris \n andreas \n mhtsos \n giannis";
        String afternoonEmp= " mhtsos\n andreas\n", afternoonEmp2=" petros \n sia\n";
        String nightEmp= " dimitris \n giannis\n",nightEmp2= " maria\n kostas \n ";
*/
        dateView= (TextView) findViewById(R.id.dateView);

        Intent incomingIntent=getIntent();
        String date= incomingIntent.getStringExtra("date");
        int day= bundle.getInt ("day");

        dateView.setText(date);


/*

       if(day <15)
           {   morningEmployees.setText(morningEmp);
               afternoonEmployees.setText(afternoonEmp);
               nightEmployees.setText(nightEmp);
           }
       else
           {  morningEmployees.setText(morningEmp2);
              afternoonEmployees.setText(afternoonEmp2);
              nightEmployees.setText(nightEmp2);
           }
*/


        button_back = (Button) findViewById(R.id.button_back);
        button_back.setOnClickListener(new View.OnClickListener() {
                @Override
            public void onClick(View v) {


                    Intent intent =new Intent(ScheduleActivity.this, CalendarActivity.class);
                    startActivity(intent);
            }

    });
    }


    //eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

    public String[] getEmployeesInfo(String loggedInUsername, ListView listView){

        class GetEmployeesInfoClass extends AsyncTask<String,Void,String> {
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
                scheduleMap.put("loggedinUsername",params[0]);
                System.out.println(params[0]);
                finalResult = httpParse.postRequest(scheduleMap, HttpURL);
                employeeArray = finalResult.split(" ");

                scheduleMap.put("employees",finalResult);
                System.out.println("result: "+finalResult);
                return finalResult;
            }
        }
        GetEmployeesInfoClass getEmployeesInfoClass = new GetEmployeesInfoClass();
        getEmployeesInfoClass.execute(loggedInUsername);
        return employeeArray;
    }

    //eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee

}


