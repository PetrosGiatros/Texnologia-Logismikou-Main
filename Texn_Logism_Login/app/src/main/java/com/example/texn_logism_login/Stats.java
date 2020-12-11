package com.example.texn_logism_login;


//Using this class means you MUST pay attention to the order of calls.
// If not, you may return uninitialized variables,attempt to access empty arrays, fuck up data and so on.
//I don't care about checks.

//PushStatsToDB should ALWAYS be called last and after all data has been filled. Shit might break otherwise.


// Please, do not touch my garbage.

import android.os.AsyncTask;

import java.util.HashMap;

public class Stats {
    static public int activeUsersCount = -1,totalProfessions = -1,professionHours[],userProfessionCount[];
    static public User users[];
    static public String professions[], scheduleType, businessType;
    static public boolean failFlag = true;
    static public HttpParse httpParse = new HttpParse();
    static public HashMap<String,String> statsMap = new HashMap<>();
    static public String HttpURL = "http://priapic-blower.000webhostapp.com/setStatistics.php";
    static public String finalResult ;
    static public String loggedInUsername;

    static public void setUsersCount(int totalUsers)
    {
        activeUsersCount = totalUsers;
        defineActiveUsers(activeUsersCount);
    }
    static public void defineActiveUsers(int totalActiveUsers)
    {
        users = new User[totalActiveUsers];
    }
    static public void setUsers(User[] userArg)
    {
        for (int i = 0; i < userArg.length; i++)
        {
            users[i] = userArg[i];
        }
    }
    static public void setProfessionCount(int professionsCount)
    {
        totalProfessions = professionsCount;
        defineProfessionCount(totalProfessions);
    }
    static public void defineProfessionCount(int count)
    {
        professions = new String[count];
    }
    static public void setProfessions(String[] professionsArg)
    {
        for (int i = 0; i < professions.length; i++)
        {
            professions[i] = professionsArg[i];
        }
    }
    static public void calculateHoursPerProfession()
    {
        int j = 0;
        professionHours = new int[professions.length];
        while( j < professions.length)
        {
            for (int i = 0; i < users.length; i++)
            {
                if (users[i].profession == professions[j])
                {
                    professionHours[j] = professionHours[j]+ users[i].hoursWorked;
                }
            }
            j++;
        }
    }
    static public void calculateUsersPerProfession()
    {
        userProfessionCount = new int[professions.length];
        int j = 0;
        while( j < professions.length)
        {
            for (int i = 0; i < users.length; i++)
            {
                if (users[i].profession == professions[j])
                {
                    userProfessionCount[j] = userProfessionCount[j] + 1;
                }
            }
            j++;
        }
    }
    static public void setScheduleType(String scheduleType1)
    {
        scheduleType = scheduleType1;
    }
    static public void setBusinessType(String businessType1)
    {
        businessType = businessType1;
    }
    static public void setLoggedInUsername()
    {
         loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    }
    public void pushStatsToDB()     //This function should theoretically send all the calculated stats from a schedule creation to the DB. Always to be called last.
    {
        class pushtoDBClass extends AsyncTask<String,Void,String> {
            @Override
            protected void onPreExecute() {
                super.onPreExecute();

            }
            @Override
            protected void onPostExecute(String httpResponseMsg) {
                super.onPostExecute(httpResponseMsg);

            }

            @Override
            protected String doInBackground(String... params) {

                statsMap.put("username",params[0]);     //I have no idea which parameters to send yet.
                statsMap.put("No idea",params[1]);
                statsMap.put("No idea",params[2]);


                finalResult = httpParse.postRequest(statsMap, HttpURL);
                return finalResult;
            }
        }
        pushtoDBClass setObject = new pushtoDBClass();  //I have no idea if this'll work but I sure fucking hope so.
        setObject.execute();
    }
}
