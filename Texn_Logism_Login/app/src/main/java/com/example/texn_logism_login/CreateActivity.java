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


        int type;
        int[][] schedule;
        if (SelectedScheduleType == "Weekly") {
            type=5;
            schedule = new int[Integer.valueOf(SelectedShiftType)*type*numOfShifts][7];
        } else if (SelectedScheduleType == "Monthly") {
            type=20;
            schedule = new int[Integer.valueOf(SelectedShiftType)*type*numOfShifts][7];
        } else if (SelectedScheduleType == "Trimester") {
            type=60;
            schedule = new int[Integer.valueOf(SelectedShiftType)*type*numOfShifts][7];
        } else {
            type=120;
            schedule = new int[Integer.valueOf(SelectedShiftType)*type*numOfShifts][7];
        }
        Random ran= new Random();


        User selectedUsers[] = new User[Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        int totalTypeHours=Integer.valueOf(SelectedShiftType)*type*numOfShifts;
        int k=0;
        int employeeAmountPerShift=2;
        int recentAmountOfEmployees=0;

        while((totalTypeHours)>0){

            int randomNum ;
            int iCheck=1;
            int thisDay=7;
            for(int i =0;i<users.length;i=i+iCheck){
                randomNum = ran.nextInt(users.length-1);
                selectedUsers[k]=users[randomNum];
                for(int j = thisDay;j<Integer.valueOf(SelectedShiftType)*type*numOfShifts;j = j + 8){
                    iCheck=1;
                    if((selectedUsers[k].hasShift==true) && (selectedUsers[k].totalHours>0)) {
                        for (int z = j; z >= j - 7; z-- )
                        {
                            schedule[i][z] = selectedUsers[k].id;
                            selectedUsers[k].hasShift=false;
                            users[k].hasShift=false;
                            selectedUsers[k].totalHours=selectedUsers[k].totalHours-Integer.valueOf(SelectedShiftType);
                        }
                        System.out.println("schedule test:" + schedule[i][j]);
                        recentAmountOfEmployees++;
                        iCheck=1;

                    }else{
                        iCheck=0;
                        thisDay=j;
                       /* randomNum = ran.nextInt(users.length-1);
                        selectedUsers[k]=users[randomNum];*/

                        break;
                    }
                    if(employeeAmountPerShift == recentAmountOfEmployees){
                        System.out.println("Reached max people per shift.");
                        i=0;
                        iCheck=1;

                        randomNum = ran.nextInt(users.length-1);
                        selectedUsers[k]=users[randomNum];
                        recentAmountOfEmployees=0;

                    }else{
                        System.out.println("Have not yet reached max people per shift.");
                        iCheck=1;
                        thisDay=j;
                        break;
                    }

                }
                totalTypeHours=totalTypeHours-Integer.valueOf(SelectedShiftType);
                System.out.println("ID:"+schedule[i][0]);
            }
            k++;
        }

    }







}
