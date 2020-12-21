package com.example.texn_logism_login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;


/**
 * HireFire Activity is responsible to show the Employees that belong to the connected admin, making him able to fire or hire a new employee
 *
 */
public class HireFireActivity extends AppCompatActivity {
    private Button hireButton,backHireButton,deleteButton;
    HashMap<String,String> hireMap = new HashMap<>();
    HashMap<String,String> delMap = new HashMap<>();
    String finalResult ;
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getEmployees.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/deleteEmployees.php";

     static String[] employeeArray={};

     ArrayAdapter adapter;

    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


    @RequiresApi(api = Build.VERSION_CODES.N)
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hirefire_form);
        deleteButton=(Button)findViewById(R.id.buttonDelete);
        backHireButton= (Button)findViewById(R.id.buttonBackHireFire);
        hireButton= (Button)findViewById(R.id.buttonHire);


        ListView listView = (ListView) findViewById(R.id.employeelist);

        getEmployeesInfo(loggedInUsername,listView);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView parent, View view, int position, long id) {

                String selectedItem = (String) parent.getItemAtPosition(position);
                final String[] selectedUsername = selectedItem.split(" ");

                System.out.println("selected username:" + selectedUsername[0]);
                deleteButton.setVisibility(view.VISIBLE);
                Toast.makeText(HireFireActivity.this, "Selected User: " + selectedUsername[0], Toast.LENGTH_SHORT).show();

                deleteButton.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {

                        DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which){
                                    case DialogInterface.BUTTON_POSITIVE:
                                        //Do your Yes progress
                                        deleteEmployees(selectedUsername[0]);
                                        getEmployeesInfo(loggedInUsername,listView);
                                        deleteButton.setVisibility(view.GONE);
                                        break;

                                    case DialogInterface.BUTTON_NEGATIVE:
                                        //Do your No progress
                                        break;
                                }
                            }
                        };
                        AlertDialog.Builder ab = new AlertDialog.Builder(HireFireActivity.this);
                        ab.setMessage("Are you sure to delete: "+selectedUsername[0]+"?").setPositiveButton("Yes", dialogClickListener)
                                .setNegativeButton("No", dialogClickListener).show();


                    }
                });



            }
        });











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


    /**
     * <h1>Get Employees Info</h1>
     * getEmployeesInfo - Is a method that gets all the user that belong to the logged in administrator, and shows their data in a Listview that we created. The users can be selected in this Listview
     * @param loggedInUsername Reffers to the username of the account currently logged in
     * @param listView Is the listView that the employees will be stored in
     * @return
     */
     public String[] getEmployeesInfo(String loggedInUsername, ListView listView){

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

                adapter=new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayNew);
                listView.setAdapter(adapter);


            }

            @Override
            protected String doInBackground(String... params) {
                hireMap.put("loggedinUsername",params[0]);

                finalResult = httpParse.postRequest(hireMap, HttpURL);
                //System.out.println("result = "+ finalResult);

                employeeArray = finalResult.split(" ");

                hireMap.put("employees",finalResult);
                return finalResult;
            }
        }
        GetEmployeesInfoClass getEmployeesInfoClass = new GetEmployeesInfoClass();
        getEmployeesInfoClass.execute(loggedInUsername);
        return employeeArray;
    }

    /**
     * <h1>Delete Employees</h1>
     * deleteEmployees - Is a method that makes it possible to delete an employee from said Listview, removing them from the layout and the database tables "login" and "users"
     * @param selectedUsername Refers to the username that is currently selected
     */
    public void deleteEmployees(String selectedUsername){

        class DeleteEmployees extends AsyncTask<String,Void,String> {
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
                delMap.put("selectedUsername",params[0]);
                finalResult = httpParse.postRequest(delMap, HttpURL2);
                return finalResult;
            }
        }
        DeleteEmployees deleteEmployees = new DeleteEmployees();
        deleteEmployees.execute(selectedUsername);
    }



}
