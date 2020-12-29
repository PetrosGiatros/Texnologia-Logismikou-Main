package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Random;
/**
 * Utilities.Java serves as a support class which contains various data and methods.
 */
public class Utilities extends AppCompatActivity {
    public static User userObjects[];
    public static ArrayList<User> usersOnShiftList = new ArrayList<User>();
    public User failUser = new User(-1,"Fail","Fail","Fail",-1,true,true,true);

    /**
     *  <h1>Get Employees With Fewest Hours</h1>
     *  This mehtod receives an array of Users and randomly selects the one with the fewest hours worked.
     *
     * @param userArray An array of objects that include the id, firstName,lastName,profession and shiftHours of each user.
     * @return
     */
    public User getEmployeeDefaultMode(User[] userArray,int shift) {
        boolean terminateFlag = false;
        int min = 0, max,randomNum,hours,index;


        ArrayList<User> possibleUsers = new ArrayList<User>();
        possibleUsers.clear();
        possibleUsers = getEmployeesForShift(shift);
        do {

            max = possibleUsers.size() - 1;
            randomNum = new Random().nextInt((max - min) + 1) + min;
            index = randomNum;
            hours = possibleUsers.get(index).totalHours;
            for (int i = 0; i < possibleUsers.size(); i++)
            {
                if(possibleUsers.get(i).totalHours > hours)
                {
                    index = i;
                    hours = possibleUsers.get(i).totalHours;
                }
            }
            if (isEmployeeValid(possibleUsers.get(index)))
            {
                if (!possibleUsers.get(index).isOnlyShift(shift))
                {
                    for (int i = 0; i < possibleUsers.size();i++)
                    {
                        if((possibleUsers.get(i).totalHours == possibleUsers.get(index).totalHours) && (possibleUsers.get(i).isOnlyShift(shift)))
                        {
                            index = i;
                            hours = possibleUsers.get(i).totalHours;
                        }
                    }
                }
            }
            if ((canEmployeeBeSelectedBasedOnShifts(shift)== false) || (canEmployeeBeSelectedBasedOnHours(shift) == false))
            {
                System.out.println("Failed to find user for shift " + shift);
                return (failUser);
            }
        }while (!isEmployeeValid(possibleUsers.get(index)));
        System.out.println("Managed to return user");
        return (possibleUsers.get(index));
    }
    public User getEmployeePassiveMode(User[] userArray) {
        /*boolean terminateFlag = false;
        final int min = 0;
        int index;
        final int max = userArray.length - 1;
        do
        {
            int randomNum = new Random().nextInt((max - min) + 1) + min;
            index = randomNum;
            int hours = userArray[randomNum].totalHours;
            for (int i = 0; i < userArray.length; i++) {
                if (userArray[i].totalHours > hours) {
                    index = i;
                    hours = userArray[i].totalHours;

                }
                //System.out.println("Index in for = " + index);
            }
            if(isEmployeeValid(userArray[index]))
            {
                terminateFlag = true;

            }
            if (((canEmployeeBeSelectedBasedOnHours() == false) || (canEmployeeBeSelectedBasedOnShifts()==false)) && (terminateFlag == false))
            {
                return(failUser);
            }
        }while (terminateFlag==false);

        return(userArray[index]);*/
        return(failUser);
    }

    public User getEmployeeAggressiveMode(User[] userArray)
    {
        /*boolean terminateFlag = false;
        final int min = 0;
        int index;
        final int max = userArray.length - 1;
        do
        {
            int randomNum = new Random().nextInt((max - min) + 1) + min;
            index = randomNum;
            int hours = userArray[randomNum].totalHours;
            for (int i = 0; i < userArray.length; i++) {
                if (userArray[i].totalHours > hours) {
                    index = i;
                    hours = userArray[i].totalHours;

                }
                //System.out.println("Index in for = " + index);
            }
            if(isEmployeeValid(userArray[index]))
            {
                terminateFlag = true;
            }
            else
            {
                return (getEmployeeWithFewestOvertime());
            }
        }while (terminateFlag == false);
        return(userArray[index]);*/
        return(failUser);
    }

