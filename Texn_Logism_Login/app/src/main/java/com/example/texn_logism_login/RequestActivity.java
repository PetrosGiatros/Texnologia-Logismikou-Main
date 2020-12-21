package com.example.texn_logism_login;



import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;

/**
 * RequestActivity.java is available only for users (not admins) and is responsible for <b>sending leave requests</b> to the admin that the user is connected to
 */
public class RequestActivity extends AppCompatActivity
{
    private Button submitLeaveRequestButton;
    private EditText leaveDays;

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getRequest.php";

    /**
     * <h1>Leave Function</h1>
     * leaveFunction is responsible for claryfing the username and the days needed for the user that wants to request a leave.
     * @param username username of the user
     * @param days Number of days that the user wants to leave
     */
    public void leaveFunction(String username,String days) {
        class leaveClass extends AsyncTask<String, Void, String> {
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
                hashMap.put("username", params[0]);
                hashMap.put("leaveDays", params[1]);
                finalResult = httpParse.postRequest(hashMap, HttpURL);
                return finalResult;
            }
        }
        leaveClass leaveObject = new leaveClass();
        leaveObject.execute(username,days);
    }



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
                String requestedLeaveDays = leaveDays.getText().toString();
                leaveFunction(loggedInUsername,requestedLeaveDays);
            }
        });

    }

}
