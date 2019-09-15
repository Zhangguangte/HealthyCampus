package com.example.HealthyCampus.common.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Environment;

import java.io.File;

public class StorageManager {

    private static StorageManager INSTANCE;
    private Context mContext;

    private BroadcastReceiver mExternalStorageReceiver;

    private boolean mExternalStorageAvailable = false;
    private boolean mExternalStorageWriteable = false;

    private File mRoot;
    private File mImageDir;

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
            mExternalStorageAvailable = mExternalStorageWriteable = true;
        } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
            mExternalStorageAvailable = true;
            mExternalStorageWriteable = false;
        } else {
            mExternalStorageAvailable = mExternalStorageWriteable = false;
        }
    }

    public void startWatching() {
        if (mContext == null) {
            return;
        }
        mExternalStorageReceiver = new BroadcastReceiver() {
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
        if (f != null && !f.exists()) {
            f.mkdir();
        }
        this.mRoot = f;
    }

    private File getCacheDir(Context context, String uniqueName) {
        String cachePath = isExternalStorageAvailable() || !isExternalStorageRemovable() ?
                getExternalStorageDir().getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public boolean isExternalStorageAvailable() {
        return mExternalStorageAvailable;
    }

    /**
     * 主外部存储设备是否是可拆卸的
     *
     * @return
     */
    private boolean isExternalStorageRemovable() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            return Environment.isExternalStorageRemovable();
        }
        return true;
    }

    private File getExternalStorageDir() {
        return new File(Environment.getExternalStorageDirectory().getPath());
    }


    public void setImageDir(String rootDir) {
        File f = getCacheDir(mContext, rootDir);
        if (f != null || !f.exists()) {
            f.mkdir();
        }
        this.mImageDir = f;
    }


}
