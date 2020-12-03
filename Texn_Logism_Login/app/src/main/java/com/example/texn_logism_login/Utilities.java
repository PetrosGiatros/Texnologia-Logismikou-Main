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

    public void saveSchedule(User[] userArray, int schedule[][],int rows,int columns,int shiftType,int numOfShifts) {
        int day = 1;
        boolean hasChangedDay = false;
        int shiftCount = 0;
        String shift = "";
        for (int i = 0; i < columns ; i = i + 8)
        {
            shiftCount++;
            if (shiftCount == 3)
            {
                hasChangedDay = true;
            }
            for (int j = 0; j < rows; j++)
            {
                if (shiftCount == 1)
                {
                    shift = " Morning Shift: ";
                }
                else if (shiftCount == 2)
                {
                    shift = " Afternoon Shift: ";
                }
                else if(shiftCount == 3)
                {
                    shift = " Graveyard Shift: ";
                }

                System.out.println("Day " + day + " " + shift + ":  Employee  " + schedule[j][i]);
            }
            if (hasChangedDay)
            {
                day++;
                hasChangedDay = false;
                shiftCount = 0;
            }
        }
    }






}

