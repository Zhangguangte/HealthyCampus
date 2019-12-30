package com.example.HealthyCampus.common.utils;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

import com.example.HealthyCampus.application.HealthApp;

import java.util.ArrayList;
import java.util.List;

public class PackageManagerUtil {

    private static PackageManager mPackageManager = HealthApp.getInstance().getPackageManager();
    private static List<String> mPackageNames = new ArrayList<>();
    private static final String GAODE_PACKAGE_NAME = "com.autonavi.minimap";
    private static final String BAIDU_PACKAGE_NAME = "com.baidu.BaiduMap";


    private static void initPackageManager(){

        List<PackageInfo> packageInfos = mPackageManager.getInstalledPackages(0);

        if (packageInfos != null) {
            for (int i = 0; i < packageInfos.size(); i++) {
                mPackageNames.add(packageInfos.get(i).packageName);
            }
        }
    }

    static boolean haveGaodeMap(){
        initPackageManager();
        return mPackageNames.contains(GAODE_PACKAGE_NAME);
    }

    public static boolean haveBaiduMap(){
        initPackageManager();
        return mPackageNames.contains(BAIDU_PACKAGE_NAME);
    }
}