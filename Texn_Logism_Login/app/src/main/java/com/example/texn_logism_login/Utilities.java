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
        boolean hasMorning=false,hasAfternoon=false,hasMidnight=false;


        SaveScheduleActivity saveSchedulePHP=new SaveScheduleActivity();
        StringBuilder builder = new StringBuilder("");
        String textMorning = builder.toString();
        String textAfternoon = builder.toString();
        String textMidnight = builder.toString();
        System.out.println("businnes type: "+businessType);

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
                    builder.append(workingEmployee).append(" ");
                    textMorning=builder.toString();
                    hasMorning=true;
                    shiftName="Morning";
                    shift = " Morning Shift: ";

                }
                else if (shiftCount == 2)
                {
                    workingEmployee=schedule[j][i];
                    builder.append(workingEmployee).append(" ");
                    textAfternoon=builder.toString();
                    hasAfternoon=true;
                    shiftName="Afternoon";
                    shift = " Afternoon Shift: ";

                }
                else if(shiftCount == 3)
                {
                    workingEmployee=schedule[j][i];
                    builder.append(workingEmployee).append(" ");
                    textMidnight=builder.toString();
                    hasMidnight=true;
                    shiftName="Midnight";
                    shift = " Midnight Shift: ";

                }

                System.out.println("Day " + day + " " + shift + ":  Employee  " + schedule[j][i]);



                //builder.append(workingEmployee).append(" ");
                //textMorning=builder.toString();
                /*if(j==rows-1){
                    //allagh shift
                    System.out.println("TextTo Send: "+text);
                    saveSchedulePHP.saveScheduleActivity(shiftName,text,hasMorning,hasAfternoon,hasMidnight);
                    builder.setLength(0);
                }*/

            }
            if (hasChangedDay)
            {
                saveSchedulePHP.saveScheduleActivity(shiftName,textMorning,textAfternoon,textMidnight,hasMorning,hasAfternoon,hasMidnight);
                builder.setLength(0);


                hasMorning=false;
                hasAfternoon=false;
                hasMidnight=false;
                day++;
                hasChangedDay = false;
                shiftCount = 0;
                shift="";
            }
        }

    }






}

