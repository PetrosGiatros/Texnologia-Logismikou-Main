package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {


        public String FirstName;
        public String LastName;
        public int shiftHours,id,totalHours,hourWorked=0;
        public String profession,shiftType;
        public boolean hasShift=true;


        public User(String firstName, String lastName, int shiftHours, int id, String profession)
        {
            FirstName = firstName;
            LastName = lastName;
            this.shiftHours = shiftHours;
            this.id = id;
            this.profession = profession;

        }

    public void setTotalHours(String scheduleType,String shiftType)
    {
        if (scheduleType == "Weekly") {
            totalHours = 5 * (Integer.valueOf(shiftType));
        } else if (scheduleType == "Monthly") {
            totalHours = 20 * (Integer.valueOf(shiftType));
        } else if (scheduleType == "Trimester") {
            totalHours = 60 * (Integer.valueOf(shiftType));
        } else if (scheduleType == "Semester"){
            totalHours = 120 * (Integer.valueOf(shiftType));
        }

    }
    public boolean isAvailable()
    {
        return(true);
    }
}
