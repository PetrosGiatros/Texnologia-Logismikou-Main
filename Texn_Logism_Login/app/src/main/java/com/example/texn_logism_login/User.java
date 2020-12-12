package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

public class User extends AppCompatActivity {

    //Current constructor arguments.
    static public int shiftHours, id;
    static public String profession;

    // Current irrelevant arguments.
    static public int totalHours, hoursWorked = 0,timesWorked = 0;
    static public boolean hasShift = true;


     public User(int id, int shiftHours, String profession) {
         this.id = id;
         this.shiftHours = shiftHours;
         this.profession = profession;

    }

    public void setTotalHours(String scheduleType, String shiftType) {
        if (scheduleType == "Weekly") {
            totalHours = 5 * (Integer.valueOf(shiftType));
        } else if (scheduleType == "Monthly") {
            totalHours = 20 * (Integer.valueOf(shiftType));
        } else if (scheduleType == "Trimester") {
            totalHours = 60 * (Integer.valueOf(shiftType));
        } else if (scheduleType == "Semester") {
            totalHours = 120 * (Integer.valueOf(shiftType));
        }

    }

    public boolean isAvailable() {
        return (true);
    }
}
