package com.example.texn_logism_login;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
/**
 * RequestActivity.java is available only for users (not admins) and is responsible for <b>sending leave requests</b> to the admin that the user is connected to
 */
public class NotificationsActivity extends AppCompatActivity {
    private Button denyButton,acceptButton,backButton;
    static String[] employeeArray={};
    ArrayAdapter adapter;
    ListView listView;

    String finalResult;
    HashMap<String, String> hashMap = new HashMap<>();
    HashMap<String,String> acceptMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();


    String HttpURL = "http://priapic-blower.000webhostapp.com/setNotifications.php";
    String HttpURL2 = "http://priapic-blower.000webhostapp.com/acceptDenyRequest.php";
    String HttpURL3 = "http://priapic-blower.000webhostapp.com/denyRequest.php";
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();







    /**
     * <h1>Deny Employees</h1>
     * This method is used to connect to the database and deny an employee's request for leave day
     * @param loggedInUsername Refers to the username of the account currently logged in
     */
    public void denyEmployees(String loggedInUsername){
        class DenyEmployees extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                System.out.println(httpResponseMsg);
            }


            @Override
            protected String doInBackground(String... params) {
                hashMap.put("selectedUsername", params[0]);
                String finalresult2 = httpParse.postRequest(hashMap, HttpURL3);
                System.out.println("apotelesmata php: "+finalresult2);
                //checkLeaveFunction(finalResult);
                return finalresult2;
            }
        }
        DenyEmployees denyObj = new DenyEmployees();
        denyObj.execute(loggedInUsername);


    }


    /**
     * <h1>Set Function</h1>
     * @param loggedInUsername Refers to the username of the account currently logged in
     * @param listView ListView to combine with the adapter
     */
    public void setFunction (String loggedInUsername, ListView listView){
        class setClass extends AsyncTask<String, Void, String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                System.out.println(httpResponseMsg);
                if (httpResponseMsg != null)
                {
                    int k = 0;
                    employeeArray = httpResponseMsg.split(" ");
                    String[][] returnedEmployeesFinal = new String[employeeArray.length/4][4];
                    ListView list;
                    for (int i = 0; i < employeeArray.length/4; i++)
                    {
                        for (int j = 0; j < 4; j++)
                        {
                            returnedEmployeesFinal[i][j] = employeeArray[k];
                            k++;
                        }
                    }
                    String format[] = new String[4];
                    format[0] = " Username: ";
                    format[1] = " ID: ";
                    format[2] = " Days: ";
                    format[3] = " Date: ";
                    int j =0;
                    for(int i = 0;i<employeeArray.length;i = i + 4){
                        employeeArray[j]=format[0] + employeeArray[i] +format[1] +employeeArray[i+1]+format[2] +employeeArray[i+2]+format[3] +employeeArray[i+3];
                        j=j+1;
                    }

                    int temp=j;
                    for(int i=temp;i<employeeArray.length;i++){
                        employeeArray[i]="";
                    }
                    String[] employeeArrayNew=new String[employeeArray.length/4];
                    for(int i=0;i<temp;i++){
                        employeeArrayNew[i]=employeeArray[i];
                    }

                    adapter = new ArrayAdapter<String>(getApplicationContext(),R.layout.activity_listview,R.id.label, employeeArrayNew);
                    listView.setAdapter(adapter);
                    //eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee
                    acceptButton=(Button)findViewById(R.id.acceptButton);
                    denyButton=(Button)findViewById(R.id.denyButton);

                    //eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee arxi
                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView parent, View view, int position, long id) {

                            String selectedItem = (String) parent.getItemAtPosition(position);
                            System.out.println("Selected item: "+selectedItem);
                            final String[] selectedUsername = selectedItem.split(" ");

                            for(int idsds=0;idsds<selectedUsername.length;idsds+=1) {
                                System.out.println(selectedUsername[idsds]);
                            }


                            acceptButton.setVisibility(view.VISIBLE);
                            denyButton.setVisibility(view.VISIBLE);

                            Toast.makeText(NotificationsActivity.this, "Selected User: " + selectedUsername[2], Toast.LENGTH_SHORT).show();
                            //eeeeeeeeeeeeeeeeeeee arxi accept
                            acceptButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which){
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    //Do your Yes progress
                                                    System.out.println(selectedUsername[2]);
                                                    acceptDenyEmployees(selectedUsername[2]);
                                                    getEmployeesInfo(loggedInUsername,listView);
                                                    acceptButton.setVisibility(view.GONE);
                                                    denyButton.setVisibility(view.GONE);
                                                    Intent intent = new Intent(NotificationsActivity.this, AdminActivity.class);
                                                    startActivity(intent);

                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    //Do your No progress
                                                    break;
                                            }
                                        }
                                    };
                                    AlertDialog.Builder ab = new AlertDialog.Builder(NotificationsActivity.this);
                                    ab.setMessage("Theleis sigoura na apodexteis tin adeia: "+selectedUsername[2]+"?").setPositiveButton("Yes", dialogClickListener)
                                            .setNegativeButton("No", dialogClickListener).show();


                                }
                            });
                            //eeeeeeeeeeeeeeeee telos accept

                            //eeeeeeeeeeeeeeeee arxi deny
                            denyButton.setOnClickListener(new View.OnClickListener() {
                                public void onClick(View v) {

                                    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
                                        @Override
                                        public void onClick(DialogInterface dialog, int which) {
                                            switch (which){
                                                case DialogInterface.BUTTON_POSITIVE:
                                                    //Do your Yes progress
                                                    System.out.println(selectedUsername[2]);
                                                    denyEmployees(selectedUsername[2]);
                                                    getEmployeesInfo(loggedInUsername,listView);
                                                    denyButton.setVisibility(view.GONE);
                                                    acceptButton.setVisibility(view.GONE);
                                                    Intent intent = new Intent(NotificationsActivity.this, AdminActivity.class);
                                                    startActivity(intent);
                                                    break;

                                                case DialogInterface.BUTTON_NEGATIVE:
                                                    //Do your No progress
                                                    break;
                                            }
                                        }
                                    };
                                    AlertDialog.Builder ab = new AlertDialog.Builder(NotificationsActivity.this);
                                    ab.setMessage("Theleis sigoura na aporripseis tin adeia: "+selectedUsername[2]+"?").setPositiveButton("Yes", dialogClickListener)
                                            .setNegativeButton("No", dialogClickListener).show();


                                }
                            });
                            //eeeeeeeeeeeeeeeee telos deny
                        }
                    });
                    //eeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeeee telos
                }
                else
                {
                    System.out.println("Yeet");
                }

            }


            @Override
            protected String doInBackground(String... params) {
                hashMap.put("username", params[0]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                System.out.println("final result apo php: "+finalResult);
                return finalResult;
            }
        }
        setClass setObject = new setClass();
        setObject.execute(loggedInUsername);
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.notifications_form);
        ListView listView = (ListView) findViewById(R.id.notificationsListView);
        setFunction(loggedInUsername,listView);
        System.out.println("Logged in username " + loggedInUsername);


        backButton=(Button)findViewById(R.id.buttonBackNotif);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(NotificationsActivity.this, AdminActivity.class);
                startActivity(intent);
            }
        });
    }


    /**
     * <h1>Accept Deny Employees</h1>
     * This Method is used to connect to the Database and reset the employee's leave days request.
     * @param selectedUsername
     */
    public void acceptDenyEmployees(String selectedUsername){
        //eisagoume kodika kai edo
        class AcceptDenyEmployees extends AsyncTask<String,Void,String> {
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
                acceptMap.put("selectedUsername",params[0]);
                finalResult = httpParse.postRequest(acceptMap, HttpURL2);
                System.out.println("final result apo php accept: "+finalResult);
                return finalResult;
            }
        }
        AcceptDenyEmployees acceptDenyEmployees = new AcceptDenyEmployees();
        acceptDenyEmployees.execute(selectedUsername);
    }

    public void getEmployeesInfo(String loggedInUsername, ListView listView){
        //do nothing
    }
}

