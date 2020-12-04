package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Utilities extends AppCompatActivity {
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
        SaveScheduleActivity saveScheduleActivity=new SaveScheduleActivity();
        StringBuilder builder = new StringBuilder("");
        String text = builder.toString();
        System.out.println("businnes type: "+businessType);
        for (int i = 0; i < columns ; i = i + 8)
        {
            shiftCount++;
            if (shiftCount == businessType)
            {

                hasChangedDay = true;
            }
            for (int j = 0; j < rows; j++)
            {
                if (shiftCount == 1)
                {
                    shiftName="Morninng";
                    shift = " Morning Shift: ";
                    //return shiftName;
                }
                else if (shiftCount == 2)
                {
                    shiftName="Afternoon";
                    shift = " Afternoon Shift: ";
                    //return shiftName;
                }
                else if(shiftCount == 3)
                {
                    shiftName="Graveyard";
                    shift = " Graveyard Shift: ";
                    //return shiftName;
                }

                System.out.println("Day " + day + " " + shift + ":  Employee  " + schedule[j][i]);
                int workingEmployee;
                workingEmployee=schedule[j][i];

                builder.append(workingEmployee).append(" ");
                text=builder.toString();

                //saveScheduleActivity.saveSchedule(shiftName,text);
            }
            if (hasChangedDay)
            {
                day++;
                hasChangedDay = false;
                shiftCount = 0;
                shift="";
            }
        }
        //return shiftName;

    }






}

