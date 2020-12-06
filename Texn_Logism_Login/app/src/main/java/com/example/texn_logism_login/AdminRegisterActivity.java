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

public class AdminRegisterActivity extends AppCompatActivity {

    private TextView usernameRegTextView, passwordRegTextView, emailRegTextView;
    private Button submitButton,backButton;
    ProgressDialog progressDialog;
    HashMap<String,String> regMap = new HashMap<>();
    HashMap<String,String> addedMap =new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String finalResult;
    String usernameHolder,passwordHolder,emailHolder;
    boolean CheckEditText;
    String HttpURL = "http://priapic-blower.000webhostapp.com/getid.php";
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_register_form);


        usernameRegTextView = (TextView) findViewById(R.id.editTextUsernameReg);
        passwordRegTextView = (TextView) findViewById(R.id.editTextPasswordReg);
        emailRegTextView = (TextView) findViewById(R.id.editTextEmailReg);




        submitButton = (Button) findViewById(R.id.buttonSubmitReg);
        backButton = (Button) findViewById(R.id.ButtonBackReg);
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                String username = usernameRegTextView.getText().toString();
                String password = passwordRegTextView.getText().toString();
                String email = emailRegTextView.getText().toString();

                CheckEditTextIsEmptyOrNot();
                if (CheckEditText) {
                    AdminRegisterFunction(username, password, email);
                } else {
                    Toast.makeText(AdminRegisterActivity.this, "Please fill form fields.", Toast.LENGTH_LONG).show();
                }
                Intent intent = new Intent(AdminRegisterActivity.this, AdminRegisterActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);
            }

        });

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(AdminRegisterActivity.this, LoginActivity.class);
                //intent.putExtra(username,password);
                startActivity(intent);

            }
        });
    }
    public void CheckEditTextIsEmptyOrNot(){
        usernameHolder = usernameRegTextView.getText().toString();
        passwordHolder = passwordRegTextView.getText().toString();
        emailHolder = emailRegTextView.getText().toString();



        if ((TextUtils.isEmpty(usernameHolder)) || (TextUtils.isEmpty(passwordHolder)) || (TextUtils.isEmpty(passwordHolder))) {
            CheckEditText = false;
        } else {
            CheckEditText = true;
        }
    }


    public void AdminRegisterFunction(String username,String password,String email){

        class AdminRegisterFunctionClass extends AsyncTask<String,Void,String> {
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

                System.out.println("username login:"+username);
                Toast.makeText(AdminRegisterActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                regMap.put("username",params[0];
                regMap.put("password",params[1]);
                regMap.put("email",params[2]);


                finalResult = httpParse.postRequest(regMap, HttpURL);
                System.out.println("result = "+ finalResult);
                return finalResult;
            }
        }
        AdminRegisterFunctionClass AdminRegisterFunctionClass = new AdminRegisterFunctionClass();
        AdminRegisterFunctionClass.execute(username,password,email);
    }





}
