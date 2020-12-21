package com.example.texn_logism_login;


//Using this class means you MUST pay attention to the order of calls.
// If not, you may return uninitialized variables,attempt to access empty arrays, fuck up data and so on.
//I don't care about checks.

//PushStatsToDB should ALWAYS be called last and after all data has been filled. Shit might break otherwise.


// Please, do not touch my garbage.

import android.os.AsyncTask;

import java.util.HashMap;

/**
 * The class that stores and modifies statistics.
 */
public class Stats {
    static public int activeUsersCount = -1,totalProfessions = -1,professionHours[],userProfessionCount[];
    static public User users[];
    static public String professions[], scheduleType, businessType;
    static public boolean failFlag = true;
    /**
     * A parser to parse the URL
     */
    static public HttpParse httpParse = new HttpParse();
    /**
     * A hashmap
     */
    static public HashMap<String,String> statsMap = new HashMap<>();
    /**
     * The URL needed to connect to the database and set the statistics
     */
    static public String HttpURL = "http://priapic-blower.000webhostapp.com/setStatistics.php";
    static public String finalResult ;
    /**
     * Logged In Username
     */
    static public String loggedInUsername;

    /**
     * <h1>Set Users Count</h1>
     * Stores the amount of users pulled from the database
     * @param totalUsers Total Users that exist in the database and are conencted to the specific admin
     */
    static public void setUsersCount(int totalUsers)
    {
        activeUsersCount = totalUsers;
        defineActiveUsers(activeUsersCount);
    }

    /**
     * <h1>Define Active Users</h1>
     * Initializes the array that will store the users
     * @param totalActiveUsers the array used to store the users
     */
    static public void defineActiveUsers(int totalActiveUsers)
    {
        users = new User[totalActiveUsers];
    }

    /**
     * <h1>Set Users</h1>
     * Stores the users
     * @param userArg An array used to store the users
     */
    static public void setUsers(User[] userArg)
    {
        for (int i = 0; i < userArg.length; i++)
        {
            users[i] = userArg[i];
        }
    }

    /**
     * <h1>Set Profession Count</h1>
     * Stores the amount of professions.
     * @param professionsCount
     */
    static public void setProfessionCount(int professionsCount)
    {
        totalProfessions = professionsCount;
        defineProfessionCount(totalProfessions);
    }

    /**
     * <h1>Define Profession Count</h1>
     * Initializes the array that'll store the professions.
     * @param count
     */
    static public void defineProfessionCount(int count)
    {
        professions = new String[count];
    }

    /**
     * <h1>Set Professions</h1>
     * Stores the professions
     * @param professionsArg (Analyst, Programmer or Manager)
     */
    static public void setProfessions(String[] professionsArg)
    {
        for (int i = 0; i < professions.length; i++)
        {
            professions[i] = professionsArg[i];
            System.out.println("Set Profession:" + professions[i]);
        }
    }

    /**
     * <h1>Calculate Hours Per Profession</h1>
     * Calculates the hours per profession (8h or 4h)
     */
    static public void calculateHoursPerProfession()
    {
        int j = 0;
        professionHours = new int[professions.length];
        while( j < professions.length)
        {
            for (int i = 0; i < users.length; i++)
            {
                System.out.println("User Profession:" + users[i].profession);
                if (users[i].profession.equals(professions[j]))
                {
                    professionHours[j] = professionHours[j]+ users[i].hoursWorked;
                }
            }
            j++;
        }
    }

    /**
     * <h1>Calculate Users Per Profession</h1>
     * Calculates the Users per Profession (Number between 1 - max Users working)
     */
    static public void calculateUsersPerProfession()
    {
        userProfessionCount = new int[professions.length];
        int j = 0;
        while( j < professions.length)
        {
            for (int i = 0; i < users.length; i++)
            {

                if (users[i].profession.equals(professions[j]))
                {

                    userProfessionCount[j] = userProfessionCount[j] + 1;
                }
            }
            j++;
        }
    }

    /**
     * <h1>Set Schedule Type</h1>
     * Sets the ScheduleType
     * @param scheduleType1 Is the type of Schedule the admin wants to generate(Weekly, Monthly, etc.)
     */
    static public void setScheduleType(String scheduleType1)
    {
        scheduleType = scheduleType1;
    }

    /**
     * <h1>Set Business Type</h1>
     * Sets the businessType
     * @param businessType1 - 8h, 16h, or 24h of working in the company.
     */
    static public void setBusinessType(String businessType1)
    {
        businessType = businessType1;
    }

    /**
     * <h1>Set Logged In Username</h1>
     * Sets the logged in Username
     */
    static public void setLoggedInUsername()
    {
         loggedInUsername = LoginActivity.getUsernameTextView().getText().toString();
    }

    /**
     * <H1>Push Stats To Database</H1>
     * Sends the calculated result of each stat to a table in the database.
     *
     */
    public void pushStatsToDB()     //This function should theoretically send all the calculated stats from a schedule creation to the DB. Always to be called last.
    {
        /**
         * Class used to push data to database
         */
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
