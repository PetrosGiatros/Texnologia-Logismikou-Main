package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {


        public String FirstName;
        public String LastName;
        public int hourWorking,id;
        public String profession;

        public User(String firstName, String lastName, int hourWorking, int id, String profession) {
            FirstName = firstName;
            LastName = lastName;
            this.hourWorking = hourWorking;
            this.id = id;
            this.profession = profession;
        }





}
