package com.example.HealthyCampus.application;

import android.content.Context;

import com.example.HealthyCampus.framework.BaseApplication;

/**
 * OK
 */
public class HealthApp extends BaseApplication {

    private static Context appContext;
    private static HealthApp INSTANCE;
    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        INSTANCE = this;
    }

    public static Context getAppContext() {
        return appContext;
    }

    public static HealthApp getInstance() {
        return INSTANCE;
    }

    @Override
    public void onTerminate() {
        super.onTerminate();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
    }
}
