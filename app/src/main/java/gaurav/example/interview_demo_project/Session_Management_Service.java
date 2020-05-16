package gaurav.example.interview_demo_project;

import android.content.Context;
import android.content.SharedPreferences;

public class Session_Management_Service {

    public Context mContext;
    SharedPreferences adminInfosharedPreferences;
    String username;
    String userpassword;

    public Session_Management_Service(Context mContext)
    {
        this.mContext=mContext;
        adminInfosharedPreferences=mContext.getSharedPreferences("adminInfo",Context.MODE_PRIVATE);
    }

    public void removeUserInfo()
    {
        adminInfosharedPreferences.edit().clear().commit();
    }



    public String getUsername() {
        username=adminInfosharedPreferences.getString("adminName","");
        return username;

    }

    public void setUsername(String username) {
        this.username = username;
        adminInfosharedPreferences.edit().putString("adminName",username).commit();
    }

    public String getUserpassword() {
        userpassword=adminInfosharedPreferences.getString("adminPassword","");
        return userpassword;
    }

    public void setUserpassword(String userpassword) {
        this.userpassword = userpassword;
        adminInfosharedPreferences.edit().putString("adminPassword",userpassword).commit();
    }

}
