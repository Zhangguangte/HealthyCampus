package com.example.HealthyCampus.common.network.Interceptor;


import android.support.annotation.NonNull;

import com.example.HealthyCampus.application.HealthApp;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.utils.NetworkUtil;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;


/**
 * OK
 */
public class HttpCacheInterceptor implements Interceptor {
    @Override
    public Response intercept(@NonNull Chain chain) throws IOException {
        Request request = chain.request();

        //无网络状态下,手动拦截请求，使其使用本地缓存
        if (!NetworkUtil.isNetworkConnectivity(HealthApp.getAppContext())) {
            request = request.newBuilder().cacheControl(CacheControl.FORCE_CACHE).build();
        }

        //拦截响应
        Response response = chain.proceed(request);
        if (NetworkUtil.isNetworkConnectivity(HealthApp.getAppContext())) {
            // 有网络时 默认缓存超时为0
            int maxAge = 0;
            response.newBuilder().header("Cache-Control", "public, max-age=" + maxAge).build();
        } else {
            // 无网络时，设置超时时间
            int maxStale = ConstantValues.HTTP_CACHE_TIME;
            response.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + maxStale).build();
        }
        return null;
    }
}
