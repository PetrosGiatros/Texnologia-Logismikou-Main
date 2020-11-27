package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {


        public String FirstName;
        public String LastName;
        public int shiftHours,id,totalHours,hourWorked=0;
        public String profession;
        boolean hasShift=true;


        public User(String firstName, String lastName, int shiftHours, int id, String profession) {
            FirstName = firstName;
            LastName = lastName;
            this.shiftHours = shiftHours;
            this.id = id;
            this.profession = profession;

        }





}
