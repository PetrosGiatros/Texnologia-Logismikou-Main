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


        User kitsos=new User("ki","tsos",8,1,"Programmer");
        User panagiwtis = new User("pana","giwtis",8,2,"Analyst");
        User konstantinos = new User("konsta","ntinos",8,3,"Manager");
        User kwstas=new User("kw","stas",8,4,"Programmer");
        User kotsos=new User("ko","tsos",8,5,"Analyst");
        User giannhs=new User("gian","nhs",8,6,"Programmer");
        User petran=new User("pe","tran",8,7,"Manager");

        int totalHoursSchedule;
        if(SelectedScheduleType=="Weekly"){
            totalHoursSchedule=5*(Integer.valueOf(SelectedShiftType));
        }else if( SelectedScheduleType=="Monthly"){
            totalHoursSchedule=20*(Integer.valueOf(SelectedShiftType));
        }else if(SelectedScheduleType=="Trimester"){
            totalHoursSchedule=60*(Integer.valueOf(SelectedShiftType));
        }else{
            totalHoursSchedule=120*(Integer.valueOf(SelectedShiftType));
        }






    }







}
