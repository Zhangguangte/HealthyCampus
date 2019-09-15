package com.example.HealthyCampus.common.network;

import com.example.HealthyCampus.application.HealthApp;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.Interceptor.HttpCacheInterceptor;
import com.example.HealthyCampus.common.network.api.HomePageApi;
import com.example.HealthyCampus.common.utils.LogUtil;

import java.io.File;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static HomePageApi homePageApi;

    private NetworkManager() {
    }

    public static HomePageApi getHomePageApi() {
        if (homePageApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp2(ConstantValues.HTTP_CACHE_ENABLE))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_ZHIHU)
                    .build();
            homePageApi = retrofit.create(HomePageApi.class);
        }
        return homePageApi;
    }


    /**
     * 创建okhttp,无加密
     *
     * @param cacheable
     * @return
     */

    private static OkHttpClient createOkHttp2(boolean cacheable) {
        OkHttpClient okHttpClient;
        if (cacheable) {
            okHttpClient = new OkHttpClient.Builder()
                    .cache(createCache()) //设置缓存
                    .addInterceptor(new HttpCacheInterceptor())//本地拦截缓存
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .build();
        }
        else {
            okHttpClient=new OkHttpClient.Builder()
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .build();
        }
        return okHttpClient;
    }

    private static Cache createCache() {
        File cacheFile = new File(HealthApp.getAppContext().getExternalCacheDir(), ConstantValues.HTTP_CACHE_DIR);
        Cache cache = new Cache(cacheFile, ConstantValues.HTTP_CACHE_MAXSIZE);
        return cache;
    }

    private static HttpLoggingInterceptor createLogInterceptor() {
        HttpLoggingInterceptor.Logger logger = new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                LogUtil.logI("http", message);
            }
        };
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

}