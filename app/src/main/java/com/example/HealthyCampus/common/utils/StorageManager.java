package com.example.HealthyCampus.common.utils;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Environment;

import java.io.File;

public class StorageManager {

    @SuppressLint("StaticFieldLeak")
    private static StorageManager INSTANCE;
    private Context mContext;

    private boolean mExternalStorageAvailable = false;

    public synchronized static StorageManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new StorageManager();
        }
        return INSTANCE;
    }

    public void setAppContext(Context context) {
        mContext = context;
        updateExternalStorageState();
        startWatching();
    }

    private void updateExternalStorageState() {
        String state = Environment.getExternalStorageState();
        if (Environment.MEDIA_MOUNTED.equals(state)) {
            mExternalStorageAvailable = true;
        } else mExternalStorageAvailable = Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }

    private void startWatching() {
        if (mContext == null) {
            return;
        }
        BroadcastReceiver mExternalStorageReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                updateExternalStorageState();
            }
        };

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_MEDIA_MOUNTED);
        filter.addAction(Intent.ACTION_MEDIA_REMOVED);
        mContext.registerReceiver(mExternalStorageReceiver, filter);
    }

    public void setRootDir(String rootDir) {
        File f = getCacheDir(mContext, rootDir);
        if (!f.exists()) {
            f.mkdir();
        }
    }

    private File getCacheDir(Context context, String uniqueName) {
        String cachePath = isExternalStorageAvailable() || !isExternalStorageRemovable() ?
                getExternalStorageDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    private boolean isExternalStorageAvailable() {
        return mExternalStorageAvailable;
    }

    /**
     * 主外部存储设备是否是可拆卸的
     *
     * @return
     */
    private boolean isExternalStorageRemovable() {
        return Environment.isExternalStorageRemovable();
    }

    private File getExternalStorageDir() {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }


    public void setImageDir(String rootDir) {
        File f = getCacheDir(mContext, rootDir);
        f.mkdir();
    }


}
