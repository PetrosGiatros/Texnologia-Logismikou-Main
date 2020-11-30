package com.example.texn_logism_login;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Random;

public class Utilities extends AppCompatActivity
{
    public int getEmployeeWithFewestHours(User[] userArray)
    {
        final int min = 0;
        final int max = userArray.length-1;
        int randomNum = new Random().nextInt((max - min) + 1) + min;

        int index = randomNum;
        int hours = userArray[randomNum].totalHours;
        for (int i = 0; i < userArray.length; i++)
        {
            if (userArray[i].totalHours > hours)
            {
                index = i;
                hours = userArray[i].totalHours;

            }
            System.out.println("Index in for = " + index);
        }
        return (index);
    }

}
