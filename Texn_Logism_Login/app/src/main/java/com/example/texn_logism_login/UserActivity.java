package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;
/**
 * UserActivity is available only for users (not admins) and is the <b>main menu</b> of the connected user.
 */
public class UserActivity extends AppCompatActivity {

    private Button exitButton;
    private Button button_view2;
    private Button requestButton;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_form);
        exitButton = (Button) findViewById(R.id.buttonExit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    System.exit(0);
            }
        });
        button_view2 = (Button) findViewById(R.id.button_view2);
        button_view2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, CalendarActivity.class);
                startActivity(intent);
            }
        });
        requestButton = (Button) findViewById(R.id.requestButton);
        requestButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserActivity.this, RequestActivity.class);
                startActivity(intent);
               // finish();
            }
        });

    }
}
