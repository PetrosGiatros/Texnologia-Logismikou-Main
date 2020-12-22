package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;
/**
 * The class that holds the data for the user Objects.
 */
public class User extends AppCompatActivity {

    //Current constructor arguments.
     public int shiftHours, id;
     public String firstName,lastName,profession;

    // Current irrelevant arguments.
     public int totalHours, hoursWorked = 0,timesWorked = 0;
     public boolean hasShift = true;


     public User(int id, String firstName, String lastName, String profession, int shiftHours) {
         this.id = id;
         this.firstName = firstName;
         this.lastName = lastName;
         this.profession = profession;
         this.shiftHours = shiftHours;

    }
    /**
     * <h1>Set Total Hours</h1>
     * Calculates the amount of hours each user will have to work based on the scheduleType and shiftType.
     * @param scheduleType Is the type of Schedule selected (Weekly, Monthly, Trimester, Semester)
     * @param shiftType Is the type of shift selected (4h or 8h)
     */
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
