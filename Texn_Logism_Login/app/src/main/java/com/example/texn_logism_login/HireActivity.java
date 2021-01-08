package com.example.texn_logism_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
/**
 * HireActivity is responsible for transferring data from the app to the database, when a new employee is hired
 */
public class HireActivity extends AppCompatActivity {

    private TextView usernameHireTextView, passwordHireTextView, emailHireTextView,firstNameHireTextView,lastNameHireTextView;
    private Button submitButton,backButton;
    ProgressDialog progressDialog;
    HashMap<String,String> hireMap = new HashMap<>();
    HashMap<String,String> addedMap =new HashMap<>();
    HttpParse httpParse = new HttpParse();
    HttpParse httpParse2 = new HttpParse();
    String finalResult, finalResult2 ;
    String usernameHolder,passwordHolder,emailHolder,firstNameHolder, lastNameHolder;
    boolean CheckEditText;
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getid.php";
    String HttpURL2=  "http://priapic-blower.000webhostapp.com/addedInformation.php";
    String[] Professions = new String[]{"Programmer", "Analyst", "Manager"};
    String[] Hours = new String[]{"4", "8"};
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_form);

        firstNameHireTextView = (TextView) findViewById(R.id.editTextFirstNameHire);
        lastNameHireTextView = (TextView) findViewById(R.id.editTextLastNameHire);
        usernameHireTextView = (TextView) findViewById(R.id.editTextUsernameHire);
        passwordHireTextView = (TextView) findViewById(R.id.editTextPasswordHire);
        emailHireTextView = (TextView) findViewById(R.id.editTextEmailHire);



        Spinner dropDownProfession = findViewById(R.id.dropDownProfession);
        ArrayAdapter<String> adapterProfessions = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Professions);
        dropDownProfession.setAdapter(adapterProfessions);



        submitButton = (Button) findViewById(R.id.buttonSubmitHire);
        backButton = (Button) findViewById(R.id.buttonBackHire);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String profession = dropDownProfession.getSelectedItem().toString();
                String firstName = firstNameHireTextView.getText().toString();
                String lastName = lastNameHireTextView.getText().toString();
                String username = usernameHireTextView.getText().toString();
                String password = passwordHireTextView.getText().toString();
                String email = emailHireTextView.getText().toString();
                System.out.printf("prof : " + profession );

                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    UserRegisterFunction(loggedInUsername, username, password, email );
                    UserAddedInformationFunction( firstName, lastName, profession, "8", username);
                } else {
                    Toast.makeText(HireActivity.this, "Please fill form fields.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(HireActivity.this, HireFireActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HireActivity.this, HireFireActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);

            }
        });
    }

    /**
     *<h1>Check Edit Text Is Empty Or Not</h1>
     *      This method is responsible for checking if any of the TextViews concerning the first name, last name, username , password or email of the hire form are empty.
     */
    public void CheckEditTextIsEmptyOrNot(){
        firstNameHolder = firstNameHireTextView.getText().toString();
        lastNameHolder = lastNameHireTextView.getText().toString();
        usernameHolder = usernameHireTextView.getText().toString();
        passwordHolder = passwordHireTextView.getText().toString();
        emailHolder = emailHireTextView.getText().toString();



        if ((TextUtils.isEmpty(usernameHolder)) || (TextUtils.isEmpty(passwordHolder))) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }

    /**
     * <h1>User Register Function</h1>
     * UserRegisterFunction - Is a method that gets the data from the form HireActivity that the admin filled, and sends the username,password and email from the registered user into our database table "login"
     * @param loggedInUsername Reffers to the username of the account currently logged in
     * @param username Is the username of the employee
     * @param password Is the password of the employee
     * @param email is the email of the employee
     */

    public void UserRegisterFunction(String loggedInUsername,String username,String password,String email){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(HireActivity.this,"Loading Data",null,true,true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                //httpResponseMsg = id tou admin, autou poy ekane login //

                System.out.println("username login:"+loggedInUsername);
                Toast.makeText(HireActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                hireMap.put("loggedinUsername",params[0]);
                hireMap.put("username",params[1]);
                hireMap.put("password",params[2]);
                hireMap.put("email",params[3]);


                finalResult = httpParse.postRequest(hireMap, HttpURL);
                System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        UserRegisterFunctionClass userRegisterFunctionClass = new UserRegisterFunctionClass();
        userRegisterFunctionClass.execute(loggedInUsername,username,password,email);
    }

    /**
     * <H1> User Added Information Function</H1>
     * UserAddedInformationFunction - Is a method that gets the remaining data from the form HireActivity that the admin filled. This includes first name, last name, profession and shiftType that the user will have. Then sends all of these into our database table "user"
     * @param firstName Is the first name of the employee to be hired
     * @param lastName is the last name of the employee to be hired
     * @param profession Is the employee's profession
     * @param hours Is the hours working
     * @param username Is the username he will be using to log in the app
     */

    public void UserAddedInformationFunction(String firstName, String lastName, String profession, String hours, String username){

        class UserAddedInformationFunctionClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                //httpResponseMsg = id tou admin, autou poy ekane login //


                Toast.makeText(HireActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                addedMap.put("firstName",params[0]);
                addedMap.put("lastName",params[1]);
                addedMap.put("profession",params[2]);
                addedMap.put("hours","8");
                addedMap.put("username",params[4]);

                finalResult2 = httpParse2.postRequest(addedMap, HttpURL2);
                System.out.println("result = "+ finalResult2);
                return finalResult2;
            }
        }
        UserAddedInformationFunctionClass userAddedInformationFunctionClass = new UserAddedInformationFunctionClass();
        userAddedInformationFunctionClass.execute(firstName, lastName, profession, hours,username);
    }




}
