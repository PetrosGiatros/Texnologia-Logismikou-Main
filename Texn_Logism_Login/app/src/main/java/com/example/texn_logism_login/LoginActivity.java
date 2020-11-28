package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import java.util.HashMap;
import java.util.Set;

public class LoginActivity extends AppCompatActivity {

    private static TextView usernameTextView;
    private TextView passwordTextView;
    private TextView status;
    private Button loginButton;

    String finalResult ;
    boolean CheckEditText;
    String usernameHolder,passwordHolder;
    ProgressDialog progressDialog;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();

    String HttpURL = "http://priapic-blower.000webhostapp.com/userlogin.php";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        usernameTextView = (TextView) findViewById(R.id.editTextUsername);
        passwordTextView = (TextView) findViewById(R.id.editTextTextPassword);
        status = (TextView) findViewById(R.id.status);

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String username = usernameTextView.getText().toString();
                String password = passwordTextView.getText().toString();

                CheckEditTextIsEmptyOrNot();
                if(CheckEditText){
                    UserLoginFunction(username,password);
                }
                else{
                    Toast.makeText(LoginActivity.this,"Please fill form fields.",Toast.LENGTH_LONG).show();
                }
            }

        });

    }

    public static TextView getUsernameTextView() {
        return usernameTextView;
    }

    public void CheckEditTextIsEmptyOrNot(){
        usernameHolder = usernameTextView.getText().toString();
        passwordHolder = passwordTextView.getText().toString();
        if ((TextUtils.isEmpty(usernameHolder)) || (TextUtils.isEmpty(passwordHolder))) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }
    public void UserLoginFunction(String username,String password){
        class UserLoginClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                progressDialog = ProgressDialog.show(LoginActivity.this,"Loading Data",null,true,true);
            }

            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                progressDialog.dismiss();
                if(httpResponseMsg.equalsIgnoreCase("Welcome Admin"))
                {
                    finish();
                    System.out.println("mphka sto onPostExecute IF");
                    Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                    status.setText("Success.");

                    Intent intent = new Intent(LoginActivity.this, AdminActivity.class);
                    //intent.putExtra(username,password);
                    startActivity(intent);
                }
                else if (httpResponseMsg.equalsIgnoreCase("Welcome User"))
                {
                    finish();
                    Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                    status.setText("Success.");
                    Intent intent = new Intent(LoginActivity.this, UserActivity.class);
                    startActivity(intent);
                }
                else{
                    status.setText("Login Failed.");
                    System.out.println("Error sto onPostExecute");
                    Toast.makeText(LoginActivity.this,httpResponseMsg,Toast.LENGTH_LONG).show();
                }
            }
            @Override
            protected String doInBackground(String... params) {

                hashMap.put("username",params[0]);
                hashMap.put("password",params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        UserLoginClass userLoginClass = new UserLoginClass();
        userLoginClass.execute(username,password);
        }
    }







