package com.example.HealthyCampus.application;

import com.example.HealthyCampus.framework.IAppInitialization;

/**
 * OK
 */
public class AppInitFactory {
    public static IAppInitialization getAppInitialization(boolean isMain, String processName) {
        IAppInitialization appInitialization = null;
        if (isMain) {
            appInitialization = new HealthMainInit();
        }
        else if(processName.equals("xxxx:push")){
            //根据processName初始化不同的application
        }
        return appInitialization;
    }
}
