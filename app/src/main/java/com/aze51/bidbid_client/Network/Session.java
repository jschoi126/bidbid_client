package com.aze51.bidbid_client.Network;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by ChoiJunsung on 2016. 7. 4..
 */
public class Session {
    private SharedPreferences sharedPreferences;

    public Session(Context ctx) {
        // TODO Auto-generated constructor stub
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public void setsSessionKey(String usename) {
        sharedPreferences.edit().putString("usename", usename).commit();
        sharedPreferences.edit().commit();
    }

    public String getUsername() {
        String usename = sharedPreferences.getString("usename","");
        return usename;
    }
}
