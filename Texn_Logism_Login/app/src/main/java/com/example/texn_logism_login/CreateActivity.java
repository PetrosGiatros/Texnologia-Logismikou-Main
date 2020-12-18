package com.example.texn_logism_login;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.sql.SQLOutput;
import java.sql.Struct;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Random;

/**
 *
 */
public class CreateActivity  extends AppCompatActivity {
    private Button buttonCreateSchedule,backButton;
    private EditText textViewEmployeesPerShift;
    Utilities util = new Utilities();
    private static String currentDay;
    private static String  currentMonth;
    private static String currentYear;
    HashMap<String,String> startMap =new HashMap<>();
    HttpParse startParse = new HttpParse();
    String startResult;
    String startURL=  "http://priapic-blower.000webhostapp.com/startDate.php";
    String isAssignedTo = LoginActivity.getUsernameTextView().getText().toString();
    public static Stats stObj = new Stats();
    String[] ScheduleTypes = new String[]{"Weekly", "Monthly", "Trimester", "Semester"};
    String[] ShiftTypes = new String[]{"8", "4"};
    String[] Profession = new String[]{"Programmer", "Analyst", "Manager"};  //When deleting the profession parameters, do not delete this.
    String[] Business = new String[]{"8h", "16h", "24h"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_form);

        buttonCreateSchedule = (Button) findViewById(R.id.buttonCreateSchedule);
        backButton = (Button) findViewById(R.id.buttonBackSchedule);

        Spinner spinnerScheduleType = findViewById(R.id.spinnerScheduleType);
        Spinner spinnerShiftType = findViewById(R.id.spinnerShiftType);
        Spinner spinnerProfession = findViewById(R.id.spinnerProfession);
        Spinner spinnerBusiness = findViewById(R.id.spinnerBusiness);
        EditText EditTextEmployeesPerShift = (EditText) findViewById(R.id.EditTextEmployeesPerShift);




        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(CreateActivity.this, android.R.layout.simple_spinner_dropdown_item, ScheduleTypes);
        spinnerScheduleType.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShiftTypes);
        spinnerShiftType.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Profession);
        spinnerProfession.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Business);
        spinnerBusiness.setAdapter(adapter4);


        buttonCreateSchedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                Calendar calendar = Calendar.getInstance();
                currentDay = String.valueOf(calendar.get(Calendar.DATE));
                currentMonth = String.valueOf(calendar.get(Calendar.MONTH)+1);
                currentYear = String.valueOf(calendar.get(Calendar.YEAR));


                System.out.println("SHMERINH IMEROMHNIA:" + currentDay + " " + currentMonth + " " + currentYear);

                String SelectedScheduleType = spinnerScheduleType.getSelectedItem().toString();
                String SelectedShiftType = spinnerShiftType.getSelectedItem().toString();
                String SelectedProfession = spinnerProfession.getSelectedItem().toString();
                String SelectedBusiness = spinnerBusiness.getSelectedItem().toString();
                Integer SelectedEmployeesPerShift = Integer.valueOf(EditTextEmployeesPerShift.getText().toString());
                //createSchedule(SelectedScheduleType, SelectedShiftType, SelectedProfession, SelectedEmployeesPerShift, SelectedBusiness);
                int[][] schedule;
                schedule=createSchedule(SelectedScheduleType,SelectedShiftType,SelectedProfession,SelectedEmployeesPerShift,SelectedBusiness);

                int scheduleLength=0;



                String shiftName="";


                SaveScheduleActivity saveNewSchedulePHP=new SaveScheduleActivity();
                saveNewSchedulePHP.deleteScheduleActivity(isAssignedTo);
              
                util.saveSchedule(util.userObjects,schedule,SelectedEmployeesPerShift,Integer.valueOf(SelectedShiftType)*getScheduleLength(SelectedScheduleType)*getNumOfShifts(SelectedBusiness),getNumOfShifts(SelectedBusiness));

                scheduleLength=getScheduleLength(SelectedScheduleType);
                int numberOfShifts=getNumOfShifts(SelectedBusiness);
                startDateFunction(isAssignedTo, currentDay, currentMonth, currentYear);

                Intent intent = new Intent(CreateActivity.this, AdminActivity.class);
                startActivity(intent);



            }

        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CreateActivity.this, AdminActivity.class);

                startActivity(intent);

            }
        });

    }
