package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.Random;

public class Utilities extends AppCompatActivity {
    public static User userObjects[];


    public int getEmployeeWithFewestHours(User[] userArray) {
        final int min = 0;
        final int max = userArray.length - 1;
        int randomNum = new Random().nextInt((max - min) + 1) + min;

        int index = randomNum;
        int hours = userArray[randomNum].totalHours;
        for (int i = 0; i < userArray.length; i++) {
            if (userArray[i].totalHours > hours) {
                index = i;
                hours = userArray[i].totalHours;

            }
            //System.out.println("Index in for = " + index);
        }
        return (index);
    }


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

    public void saveSchedule(User[] userArray, int schedule[][],int rows,int columns,int businessType) {
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

    static public void initializeUserObjects(int length)
    {
        userObjects = new User[length];
    }
    static public void copyUsersArray(User userArrayArg[])
    {
        for (int i = 0; i < userObjects.length; i++)
        {
            userObjects[i] = userArrayArg[i];
        }
    }





}

