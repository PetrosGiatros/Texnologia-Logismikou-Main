package com.example.texn_logism_login;

public class LoginBean {
    private static String username;
    private static String password;
    //private static String Role;

    public static String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public static String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
}
