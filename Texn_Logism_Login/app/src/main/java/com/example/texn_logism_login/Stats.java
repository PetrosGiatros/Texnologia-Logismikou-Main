package com.example.texn_logism_login;


//Using this class means you MUST pay attention to the order of calls. If not, you may return uninitialized variables.
//I don't care about checks.

//Class will change in the future. A constructor will probably be added to syphon the users from the DB. *Sigh.*
//It will probably make half these functions useless. Fucking hardcoded data.

// Please, do not touch my garbage.

public class Stats {
    static public int activeUsersCount = -1,totalProfessions = -1,professionHours[];
    static public User users[];
    static public String professions[];
    static public boolean failFlag = true;

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

}
