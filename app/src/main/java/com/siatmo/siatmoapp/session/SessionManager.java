package com.siatmo.siatmoapp.session;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.siatmo.siatmoapp.view.LandingPageAdminActivity;

import java.util.HashMap;

public class SessionManager {

    SharedPreferences pref;

    SharedPreferences.Editor editor;

    Context context;

    int PRIVATE_MODE = 0;

    private static final String PREF_NAME = "AtmaAutoSession";
    private static final String IS_LOGIN = "IsLoggedIn";
    public static final String KEY_USERNAME = "USERNAME";

    public SessionManager(Context context)
    {
        this.context = context;
        pref = this.context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void createLoginSessions(String username)
    {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public void saveSPBoolean(String username){
        editor.putBoolean(IS_LOGIN, false);
        editor.putString(KEY_USERNAME,username);
        editor.commit();
    }

    public HashMap<String, String> getUserSessionsDetails()
    {
        HashMap<String, String> user = new HashMap<String, String>();

        user.put(KEY_USERNAME, pref.getString(KEY_USERNAME, null));

        return user;
    }

    public void checkLogin()
    {
        if (!this.isLoggedIn())
        {
            Intent intent = new Intent(context, LandingPageAdminActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(IS_LOGIN, false);
    }

    public void logoutUser(){
        editor.putBoolean(IS_LOGIN, false);
        editor.clear();
        editor.commit();

        Intent i = new Intent(context, LandingPageAdminActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }
}
