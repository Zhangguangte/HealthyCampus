package com.example.HealthyCampus.common.network.Interceptor;

import android.text.TextUtils;
import android.util.Log;

import com.example.HealthyCampus.common.helper.SPHelper;

import java.io.IOException;

import okhttp3.Authenticator;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;

public class AuthorizationRequestInterceptor implements Authenticator {

    @Override
    public Request authenticate(Route route, Response response) throws IOException {

        Request.Builder request =  response.request().newBuilder()
                .addHeader("User-Agent", "HealthyCampus-Android");

        String authCode = SPHelper.getString(SPHelper.AUTH_CODE);
        Log.e("AuthorizationReq"+"123456","authCode"+authCode);
        if (!TextUtils.isEmpty(authCode)) {
            request.addHeader("Authorization", authCode);
        }
        return request.build();
    }
}