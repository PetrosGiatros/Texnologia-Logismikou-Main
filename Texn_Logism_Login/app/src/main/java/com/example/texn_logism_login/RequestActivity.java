package com.example.texn_logism_login;



import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;


public class RequestActivity extends AppCompatActivity
{
    private Button submitLeaveRequestButton;
    private EditText leaveDays;

    String finalResult ;
    HashMap<String,String> hashMap = new HashMap<>();
    HttpParse httpParse = new HttpParse();
    String HttpURL = "http://priapic-blower.000webhostapp.com/getRequest.php";


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
        EditText leaveDays = (EditText)findViewById(R.id.leaveDays);
        String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();


       

    }

}
