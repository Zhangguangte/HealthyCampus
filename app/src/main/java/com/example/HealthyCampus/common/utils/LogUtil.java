package com.example.HealthyCampus.common.utils;

import android.util.Log;

import com.example.HealthyCampus.BuildConfig;

public class LogUtil {

    public static boolean DEBUG = BuildConfig.DEBUG;
    public static void logI(String tag, String msg) {
        if (DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void logE(String tag, String msg) {
        if (DEBUG) {
            Log.e(tag, msg);
        }
    }

}