    public boolean canEmployeeBeSelectedBasedOnShifts(int cShift)
    {
        if (cShift == 1)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workMorning == true)
                {
                    if (userObjects[i].hasShift == true)
                    {
                        return (true);
                    }
                }
            }
        }
        else if (cShift == 2)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workAfternoon == true)
                {
                    if (userObjects[i].hasShift == true)
                    {
                        return (true);
                    }
                }
            }
        }
        else if (cShift == 3)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workMidnight == true)
                {
                    if (userObjects[i].hasShift == true)
                    {
                        return (true);
                    }
                }
                //System.out.println("Can Work Midnight " + userObjects[i].workMidnight + "     hasShift+++++++" +userObjects[i].hasShift);
            }
        }
        return (false);
    }
    public boolean canEmployeeBeSelectedBasedOnHours(int cShift)
    {

        if (cShift == 1)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workMorning == true)
                {
                    if (userObjects[i].totalHours > 0)
                        return (true);
                }
            }
        }
        else if (cShift == 2)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workAfternoon == true)
                {
                    if (userObjects[i].totalHours > 0)
                        return (true);
                }
            }
        }
        else if (cShift == 3)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workMidnight == true)
                {
                    if (userObjects[i].totalHours > 0)
                        return (true);
                }
            }
        }
        return (false);
    }
    public boolean isEmployeeValid(User employee)
    {
        return ((employee.hasShift==true) && (employee.totalHours>0) ? true:false);
    }
    public User getEmployeeWithFewestOvertime() //*Least*
    {
        boolean terminateFlag = false;
        final int min = 0;
        int index;
        final int max = userObjects.length - 1;
        int randomNum = new Random().nextInt((max - min) + 1) + min;
        index = randomNum;
        int overtime = userObjects[randomNum].overtimeHours;
        for (int i = 0; i < userObjects.length; i++) {
            if ((userObjects[i].overtimeHours < overtime) &&(usersOnShiftList.contains(userObjects[i]) != true)) {
                index = i;
                overtime = userObjects[i].overtimeHours;

            }
        }

        userObjects[index].overtimeHours = userObjects[index].overtimeHours + userObjects[index].shiftHours;
        return (userObjects[index]);
    }
    public int getEmployeeAmountOnMorningShift()
    {
        int counter = 0;
        for (int i = 0; i < userObjects.length; i++)
        {
            if (userObjects[i].workMorning == true)
            {
                counter++;
            }

        }
        return(counter);
    }
    public int getEmployeeAmountOnAfternoonShift()
    {
        int counter = 0;
        for (int i = 0; i < userObjects.length; i++)
        {
            if (userObjects[i].workAfternoon == true)
            {
                counter++;
            }

        }
        return(counter);
    }
    public int getEmployeeAmountOnMidnightShift()
    {
        int counter = 0;
        for (int i = 0; i < userObjects.length; i++)
        {
            if (userObjects[i].workMidnight == true)
            {
                counter++;
            }

        }
        return(counter);
    }
    public ArrayList<User> getEmployeesForShift(int cShift)
    {
        ArrayList<User> possibleUsersOnShift = new ArrayList<User>();
        if (cShift == 1)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workMorning == true)
                    possibleUsersOnShift.add(userObjects[i]);
            }
        }
        else if (cShift == 2)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workAfternoon == true)
                    possibleUsersOnShift.add(userObjects[i]);
            }
        }
        else if (cShift == 3)
        {
            for (int i = 0; i < userObjects.length; i++)
            {
                if (userObjects[i].workMidnight == true)
                    possibleUsersOnShift.add(userObjects[i]);
            }
        }
        return(possibleUsersOnShift);
    }

    public void clearUserList()
    {
        usersOnShiftList.clear();
    }



    /**
     * <h1> Display Schedule </h1>
     * This method is used to display the schedule that was created in CreateActivity.java
     *
     * @param userArray An array of objects that include the id, firstName,lastName,profession and shiftHours of each user.
     * @param schedule An array that has the already saved Schedule inside it.
     * @param rows An integer number that contains the number of rows of the previous array and that is used to make editing the schedule table easier.
     * @param columns An integer number that contains the number of columns of the previous array and that is  used to make editing the schedule table easier.
     * @param shiftType Shows if the admin has requested a program based on 4h or 8h shifts.
     */
    public void displaySchedule(User[] userArray, int schedule[][],int rows,int columns,int shiftType) {
        int day = 1;
        for (int i = 0; i < columns ; i = i + shiftType )
        {
            for (int j = 0; j < rows; j++)
            {
               System.out.println("Day " + day + ":  Employee  " + schedule[j][i]);
            }
            day++;
            System.out.println();
        }
    }
    /**
     * <h1> Save Schedule </h1>
     * This method takes the finished schedule from CreateActivity.java and uses some parameters given by the admin in order to place the specific information to the correct column of the online database via PHP files.
     *
     *
     *
     * @param userArray An array of objects that include the id, firstName,lastName,profession and shiftHours of each user.
     * @param schedule An array that has the already saved Schedule inside it.
     * @param rows An integer number that contains the number of rows of the previous array and that is  used to make editing the schedule table easier.
     * @param columns An integer number that contains the number of columns of the previous array and that is  used to make editing the schedule table easier.
     * @param businessType Shows if the admin has requested a 8h/16h/24h program
     */
    public void saveSchedule(User[] userArray, int schedule[][],int rows,int columns,int businessType,boolean saturdayCheck,boolean sundayCheck) {
        int day = 1;
        boolean hasChangedDay = false;
        int shiftCount = 0;
        String shift = "";
        String shiftName="";
        String hasMorning="",hasAfternoon="",hasMidnight="";
        String loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
        Calendar calendarSchedule = Calendar.getInstance();
        String date;
        String currentDay = String.valueOf(calendarSchedule.get(Calendar.DATE));
        String currentMonth = String.valueOf(calendarSchedule.get(Calendar.MONTH)+1);
        String currentYear = String.valueOf(calendarSchedule.get(Calendar.YEAR));


        SaveScheduleActivity saveSchedulePHP=new SaveScheduleActivity();
        SaveScheduleActivity saveNewSchedulePHP=new SaveScheduleActivity();
        StringBuilder builderMorning = new StringBuilder("");
        StringBuilder builderAfternoon = new StringBuilder("");
        StringBuilder builderMidnight = new StringBuilder("");
        String textMorning = builderMorning.toString();
        String textAfternoon = builderAfternoon.toString();
        String textMidnight = builderMidnight.toString();
        System.out.println("business type: "+businessType);

        for (int i = 0; i < columns ; i = i + 8)
        {
            int workingEmployee;
            shiftCount++;
            if (shiftCount == businessType)
            {
                //allagh meras
                hasChangedDay = true;
            }
            int dow = calendarSchedule.get(Calendar.DAY_OF_WEEK);
            if ((dow == Calendar.SATURDAY) && (saturdayCheck == false))
            {
                long millisNext = calendarSchedule.getTimeInMillis() + (86400000);
                calendarSchedule.setTimeInMillis(millisNext);
                currentDay = String.valueOf(calendarSchedule.get(Calendar.DATE));
                currentMonth = String.valueOf(calendarSchedule.get(Calendar.MONTH)+1);
                currentYear = String.valueOf(calendarSchedule.get(Calendar.YEAR));

            }
            if ((dow == Calendar.SUNDAY) && (sundayCheck == false))
            {
                long millisNext = calendarSchedule.getTimeInMillis() + 86400000;
                calendarSchedule.setTimeInMillis(millisNext);
                currentDay = String.valueOf(calendarSchedule.get(Calendar.DATE));
                currentMonth = String.valueOf(calendarSchedule.get(Calendar.MONTH)+1);
                currentYear = String.valueOf(calendarSchedule.get(Calendar.YEAR));
            }
            for (int j = 0; j < rows; j++)
            {
                if (shiftCount == 1)
                {
                    workingEmployee=schedule[j][i];
                    builderMorning.append(workingEmployee).append(" ");
                    hasMorning="yes";
                    shiftName="Morning";
                    shift = " Morning Shift: ";

                }
                else if (shiftCount == 2)
                {
                    workingEmployee=schedule[j][i];
                    builderAfternoon.append(workingEmployee).append(" ");
                    hasAfternoon="yes";
                    shiftName="Afternoon";
                    shift = " Afternoon Shift: ";

                }
                else if(shiftCount == 3)
                {
                    workingEmployee=schedule[j][i];
                    builderMidnight.append(workingEmployee).append(" ");
                    hasMidnight="yes";
                    shiftName="Midnight";
                    shift = " Midnight Shift: ";

                }

                System.out.println("Day " + day + " " + shift + ":  Employee  " + schedule[j][i]);

                String employeeID;

                employeeID=String.valueOf(schedule[j][i]);

                date=currentDay+"-"+currentMonth+"-"+currentYear;

                System.out.println("tha stalthoun ta dedomena: "+shiftName+" "+employeeID+" "+hasMorning+" "+hasAfternoon+" "+hasMidnight+" "+date+" "+loggedInUsername);

                saveNewSchedulePHP.saveNewScheduleActivity(shiftName,employeeID,hasMorning,hasAfternoon,hasMidnight,date,loggedInUsername);




               /* if(j==rows-1){
                    //allagh shift
                    System.out.println("TextTo Send: "+text);
                    saveSchedulePHP.saveScheduleActivity(shiftName,text,hasMorning,hasAfternoon,hasMidnight);
                    builder.setLength(0);
                }*/

            }
            if (hasChangedDay)
            {
                System.out.println("H prohgoumenh hmeromhnia htan: "+ currentDay + " " +currentMonth+ " " +currentYear);
                long millisNext = calendarSchedule.getTimeInMillis() + 86400000;
                calendarSchedule.setTimeInMillis(millisNext);

                currentDay = String.valueOf(calendarSchedule.get(Calendar.DATE));
                currentMonth = String.valueOf(calendarSchedule.get(Calendar.MONTH)+1);
                currentYear = String.valueOf(calendarSchedule.get(Calendar.YEAR));

                System.out.println("H hmeromhnia allakse se: "+ currentDay + " " +currentMonth+ " " +currentYear);

                builderMorning.setLength(0);
                builderAfternoon.setLength(0);
                builderMidnight.setLength(0);

                hasMorning="";
                hasAfternoon="";
                hasMidnight="";
                day++;
                hasChangedDay = false;
                shiftCount = 0;
                shift="";
            }
        }




    }
    /** <h1>Initialize User Objects </h1>
     * This method is used to initialize the Array used to store the User objects pulled from the database.
     * @param length is the number of employees
     */
    static public void initializeUserObjects(int length)
    {
        userObjects = new User[length];
    }
    /**<h1>Copy Users Array </h1>
     * This method is used to copy the scope-restricted UserArray into a global Array of User objects.
     * @param userArrayArg is an array containing all the employees
     */
    static public void copyUsersArray(User userArrayArg[])
    {
        for (int i = 0; i < userObjects.length; i++)
        {
            userObjects[i] = userArrayArg[i];
        }
    }





}

