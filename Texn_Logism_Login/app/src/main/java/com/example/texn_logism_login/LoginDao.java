package com.example.texn_logism_login;


import androidx.appcompat.app.AppCompatActivity;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import com.example.texn_logism_login.LoginBean;

public class LoginDao extends AppCompatActivity {
    public String authEmployer(LoginBean loginBean) {
        Connection connect = null;
        Statement statement = null;
        ResultSet resultSet = null;


        try {
            String username = LoginBean.getUsername();
            String password = LoginBean.getPassword();

            String usernameDB = "";
            String passwordDB = "";

            connect = DBConnection.createConnection();

            statement = connect.createStatement();
            resultSet = statement.executeQuery("select username,password from employer");

            while (resultSet.next()) {
                usernameDB = resultSet.getString("username");
                passwordDB = resultSet.getString("password");
                if (username.equals(usernameDB) && password.equals(passwordDB)) {
                    System.out.println("Success");
                    return "Success";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Invalid Credentials";
    }
}