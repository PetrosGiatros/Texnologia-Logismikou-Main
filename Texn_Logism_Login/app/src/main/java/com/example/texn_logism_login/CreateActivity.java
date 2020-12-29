package com.example.texn_logism_login;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Calendar;
import java.util.HashMap;

/**
 * Create Activity is responsible for generating a schedule, when the admin presses the Create button.
 */

public class CreateActivity  extends AppCompatActivity {
    private Button buttonCreateSchedule,backButton;
    private EditText textViewEmployeesPerShift;
    Utilities util = new Utilities();
    public boolean isSaturdayChecked, isSundayChecked;
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
    public String [] Override = new String[]{"None","Passive","Aggressive","Severe"};


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.create_form);
        TextView infoText;
        infoText = (TextView) findViewById(R.id.textView14);
        infoText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                System.out.println("Hello there.");
                AlertDialog.Builder builder1 = new AlertDialog.Builder(CreateActivity.this);
                builder1.setMessage("Restrictions Override mode defines how the Algorithm will run."  +"\n" +"\n" +
                        "None: The Algorithm will generate the fairest possible schedule as normal by following all restrictions. If a schedule can't be created, it will abort and alert the user."  +"\n" +"\n"+
                        "Passive: The Algorithm will attempt to generate the fairest possible schedule. If an employee can't be assigned due to restrictions, it will continue without doing so. " +"\n" +"\n"+
                        "Aggressive: The Algorithm will generate a fair schedule by any means necessary. It will utilize overtime and ignore shift restrictions.").show();

            }
        });



        buttonCreateSchedule = (Button) findViewById(R.id.buttonCreateSchedule);
        backButton = (Button) findViewById(R.id.buttonBackSchedule);

        Spinner spinnerScheduleType = findViewById(R.id.spinnerScheduleType);
        Spinner spinnerShiftType = findViewById(R.id.spinnerShiftType);
        Spinner spinnerProfession = findViewById(R.id.spinnerProfession);
        Spinner spinnerBusiness = findViewById(R.id.spinnerBusiness);
        Spinner spinnerOverride = findViewById(R.id.spinnerOverride);
        EditText EditTextEmployeesPerShift = (EditText) findViewById(R.id.EditTextEmployeesPerShift);
        CheckBox checkBoxSaturday = findViewById(R.id.checkBoxSaturday);
        CheckBox checkBoxSunday = findViewById(R.id.checkBoxSunday);




        ArrayAdapter<String> adapter;
        adapter = new ArrayAdapter<>(CreateActivity.this, android.R.layout.simple_spinner_dropdown_item, ScheduleTypes);
        spinnerScheduleType.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, ShiftTypes);
        spinnerShiftType.setAdapter(adapter2);

        ArrayAdapter<String> adapter3 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Profession);
        spinnerProfession.setAdapter(adapter3);

        ArrayAdapter<String> adapter4 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Business);
        spinnerBusiness.setAdapter(adapter4);
        ArrayAdapter<String> adapter5 = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, Override);
        spinnerOverride.setAdapter(adapter5);


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
                String SelectedOverrideMode = spinnerOverride.getSelectedItem().toString();


                isSaturdayChecked = ((CheckBox) findViewById(R.id.checkBoxSaturday)).isChecked();
                isSundayChecked = ((CheckBox) findViewById(R.id.checkBoxSunday)).isChecked();
                stObj.saturdayCheck = isSaturdayChecked;
                System.out.println("saturday1: " + isSaturdayChecked);
                stObj.sundayCheck = isSundayChecked;
                System.out.println("Sunday1: " + isSundayChecked);


                //createSchedule(SelectedScheduleType, SelectedShiftType, SelectedProfession, SelectedEmployeesPerShift, SelectedBusiness);
                int[][] schedule;
                schedule=createSchedule(SelectedScheduleType,SelectedShiftType,SelectedProfession,SelectedEmployeesPerShift,SelectedBusiness,SelectedOverrideMode);

                int scheduleLength=0;



                String shiftName="";


                //SaveScheduleActivity saveNewSchedulePHP=new SaveScheduleActivity();
               // saveNewSchedulePHP.deleteScheduleActivity(isAssignedTo);
              
                //util.saveSchedule(util.userObjects,schedule,SelectedEmployeesPerShift,Integer.valueOf(SelectedShiftType)*getScheduleLength(SelectedScheduleType)*getNumOfShifts(SelectedBusiness),getNumOfShifts(SelectedBusiness),isSaturdayChecked,isSundayChecked);

                scheduleLength=getScheduleLength(SelectedScheduleType);
                int numberOfShifts=getNumOfShifts(SelectedBusiness);
                //startDateFunction(isAssignedTo, currentDay, currentMonth, currentYear);

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

    /**
     * <h1>Get Schedule Length</h1>
     * getScheduleInfo is used to return the number of employees the admin has requested for each shift as well as the overall length of the schedule (weekly, monthly etc).
     * @param SelectedScheduleType is the length of the requested schedule.
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
     * <h1>Get Number Of Shifts</h1>
     * getNumOfShifts is used to return the number of Shifts, based on the selected business type (8h, 16h or 24h)
     * @param SelectedBusiness Is the business type selected (8h, 16h, 24h)
     * @return returns num, based on the business type
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
     * <h1>Create Schedule</h1>
     * CreateSchedule is the main class needed to <b>generate a Schedule</b>, based on multiple options the admin has picked
     * @param SelectedScheduleType String Array that Stores the schedule types (Weekly, Monthly, Trimester or Semester
     * @param SelectedShiftType String Array that stores the Shift types, 8h or 4h
     * @param SelectedProfession String Array that stores the Professions available (Programmer, Analyst, Manager)
     * @param SelectedEmployeesPerShift Is a number that clarifies how many users per shift the admin wants
     * @param SelectedBusinessType String Array that stores the Business type (8h, 16h, or 24h of working schedule)
     * @return Returns the generated Schedule
     */


    public int[][] createSchedule(String SelectedScheduleType,String SelectedShiftType,String SelectedProfession,Integer SelectedEmployeesPerShift,String SelectedBusinessType,String SelectedOverrideMode){

        //We need to create a check on whether the admin who creates the schedule has hired any employees.
        //If there are no employees and he attempts to generate a schedule, it'll mostl likely crash
        //due to calls on null objects.

        int numOfShifts = 0;
        int dayCount = 0;
        boolean allowedToCreateSchedule = true,isAggroActive = false;
        //Xrhsh newn sunarthsewn//
        numOfShifts=getNumOfShifts(SelectedBusinessType);

        User currentUser = new User(-1,"first","last","profession",-1,false,false,false);



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
               schedule[row][col]=-1;
            }

        }

        int randomNum = -1;
        boolean isOver = false;
        Utilities util = new Utilities();

        int totalTypeHours=Integer.valueOf(SelectedShiftType)*type*numOfShifts;


        StringBuilder builder = new StringBuilder("");
        String text = builder.toString();
        int recentAmountOfEmployees=0;

        if ((util.userObjects.length *  Integer.valueOf(SelectedShiftType)*type < (type * (numOfShifts* Integer.valueOf(SelectedShiftType)))*employeeAmountPerShift ) && (SelectedOverrideMode == "None"))
        {
            allowedToCreateSchedule = false;
            System.out.println("Not enough employees to complete schedule.");
        }

        while((totalTypeHours > 0) && (allowedToCreateSchedule) ){
            System.out.println("In While + + + + + + +");

            int iCheck=1;
            int thisDay=Integer.valueOf(SelectedShiftType) - 1;
            int j =0;
            for(int i =0;i<util.userObjects.length;i=i+iCheck)
            {
                for (j = thisDay; j < Integer.valueOf(SelectedShiftType) * type * numOfShifts; j = j + Integer.valueOf(SelectedShiftType))
                {
                    iCheck = 1;
                    if (SelectedOverrideMode == "None")
                    {
                        currentUser = util.getEmployeeDefaultMode(util.userObjects);    //Yes yes, I know. The arguments are redundant.
                    }
                    else if (SelectedOverrideMode == "Passive")
                    {
                        currentUser = util.getEmployeePassiveMode(util.userObjects);
                    }
                    else if (SelectedOverrideMode == "Aggressive")
                    {
                        currentUser = util.getEmployeePassiveMode(util.userObjects);
                        System.out.println("Current user ID from passive " + currentUser.id);
                        if (currentUser.id == -1)
                        {
                            currentUser = util.getEmployeeAggressiveMode(util.userObjects);
                            isAggroActive = true;
                            System.out.println("Aggro Active");
                        }
                    }
                    System.out.println("Employee with fewest hours returned " + currentUser.firstName + "  With hours:  " + currentUser.totalHours + " With ID:  " + currentUser.id + " With hasShit " + currentUser.hasShift);
                    //builder.append("Apple").append(" ").append("Banana");
                    for (int z = j; z >= j - (Integer.valueOf(SelectedShiftType) - 1); z--) {

                        schedule[i][z] = currentUser.id;
                        //System.out.println("o user: " + currentUser.id + "  mphke ston pinaka schedule[" + i + "][" + z + "]" + "me hasShift:" + currentUser.hasShift);
                    }
                    if (totalTypeHours <= 0) {
                        isOver = true;
                    }
                    for (int g = 0; g < util.userObjects.length;g++)
                    {
                        if (util.userObjects[g].id == currentUser.id)
                        {
                            util.userObjects[g].timesWorked = util.userObjects[g].timesWorked + 1;
                            System.out.println("Added times worked.");
                            util.userObjects[g].hasShift = false;
                            if (isAggroActive == false)
                            {
                                util.userObjects[g].totalHours = util.userObjects[g].totalHours - Integer.valueOf(SelectedShiftType);
                                util.userObjects[g].regulationHoursWorked = util.userObjects[g].regulationHoursWorked + Integer.valueOf(SelectedShiftType);
                            }

                        }
                    }

                    recentAmountOfEmployees++;
                    //System.out.println("Total Hours: "+users[randomNum].totalHours);
                    iCheck = 1;
                    if (employeeAmountPerShift == recentAmountOfEmployees)
                    {
                        dayCount++;
                        System.out.println("Reached max people per shift.");
                        builder.setLength(0);
                        i = 0;
                        iCheck = 0;
                        totalTypeHours = totalTypeHours - Integer.valueOf(SelectedShiftType);
                        recentAmountOfEmployees = 0;
                        //k++;
                        //System.out.println("Allagh meras");
                        //System.out.println("upoloipomenes totaltype hours: "+ totalTypeHours);

                        if (dayCount == numOfShifts) {
                            for (int q = 0; q < util.userObjects.length; q++) {
                                util.userObjects[q].hasShift = true;
                            }
                            dayCount = 0;
                            isAggroActive = false;
                            System.out.println("Aggro set to false");
                        }

                    } else {
                        //System.out.println("Have not yet reached max people per shift.");
                        iCheck = 1;
                        thisDay = j;

                        break;
                    }

                }
                if (totalTypeHours <= 0) {
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

            //util.saveSchedule(util.userObjects,schedule,SelectedEmployeesPerShift,Integer.valueOf(SelectedShiftType)*getScheduleLength(SelectedScheduleType)*numOfShifts,numOfShifts);
        }

        /*for(int i =0;i<users.length;i++){

            System.out.println("O user: "+users[i].FirstName+" exei "+users[i].totalHours+" wres left. Kai exei doulepsei "+users[i].timesWorked + " fores.");

        }*/

        if (allowedToCreateSchedule)
        {
            System.out.println("I was allowed to create a schedule.");
            stObj.setLoggedInUsername();
            stObj.setUsersCount(util.userObjects.length);  //Do NOT change the call order.
            stObj.setUsers(util.userObjects);
            stObj.setProfessionCount(Profession.length);
            stObj.setProfessions(Profession);
            stObj.failFlag = false;
            stObj.setScheduleType(SelectedScheduleType);
            stObj.setBusinessType(SelectedBusinessType);
            stObj.peoplePerShift = employeeAmountPerShift;
            stObj.calculateHoursPerProfession();
            stObj.calculateUsersPerProfession();
            stObj.pushScheduleStatsToDB();
            stObj.deleteUserStats();
            stObj.pushUserStatsToDB();
            for (int i = 0; i < util.userObjects.length;i++)
            {
                System.out.println("User ID: " +util.userObjects[i].id + "  Regulation Hours Worked: "+ util.userObjects[i].regulationHoursWorked +
                        "    Overtime Hours: " + util.userObjects[i].overtimeHours + "   Times Worked " + util.userObjects[i].timesWorked + "  Total Hours " +util.userObjects[i].totalHours);

            }
        }
        return schedule;
    }


    /**
     * <h1>Start Date Function</h1>
     * startDateFunction is responsible to <b>clarify the date</b> that the admin wants to create the schedule.
     * @param isAssignedTo Defines to whom the schedule refers to (Used to avoid conflicts with users that do <b>not</b> belong to the specific admin
     * @param currentDay String used to provide the current day
     * @param currentMonth String used to provide the current month
     * @param currentYear String used to provide the current year
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



