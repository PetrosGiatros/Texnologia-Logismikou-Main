package com.example.texn_logism_login;

import android.os.StrictMode;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection extends AppCompatActivity {

    public static Connection createConnection() {
        Connection con = null;
        String url = "jdbc:mysql://localhost/loginform_test?useUnicode=true&useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&serverTimezone=UTC";

        String username ="root";
        String password ="root";

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        System.out.println("In DBConnection.java class ");
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
            con=DriverManager.getConnection(url,username,password);
            System.out.println("Printing connection object "+ con);

        }catch (Exception e) {
            e.printStackTrace();
        }
        return con;
    }



}
