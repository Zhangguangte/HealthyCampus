package com.example.health;

import android.app.Application;
import android.content.Context;

import com.facebook.drawee.backends.pipeline.Fresco;

public class MyApplication extends Application {

    private static MyApplication mcontext;

    @Override
    public void onCreate() {
        super.onCreate();
        Fresco.initialize(this);
        mcontext = this; //初始化mContext
    }

    /**
     * 获取context
     * @return
     */
    public static Context getInstance()
    {
        if(mcontext == null)
        {
            mcontext = new MyApplication();
        }
        return mcontext;
    }

}
