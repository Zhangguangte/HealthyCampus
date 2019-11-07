package com.example.HealthyCampus.common.helper;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.example.HealthyCampus.application.HealthApp;

public class SPHelper {

    //User
    public static final String USER_ID = "USER_ID";
    public static final String ACCOUNT = "ACCOUNT";
    public static final String PASSWORD = "PASSWORD";
    public static final String NICKNAME = "NICKNAME";
    public static final String USER_AVATAR = "USER_AVATAR";
    public static final String USER_DESCRIPTION = "USER_DESCRIPTION";
    public static final String USER_LOCATION = "USER_LOCATION";
    public static final String USER_SEX = "USER_SEX";
    public static final String USER_BRITHDAY = "USER_BRITHDAY";
    public static final String USER_MODIFYTIME = "USER_MODIFYTIME";
    public static final String USER_PHONE = "USER_PHONE";


    //    登录
    public static final String AUTH_CODE = "AUTH_CODE";
    public static final String REGISTER_ID = "REGISTER_ID";


    public static final String JPUSH_MUSIC = "JPUSH_MUSIC";
    public static final String JPUSH_VIB = "JPUSH_VIB";
    public static final String JPUSH_ROAMING = "JPUSH_ROAMING";



    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static boolean setString(String key, String value) {
        return getPreferences().edit().putString(key, value).commit();
    }

    public static boolean setBoolean(String key, boolean defaultValue) {
        return getPreferences().edit().putBoolean(key, defaultValue).commit();
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
