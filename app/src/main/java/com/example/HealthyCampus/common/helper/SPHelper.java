package com.example.HealthyCampus.common.helper;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.HealthyCampus.application.HealthApp;

public class SPHelper {

    //    登录
    public static final String AUTH_CODE = "AUTH_CODE";
    public static final String REGISTER_ID = "REGISTER_ID";

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static boolean setString(String key, String value) {
        return getPreferences().edit().putString(key, value).commit();
    }


    public static boolean getBoolean(String key, boolean defaultValue) {
        return getPreferences().getBoolean(key, defaultValue);
    }

    private static SharedPreferences preferences;

    private static SharedPreferences getPreferences() {
        if (preferences == null) {
            preferences = PreferenceManager.getDefaultSharedPreferences(HealthApp.getInstance());
        }
        return preferences;
    }
}
