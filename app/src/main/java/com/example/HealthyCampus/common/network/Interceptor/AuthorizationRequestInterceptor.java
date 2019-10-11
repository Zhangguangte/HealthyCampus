package com.example.HealthyCampus.common.network.Interceptor;

import android.text.TextUtils;

import com.example.HealthyCampus.common.helper.SPHelper;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationRequestInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        //拦截响应
        Response response = chain.proceed(request);
        response.newBuilder().addHeader("User-Agent", "HealthyCampus-Android");
        String authCode = SPHelper.getString(SPHelper.AUTH_CODE);
        if (!TextUtils.isEmpty(authCode)) {
            response.newBuilder().addHeader("Authorization", authCode).build();
        }
        return null;
    }
}