//prosthikh newn sunarthsewn gia to megethos tou schedule kai twn arithmo twn shifts per day.

    /**
     *
     * @param SelectedScheduleType
     * @return
     */
    public int getScheduleLength(String SelectedScheduleType){
        int scheduleLength=0;

        if (SelectedScheduleType == "Weekly") {
            scheduleLength =5;

        } else if (SelectedScheduleType == "Monthly") {
            scheduleLength=20;

        } else if (SelectedScheduleType == "Trimester") {
            scheduleLength=60;

        } else if (SelectedScheduleType == "Semester"){
            scheduleLength=120;

        }
        return scheduleLength;
    }

    /**
     *
     * @param SelectedBusiness
     * @return
     */

    public int getNumOfShifts(String SelectedBusiness){
        int num=0;
    if (SelectedBusiness == "8h") {
        num = 1;
    }else if (SelectedBusiness == "16h") {
        num = 2;
    }else if (SelectedBusiness == "24h") {
        num = 3;
    }
    return num;
}


    /**
     *
     * @param SelectedScheduleType
     * @param SelectedShiftType
     * @param SelectedProfession
     * @param SelectedEmployeesPerShift
     * @param SelectedBusinessType
     * @return
     */
    public int[][] createSchedule(String SelectedScheduleType,String SelectedShiftType,String SelectedProfession,Integer SelectedEmployeesPerShift,String SelectedBusinessType){
        int numOfShifts = 0;
        int dayCount = 0;
        boolean allowedToCreateSchedule = true;
        //Xrhsh newn sunarthsewn//
        numOfShifts=getNumOfShifts(SelectedBusinessType);
        int daycount=0;



        for (int i = 0; i < util.userObjects.length; i++)
        {
            util.userObjects[i].setTotalHours(SelectedScheduleType,SelectedShiftType);
        }

        int employeeAmountPerShift=SelectedEmployeesPerShift;

        //xrhsh newn sunarthsewn//
        int type=getScheduleLength(SelectedScheduleType);
        int[][] schedule;


        schedule = new int[employeeAmountPerShift][Integer.valueOf(SelectedShiftType)*type*numOfShifts];


        for (int row = 0; row < schedule.length; row++)//Cycles through rows
        {
            for (int col = 0; col < schedule[row].length; col++)//Cycles through columns
            {
               schedule[row][col]=-1;//change the %5d to however much space you want
            }

        }

        int randomNum;
        boolean isOver = false;
        Utilities util = new Utilities();

        int totalTypeHours=Integer.valueOf(SelectedShiftType)*type*numOfShifts;


        StringBuilder builder = new StringBuilder("");
        String text = builder.toString();
        int recentAmountOfEmployees=0;

        if (util.userObjects.length *  Integer.valueOf(SelectedShiftType)*type < (type * (numOfShifts* Integer.valueOf(SelectedShiftType)))*employeeAmountPerShift )
        {
            allowedToCreateSchedule = false;
            System.out.println("Not enough employees to complete schedule.");


        }

        while((totalTypeHours > 0) && (allowedToCreateSchedule) ){
            //System.out.println("In While + + + + + + +");

            int iCheck=1;
            int thisDay=Integer.valueOf(SelectedShiftType) - 1;
            int j =0;
            for(int i =0;i<util.userObjects.length;i=i+iCheck){
                for(j = thisDay;j<Integer.valueOf(SelectedShiftType)*type*numOfShifts;j = j + Integer.valueOf(SelectedShiftType)){
                    iCheck=1;
                    randomNum = util.getEmployeeWithFewestHours(util.userObjects);
                    //System.out.println("Employee with fewest hours returned " + users[randomNum].FirstName + "  With hours:  " +users[randomNum].totalHours + " With ID:  " +users[randomNum].id + " With hasShit " + users[randomNum].hasShift);
                    if((util.userObjects[randomNum].hasShift==true) && (util.userObjects[randomNum].totalHours>0)) {
                        util.userObjects[randomNum].timesWorked=util.userObjects[randomNum].timesWorked+1;
                        //builder.append("Apple").append(" ").append("Banana");
                        for (int z = j; z >= j - (Integer.valueOf(SelectedShiftType) - 1) ; z-- )
                        {

                            schedule[i][z] = util.userObjects[randomNum].id;
                            //System.out.println("o user: "+users[randomNum].id+"  mphke ston pinaka schedule["+i+"]["+z+"]"+"me hasShift:"+users[randomNum].hasShift);
                        }
                        if (totalTypeHours <= 0)
                        {
                            isOver = true;
                        }
                        util.userObjects[randomNum].hasShift=false;
                        util.userObjects[randomNum].totalHours=util.userObjects[randomNum].totalHours-Integer.valueOf(SelectedShiftType);
                        util.userObjects[randomNum].hoursWorked = util.userObjects[randomNum].hoursWorked+Integer.valueOf(SelectedShiftType);
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

                        break;
                    }
                    if(employeeAmountPerShift == recentAmountOfEmployees){
                        dayCount++;
                        System.out.println("Reached max people per shift.");
                        builder.setLength(0);
                        i=0;
                        iCheck=0;
                        totalTypeHours=totalTypeHours-Integer.valueOf(SelectedShiftType);
                        recentAmountOfEmployees=0;
                        //k++;
                        //System.out.println("Allagh meras");
                        //System.out.println("upoloipomenes totaltype hours: "+ totalTypeHours);
                     
                        if(dayCount==numOfShifts) {
                            for (int q = 0; q < util.userObjects.length; q++) {
                                util.userObjects[q].hasShift = true;
                            }
                            dayCount=0;
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
        String shiftName="";
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

           //util.displaySchedule(users,schedule,employeeAmountPerShift,Integer.valueOf(SelectedShiftType)*type*numOfShifts,Integer.valueOf(SelectedShiftType));

            util.saveSchedule(util.userObjects,schedule,SelectedEmployeesPerShift,Integer.valueOf(SelectedShiftType)*getScheduleLength(SelectedScheduleType)*numOfShifts,numOfShifts);
        }

        /*for(int i =0;i<users.length;i++){

            System.out.println("O user: "+users[i].FirstName+" exei "+users[i].totalHours+" wres left. Kai exei doulepsei "+users[i].timesWorked + " fores.");

        }*/

        if (allowedToCreateSchedule)
        {
            System.out.println("I was allowed to create a schedule.");
            stObj.setUsersCount(util.userObjects.length);  //Do NOT change the call order.
            stObj.setUsers(util.userObjects);
            stObj.setProfessionCount(Profession.length);
            stObj.setProfessions(Profession);
            stObj.failFlag = false;
            stObj.setScheduleType(SelectedScheduleType);
            stObj.setBusinessType(SelectedBusinessType);
        }
        return schedule;


    }

    /**
     *
     * @param isAssignedTo
     * @param currentDay
     * @param currentMonth
     * @param currentYear
     */
    public void startDateFunction(String isAssignedTo, String currentDay, String currentMonth, String currentYear){

        class startDateFunctionClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);
                //progressDialog.dismiss();
                //httpResponseMsg = id tou admin, autou poy ekane login //


                //Toast.makeText(HireActivity.this,httpResponseMsg.toString(), Toast.LENGTH_LONG).show();

            }

            @Override
            protected String doInBackground(String... params) {

                startMap.put("isAssignedTo",params[0]);
                startMap.put("startingDay",params[1]);
                startMap.put("startingMonth",params[2]);
                startMap.put("startingYear",params[3]);


                startResult = startParse.postRequest(startMap, startURL);
                return startResult;
            }
        }
        startDateFunctionClass startDateFunctionClass = new startDateFunctionClass();
        startDateFunctionClass.execute(isAssignedTo, currentDay, currentMonth, currentYear);
    }




}



