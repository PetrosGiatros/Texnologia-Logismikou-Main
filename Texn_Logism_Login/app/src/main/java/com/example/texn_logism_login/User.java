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
     public int totalHours, regulationHoursWorked = 0,timesWorked = 0,overtimeHours;
     public boolean hasShift = true;

     public boolean workMorning,workAfternoon,workMidnight;

    /**
     * <h1>User</h1>
     * Sets the user's data.
     * @param id Is the employee's ID
     * @param firstName Is the employee's first name
     * @param lastName Is the employee's last name
     * @param profession Is the employee's profession
     * @param shiftHours Is the employee's shift Hourse preference.
     */
     public User(int id, String firstName, String lastName, String profession, int shiftHours,boolean workMorning,boolean workAfternoon,boolean workMidnight) {
         this.id = id;
         this.firstName = firstName;
         this.lastName = lastName;
         this.profession = profession;
         this.shiftHours = shiftHours;
         this.workMorning = workMorning;
         this.workAfternoon = workAfternoon;
         this.workMidnight = workMidnight;
         this.overtimeHours = 0;

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
    public boolean isOnlyShift(int curShift)
    {
        switch(curShift)
        {
            case 1:
                if ((workMorning == true) && (workAfternoon == false) && (workMidnight == false))
                {
                    return(true);
                }
                break;
            case 2:
                if ((workAfternoon == true) && (workMorning == false) && (workMidnight == false))
                {
                    return(true);
                }
                break;
            case 3:
                if ((workMidnight == true) && (workMorning == false) && (workAfternoon == false))
                {
                    return(true);
                }
                break;
            default:
                return (false);
         }
         return (false);
    }

    public boolean isAvailable() {
        return (true);
    }
}
