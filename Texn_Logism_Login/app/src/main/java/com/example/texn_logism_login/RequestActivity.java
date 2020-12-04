package com.example.texn_logism_login;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity
{
    String HttpURL = "http://priapic-blower.000webhostapp.com/getRequest.php";
    private Button submitLeaveRequestButton;
    private EditText leaveDays;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_form);
        submitLeaveRequestButton=(Button)findViewById(R.id.leaveButton);
        EditText leaveDays = (EditText)findViewById(R.id.leaveDays);
        String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


        submitLeaveRequestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                int number = Integer.parseInt(leaveDays.getText().toString());
                System.out.println("Leave Days: " + number + "Username " + loggedInUsername);
            }
        });

    }
}
