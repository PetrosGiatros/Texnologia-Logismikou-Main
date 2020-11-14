package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


public class LoginActivity extends AppCompatActivity {

        private TextView usernameTextView, passwordTextView;
        private Button loginButton;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        loginButton = (Button) findViewById(R.id.buttonLogin);
        usernameTextView= (TextView) findViewById(R.id.editTextUsername);
        passwordTextView = (TextView) findViewById(R.id.editTextTextPassword);

        LoginBean loginbean = new LoginBean();
        LoginDao dao = new LoginDao();


        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    loginbean.setUsername(String.valueOf(usernameTextView.getText()));
                    loginbean.setPassword(String.valueOf(passwordTextView.getText()));

                    String userValid = dao.authEmployer(loginbean);
                    if(userValid=="Success"){
                        System.out.println("Egine Validation");
                    }
                    else if(userValid=="Invalid Credentials"){
                        System.out.println("Lathos stoixeia, prospathistes jana.");
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
        });


    }




}