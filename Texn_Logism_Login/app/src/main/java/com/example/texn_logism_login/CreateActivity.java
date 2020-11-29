package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Struct;
import java.util.Random;


public class CreateActivity  extends AppCompatActivity {
    private Button buttonCreateSchedule;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_form);

        buttonCreateSchedule=(Button)findViewById(R.id.buttonCreateSchedule);

        Spinner spinnerScheduleType = findViewById(R.id.spinnerScheduleType);
        Spinner spinnerShiftType = findViewById(R.id.spinnerShiftType);
        Spinner spinnerProfession = findViewById(R.id.spinnerProfession);
        String[] ScheduleTypes = new String[]{"Weekly", "Monthly", "Trimestser","Semester"};
        String[] ShiftTypes = new String[]{"8", "4"};
        String[] Profession = new String[]{"Programmer", "Analyst","Manager"};


        ArrayAdapter<String> adapter ;
        adapter = new ArrayAdapter<>(CreateActivity.this, android.R.layout.simple_spinner_dropdown_item, ScheduleTypes);
        spinnerScheduleType.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShiftTypes);
        spinnerShiftType.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Profession);
        spinnerProfession.setAdapter(adapter3);

        String SelectedScheduleType = spinnerScheduleType.getSelectedItem().toString();
        String SelectedShiftType = spinnerShiftType.getSelectedItem().toString();
        String SelectedProfession = spinnerProfession.getSelectedItem().toString();


        buttonCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                createSchedule(SelectedScheduleType,SelectedShiftType,SelectedProfession);




            }
        });

    }

    public void createSchedule(String SelectedScheduleType,String SelectedShiftType,String SelectedProfession){

        int numOfShifts = 1;
        User kitsos=new User("ki","tsos",8,1,"Programmer");
        User panagiwtis = new User("pana","giwtis",8,2,"Analyst");
        User konstantinos = new User("konsta","ntinos",8,3,"Manager");
        User kwstas=new User("kw","stas",8,4,"Programmer");
        User kotsos=new User("ko","tsos",8,5,"Analyst");
        User giannhs=new User("gian","nhs",8,6,"Programmer");
        User petran=new User("pe","tran",8,7,"Manager");


        User users[] = new User[7];
        users[0] = kitsos;
        users[1] = panagiwtis;
        users[2] = konstantinos;
        users[3] = kwstas;
        users[4] = kotsos;
        users[5] = giannhs;
        users[6] = petran;

        for (int i = 0; i < users.length; i++)
        {
            users[i].setTotalHours(SelectedScheduleType,SelectedShiftType);
        }

        int employeeAmountPerShift=2;
        int type;
        int[][] schedule;
        if (SelectedScheduleType == "Weekly") {
            type=5;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        } else if (SelectedScheduleType == "Monthly") {
            type=20;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        } else if (SelectedScheduleType == "Trimester") {
            type=60;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        } else {
            type=120;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        }

        for (int row = 0; row < schedule.length; row++)//Cycles through rows
        {
            for (int col = 0; col < schedule[row].length; col++)//Cycles through columns
            {
               schedule[row][col]=-1;//change the %5d to however much space you want
            }

        }

        final int min = 0;
        final int max = 6;
        int randomNum = new Random().nextInt((max - min) + 1) + min;

        boolean isOver = false;

        User selectedUsers[] = new User[Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        int totalTypeHours=Integer.valueOf(SelectedShiftType)*type*numOfShifts;
        int k=0;

        int recentAmountOfEmployees=0;
        System.out.println("prin while");
        while((totalTypeHours)>0){
            System.out.println("mesa sto while");

            int iCheck=1;
            int thisDay=7;
            int j =0;
            for(int i =0;i<users.length;i=i+iCheck){
                randomNum = new Random().nextInt((max - min) + 1) + min;
                //selectedUsers[k]=users[randomNum];
                for(j = thisDay;j<Integer.valueOf(SelectedShiftType)*type*numOfShifts;j = j + 8){
                    iCheck=1;
                    if((users[randomNum].hasShift==true) && (users[randomNum].totalHours>0)) {
                        for (int z = j; z >= j - 7; z-- )
                        {
                            schedule[i][z] = users[randomNum].id;
                            users[randomNum].hasShift=false;
                            k++;
                            System.out.println("o user:"+users[randomNum].FirstName+"  mphke ston pinaka shedule["+i+"]["+z+"]"+"me total horurs:"+users[randomNum].totalHours);

                        }
                        if (j == 39)
                        {
                            isOver = true;
                        }
                        users[randomNum].totalHours=users[randomNum].totalHours-Integer.valueOf(SelectedShiftType);
                        recentAmountOfEmployees++;
                        System.out.println("Total Hours: "+users[randomNum].totalHours);
                        iCheck=1;

                    }else{
                        iCheck=0;
                        thisDay=j;
                        if (j == 39)
                        {
                            isOver = true;
                        }

                        System.out.println("Den mphke o: "+users[randomNum].FirstName);
                       /* try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        break;
                    }
                    if(employeeAmountPerShift == recentAmountOfEmployees){
                        System.out.println("Reached max people per shift.");
                        i=0;
                        iCheck=0;
                        totalTypeHours=totalTypeHours-Integer.valueOf(SelectedShiftType);
                        recentAmountOfEmployees=0;
                        k++;
                        System.out.println("Allagh meras");
                        System.out.println("upoloipomenes totaltype hours: "+ totalTypeHours);
                        for(int q = 0;q<users.length;q++){
                            users[i].hasShift=true;
                        }


                    }else{
                        System.out.println("Have not yet reached max people per shift.");
                        iCheck=1;
                        thisDay=j;

                        break;
                    }

                }
                if (isOver == true)
                {
                    break;
                }
                //Something to break out of I
            }
            if (isOver == true)
            {
                break;
            }
        }

        for (int row = 0; row < schedule.length; row++)//Cycles through rows
        {
            for (int col = 0; col < schedule[row].length; col++)//Cycles through columns
            {
                System.out.printf("%5d", schedule[row][col]); //change the %5d to however much space you want
            }
            System.out.println(); //Makes a new row
        }
//This allows you to print the array as matrix



    }







}



