package com.example.HealthyCampus.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {


    /**
     * 判断当前网络是否已连接
     * 需添加权限：
     * <pre>
     *     android.permission.ACCESS_NETWORK_STATE
     * </pre>
     *
     * @param context 上下文
     * @return true 网络已连接
     */
    public static boolean isNetworkConnectivity(Context context) {
        if (null == context) {
            return false;
        }
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

}
