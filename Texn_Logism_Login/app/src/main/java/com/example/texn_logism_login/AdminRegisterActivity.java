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

public class AdminRegisterActivity extends AppCompatActivity {

    private TextView usernameRegTextView, passwordRegTextView, emailRegTextView;
    private Button submitButton,backButton;
    ProgressDialog progressDialog;
    HashMap<String,String> hireMap = new HashMap<>();
    HashMap<String,String> addedMap =new HashMap<>();
    HttpParse httpParse = new HttpParse();
    HttpParse httpParse2 = new HttpParse();
    String finalResult, finalResult2 ;
    String usernameHolder,passwordHolder,emailHolder;
    boolean CheckEditText;
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getid.php";
    String HttpURL2=  "http://priapic-blower.000webhostapp.com/addedInformation.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_form);


        usernameRegTextView = (TextView) findViewById(R.id.editTextUsernameReg);
        passwordRegTextView = (TextView) findViewById(R.id.editTextPasswordReg);
        emailRegTextView = (TextView) findViewById(R.id.editTextEmailReg);




        submitButton = (Button) findViewById(R.id.buttonSubmitReg);
        backButton = (Button) findViewById(R.id.Reg);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = usernameRegTextView.getText().toString();
                String password = passwordRegTextView.getText().toString();
                String email = emailRegTextView.getText().toString();

                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    AdminAddedInformationFunction(username, password, email);
                } else {
                    Toast.makeText(AdminRegisterActivity.this, "Please fill form fields.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(AdminRegisterActivity.this, HireFireActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRegisterActivity.this, HireFireActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);

            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){
        usernameHolder = usernameRegTextView.getText().toString();
        passwordHolder = passwordRegTextView.getText().toString();
        emailHolder = emailRegTextView.getText().toString();



        if ((TextUtils.isEmpty(usernameHolder)) || (TextUtils.isEmpty(passwordHolder))) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }


    public void UserRegisterFunction(String loggedInUsername,String username,String password,String email){

        class UserRegisterFunctionClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(AdminRegisterActivity.this,"Loading Data",null,true,true);
            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                //httpResponseMsg = id tou admin, autou poy ekane login //

                System.out.println("username login:"+loggedInUsername);
                Toast.makeText(AdminRegisterActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

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

    public void AdminAddedInformationFunction(String username,String password,String email){

        class AdminAddedInformationFunctionClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                //httpResponseMsg = id tou admin, autou poy ekane login //


                Toast.makeText(AdminRegisterActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                addedMap.put("firstName",params[0]);
                addedMap.put("lastName",params[1]);
                addedMap.put("profession",params[2]);
                addedMap.put("hours",params[3]);
                addedMap.put("username",params[4]);

                finalResult2 = httpParse2.postRequest(addedMap, HttpURL2);
                System.out.println("result = "+ finalResult2);
                return finalResult2;
            }
        }
        AdminAddedInformationFunctionClass userAddedInformationFunctionClass = new AdminAddedInformationFunctionClass();
        AdminAddedInformationFunctionClass.execute(username, password, email);
    }




}
