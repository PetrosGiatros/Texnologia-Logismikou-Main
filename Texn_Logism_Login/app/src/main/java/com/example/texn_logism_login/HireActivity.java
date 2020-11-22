package com.example.texn_logism_login;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;

public class HireActivity extends AppCompatActivity {

    private TextView usernameHireTextView, passwordHireTextView, emailHireTextView;
    private Button submitButton,backButton;
    ProgressDialog progressDialog;
    HashMap<String,String> hireMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult ;
    String usernameHolder,passwordHolder,emailHolder;
    boolean CheckEditText;
    String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getid.php";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hire_form);

        usernameHireTextView = (TextView) findViewById(R.id.editTextUsernameHire);
        passwordHireTextView = (TextView) findViewById(R.id.editTextPasswordHire);
        emailHireTextView = (TextView) findViewById(R.id.editTextEmailHire);

        submitButton = (Button) findViewById(R.id.buttonSubmitHire);
        backButton = (Button) findViewById(R.id.buttonBackHire);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameHireTextView.getText().toString();
                String password = passwordHireTextView.getText().toString();
                String email = emailHireTextView.getText().toString();

                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    UserRegisterFunction(loggedInUsername, username, password, email);
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
    public void CheckEditTextIsEmptyOrNot(){
        usernameHolder = usernameHireTextView.getText().toString();
        passwordHolder = passwordHireTextView.getText().toString();
        emailHolder = emailHireTextView.getText().toString();
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




}
