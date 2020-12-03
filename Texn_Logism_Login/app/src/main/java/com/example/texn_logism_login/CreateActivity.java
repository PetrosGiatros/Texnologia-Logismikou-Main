package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Struct;
import java.util.Random;


public class CreateActivity  extends AppCompatActivity {
    private Button buttonCreateSchedule;
    private EditText textViewEmployeesPerShift;
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_form);

        buttonCreateSchedule=(Button)findViewById(R.id.buttonCreateSchedule);

        Spinner spinnerScheduleType = findViewById(R.id.spinnerScheduleType);
        Spinner spinnerShiftType = findViewById(R.id.spinnerShiftType);
        Spinner spinnerProfession = findViewById(R.id.spinnerProfession);
        Spinner spinnerBusiness = findViewById(R.id.spinnerBusiness);
        EditText EditTextEmployeesPerShift = (EditText)findViewById(R.id.EditTextEmployeesPerShift);

        String[] ScheduleTypes = new String[]{"Weekly","Monthly","Trimester","Semester"};
        String[] ShiftTypes = new String[]{"8", "4"};
        String[] Profession = new String[]{"Programmer", "Analyst","Manager"};
        String[] Business = new String[]{"8h" ,"16h" ,"24h"};


        ArrayAdapter<String> adapter ;
        adapter = new ArrayAdapter<>(CreateActivity.this, android.R.layout.simple_spinner_dropdown_item, ScheduleTypes);
        spinnerScheduleType.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShiftTypes);
        spinnerShiftType.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Profession);
        spinnerProfession.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Business);
        spinnerBusiness.setAdapter(adapter4);

       /* String SelectedScheduleType = spinnerScheduleType.getSelectedItem().toString();
        String SelectedShiftType = spinnerShiftType.getSelectedItem().toString();
        String SelectedProfession = spinnerProfession.getSelectedItem().toString();*/


        buttonCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String SelectedScheduleType = spinnerScheduleType.getSelectedItem().toString();
                String SelectedShiftType = spinnerShiftType.getSelectedItem().toString();
                String SelectedProfession = spinnerProfession.getSelectedItem().toString();
                String SelectedBusiness = spinnerBusiness.getSelectedItem().toString();
                Integer SelectedEmployeesPerShift = Integer.valueOf(EditTextEmployeesPerShift.getText().toString());
                int[][] schedule;
                schedule=createSchedule(SelectedScheduleType,SelectedShiftType,SelectedProfession,SelectedEmployeesPerShift,SelectedBusiness);






            }
        });

    }

    public int[][] createSchedule(String SelectedScheduleType,String SelectedShiftType,String SelectedProfession,Integer SelectedEmployeesPerShift,String SelectedBusinessType){
        int numOfShifts = 0;
        boolean allowedToCreateSchedule = true;

        if (SelectedBusinessType == "8h") {
             numOfShifts = 1;
        }else if (SelectedBusinessType == "16h") {
            numOfShifts = 2;
        }else if (SelectedBusinessType == "24h") {
            numOfShifts = 3;
        }

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

        int employeeAmountPerShift=SelectedEmployeesPerShift;
        int type=1;
        int[][] schedule;
        schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];

        if (SelectedScheduleType == "Weekly") {
            type=5;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        } else if (SelectedScheduleType == "Monthly") {
            type=20;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        } else if (SelectedScheduleType == "Trimester") {
            type=60;
            schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];
        } else if (SelectedScheduleType == "Semester"){
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

        //System.out.println("Schedule: "+Integer.valueOf(SelectedShiftType)*type*numOfShifts);
        /*final int min = 0;
        final int max = 6;*/
        int randomNum;
        boolean isOver = false;
        Utilities util = new Utilities();
        int[] sumHoursPerEmployee;
        sumHoursPerEmployee= new int[users.length];
        for(int i =0;i<users.length;i++){
            sumHoursPerEmployee[i]=0;
        }

        int totalTypeHours=Integer.valueOf(SelectedShiftType)*type*numOfShifts;




        int recentAmountOfEmployees=0;
        //System.out.println("Total Type Hours " +totalTypeHours);

        //System.out.println("Before While + + + + + + +" );
        if (users.length *  Integer.valueOf(SelectedShiftType)*type < (type * (numOfShifts* Integer.valueOf(SelectedShiftType)))*employeeAmountPerShift )
        {
            allowedToCreateSchedule = false;
            System.out.println("Not enough employees to complete schedule.");
        }
        //System.out.println("Left part " + users.length *  Integer.valueOf(SelectedShiftType)*type);
        //System.out.println("Right part" + type * (numOfShifts* Integer.valueOf(SelectedShiftType))*employeeAmountPerShift );

        while((totalTypeHours > 0) && (allowedToCreateSchedule) ){
            //System.out.println("In While + + + + + + +");

            int iCheck=1;
            int thisDay=Integer.valueOf(SelectedShiftType) - 1;
            int j =0;
            for(int i =0;i<users.length;i=i+iCheck){
                //randomNum = util.getEmployeeWithFewestHours(users);
                for(j = thisDay;j<Integer.valueOf(SelectedShiftType)*type*numOfShifts;j = j + Integer.valueOf(SelectedShiftType)){
                    iCheck=1;
                    randomNum = util.getEmployeeWithFewestHours(users);
                    //System.out.println("Employee with fewest hours returned " + users[randomNum].FirstName + "With hours:  " +users[randomNum].totalHours + " With ID:  " +users[randomNum].id + " With index " + randomNum);
                    if((users[randomNum].hasShift==true) && (users[randomNum].totalHours>0)) {
                        sumHoursPerEmployee[randomNum]=sumHoursPerEmployee[randomNum]+1;

                        for (int z = j; z >= j - (Integer.valueOf(SelectedShiftType) - 1) ; z-- )
                        {
                            schedule[i][z] = users[randomNum].id;
                            users[randomNum].hasShift=false;

                            //System.out.println("o user:"+users[andomNum].FirstName+"  mphke ston pinaka shedule["+i+"]["+z+"]"+"me total hours:"+users[randomNum].totalHours);

                        }
                        if (totalTypeHours <= 0)
                        {
                            isOver = true;
                        }
                        users[randomNum].totalHours=users[randomNum].totalHours-Integer.valueOf(SelectedShiftType);
                        recentAmountOfEmployees++;
                        //System.out.println("Total Hours: "+users[randomNum].totalHours);
                        iCheck=1;

                    }else{
                        iCheck=0;
                        thisDay=j;
                        if (totalTypeHours <= 0)
                        {
                            isOver = true;
                        }

                        System.out.println("Den mphke o: "+users[randomNum].FirstName);
                        System.out.println("Total Hours: "+users[randomNum].totalHours);
                        System.out.println("Total Type Hours : "+totalTypeHours);

                       /* try {
                            Thread.sleep(500);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }*/
                        break;
                    }
                    if(employeeAmountPerShift == recentAmountOfEmployees){
                        //System.out.println("Reached max people per shift.");
                        i=0;
                        iCheck=0;
                        totalTypeHours=totalTypeHours-Integer.valueOf(SelectedShiftType);
                        recentAmountOfEmployees=0;
                        //k++;
                        //System.out.println("Allagh meras");
                        //System.out.println("upoloipomenes totaltype hours: "+ totalTypeHours);
                        for(int q = 0;q<users.length;q++){
                            users[q].hasShift=true;
                        }


                    }else{
                        //System.out.println("Have not yet reached max people per shift.");
                        iCheck=1;
                        thisDay=j;

                        break;
                    }

                }
                if (totalTypeHours <= 0)
                {
                    break;
                }
                //Something to break out of I
            }
            if (totalTypeHours <= 0)
            {
                break;
            }
        }
        if (allowedToCreateSchedule)
        {
            for (int row = 0; row < schedule.length; row++)//Cycles through rows
            {
                for (int col = 0; col < schedule[row].length; col++)//Cycles through columns
                {
                    System.out.printf("%5d", schedule[row][col]); //change the %5d to however much space you want
                }
                System.out.println(); //Makes a new row
            }

            util.displaySchedule(users,schedule,employeeAmountPerShift,Integer.valueOf(SelectedShiftType)*type*numOfShifts,Integer.valueOf(SelectedShiftType));
        }



        for(int i =0;i<users.length;i++){

            System.out.println("O user: "+users[i].FirstName+" exei "+users[i].totalHours+" wres left. Kai exei doulepsei "+sumHoursPerEmployee[i]+" fores.");

        }
        return schedule;

    }


}



