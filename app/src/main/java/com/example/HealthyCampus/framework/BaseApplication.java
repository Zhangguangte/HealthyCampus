package com.example.HealthyCampus.framework;

import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.example.HealthyCampus.application.AppInitFactory;
import com.example.HealthyCampus.common.utils.AppStatusTracker;

import java.util.List;

/**
 * OK
 */
public class BaseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        String currentProcessName = getCurrentProcessName();
        boolean main = isMainProcess();
        if (main) {
            //主进程
            initAppTracker();
        } else {
            //其他进程，如push
        }
        IAppInitialization IAppInitialization = AppInitFactory.getAppInitialization(main, currentProcessName);
        if (IAppInitialization != null) {
            IAppInitialization.onAppCreate(this);
        }
    }

    private void initAppTracker(){
        AppStatusTracker.init(this);
    }


    /**
     * 获取当前进程名称
     *
     * @return
     */
    protected String getCurrentProcessName() {
        String currentProcName = "";
        int pid = android.os.Process.myPid();
        ActivityManager manager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);
        for (ActivityManager.RunningAppProcessInfo processInfo : manager.getRunningAppProcesses()) {
            if (processInfo.pid == pid) {
                currentProcName = processInfo.processName;
                break;
            }
        }
        return currentProcName;
    }

    /**
     * 是否是主进程
     *
     * @return
     */
    protected boolean isMainProcess() {
        ActivityManager am = (ActivityManager) this.getSystemService(ACTIVITY_SERVICE);
        int myPid = android.os.Process.myPid();
        String mainProcessName = this.getCurrentProcessName();
        List<ActivityManager.RunningAppProcessInfo> infos = am.getRunningAppProcesses();
        if (infos == null || infos.size() == 0) {
            return false;
        }
        for (ActivityManager.RunningAppProcessInfo info : infos) {
            if (info.pid == myPid && mainProcessName.equals(info.processName)) {
                return true;
            }
        }
        return false;
    }


}
