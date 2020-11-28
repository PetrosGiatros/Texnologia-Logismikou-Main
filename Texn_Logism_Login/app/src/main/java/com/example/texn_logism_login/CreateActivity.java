package com.example.texn_logism_login;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.Struct;


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

        int type;
        /*kitsos.setTotalHours(SelectedScheduleType,SelectedShiftType);
        panagiwtis.setTotalHours(SelectedScheduleType,SelectedShiftType);
        konstantinos.setTotalHours(SelectedScheduleType,SelectedShiftType);
        kwstas.setTotalHours(SelectedScheduleType,SelectedShiftType);
        kotsos.setTotalHours(SelectedScheduleType,SelectedShiftType);
        giannhs.setTotalHours(SelectedScheduleType,SelectedShiftType);
        petran.setTotalHours(SelectedScheduleType,SelectedShiftType);*/
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
        if (SelectedScheduleType == "Weekly") {
           final int[][] schedule = new int[Integer.valueOf(SelectedShiftType)*5*numOfShifts][7];
        }
        else if(SelectedScheduleType == "Monthly") {
            int[][] schedule = new int[Integer.valueOf(SelectedShiftType)*20*numOfShifts][7];
        }
        else if(SelectedScheduleType == "Trimester") {
            int[][] schedule = new int[Integer.valueOf(SelectedShiftType)*60*numOfShifts][7];

        }
        else if(SelectedScheduleType == "Semester") {
            int[][] schedule = new  int[Integer.valueOf(SelectedShiftType) * 120 * numOfShifts][7];
        }
        System.out.println();
       /*for (int i = 0; i < 20; i++ )
       {
           for (int j = 0; j < 7; j++)
           {

           }

       }*/



    }







}
