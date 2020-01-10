package com.example.HealthyCampus.common.network.Interceptor;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.HealthyCampus.common.helper.SPHelper;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AuthorizationRequestInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder request = chain.request().newBuilder()
                .addHeader("User-Agent", "HealthyCampus-Android");
        String authCode = SPHelper.getString(SPHelper.AUTH_CODE);
        if (!TextUtils.isEmpty(authCode)) {
            request.addHeader("Authorization", authCode);
        }
        return chain.proceed(request.build());
    }
}