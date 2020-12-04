package com.example.texn_logism_login;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class RequestActivity extends AppCompatActivity
{

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.requests_form);
        Utilities util = new Utilities();
    }
}
