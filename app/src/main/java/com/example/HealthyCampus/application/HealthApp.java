package com.example.HealthyCampus.application;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;

import com.example.HealthyCampus.framework.BaseApplication;

/**
 * OK
 */
public class HealthApp extends BaseApplication {

    private static Context appContext;
    @SuppressLint("StaticFieldLeak")
    private static HealthApp INSTANCE;

    private int appCount = 0;

    @Override
    public void onCreate() {
        super.onCreate();
        appContext = getApplicationContext();
        INSTANCE = this;

        initStep();
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

    private void initStep() {
        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle savedInstanceState) {

            }

            @Override
            public void onActivityStarted(Activity activity) {
                appCount++;
            }

            @Override
            public void onActivityResumed(Activity activity) {

            }

            @Override
            public void onActivityPaused(Activity activity) {

            }

            @Override
            public void onActivityStopped(Activity activity) {
                appCount--;
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(Activity activity) {

            }
        });
    }

    /**
     * app是否在前台
     *
     * @return true前台，false后台
     */
    public boolean isForeground() {
        return appCount > 0;
    }

}
