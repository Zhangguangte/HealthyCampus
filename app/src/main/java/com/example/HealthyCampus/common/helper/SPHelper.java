package com.example.HealthyCampus.common.helper;

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

    //学生信息
    public static final String MAJOR = "MAJOR";
    public static final String YEAR = "YEAR";
    public static final String STUDENT_ID = "STUDENT_ID";


    //    登录
    public static final String AUTH_CODE = "AUTH_CODE";
//    public static final String REGISTER_ID = "REGISTER_ID";


//    public static final String JPUSH_MUSIC = "JPUSH_MUSIC";
//    public static final String JPUSH_VIB = "JPUSH_VIB";
//    public static final String JPUSH_ROAMING = "JPUSH_ROAMING";

    //  调用百度的token
    public static final String BAIDU_TOKEN = "BAIDU_TOKEN";

    //  匿名聊天
    public static final String MEIQIA_NAME_1 = "MEIQIA_NAME_1";
    public static final String MEIQIA_ROOM_1 = "MEIQIA_ROOM_1";
    public static final String MEIQIA_NAME_2 = "MEIQIA_NAME_2";
    public static final String MEIQIA_ROOM_2 = "MEIQIA_ROOM_2";
    public static final String MEIQIA_COOUNT = "MEIQIA_COOUNT";
    public static final String MERQIA_DATE = "MERQIA_DATE";


    //软键盘高度锁定
    public static final String SOFT_INPUT_HEIGHT = "SOFT_INPUT_HEIGHT";
    public static final String IS_EXCEED_SCEEN = "IS_EXCEED_SCEEN";

    public static final String NOTICE_DATE = "NOTICE_DATE";

    public static String getString(String key) {
        return getString(key, "");
    }

    public static String getString(String key, String defaultValue) {
        return getPreferences().getString(key, defaultValue);
    }

    public static void setString(String key, String value) {
        getPreferences().edit().putString(key, value).apply();
    }

    public static void setInt(String key, int value) {
        getPreferences().edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        return getPreferences().getInt(key, defaultValue);
    }

    public static void setBoolean(String key, boolean defaultValue) {
        getPreferences().edit().putBoolean(key, defaultValue).apply();
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
