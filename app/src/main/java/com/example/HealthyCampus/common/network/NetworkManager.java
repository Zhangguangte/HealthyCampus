package com.example.HealthyCampus.common.network;

import com.example.HealthyCampus.application.HealthApp;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.Interceptor.AuthorizationRequestInterceptor;
import com.example.HealthyCampus.common.network.Interceptor.HttpCacheInterceptor;
import com.example.HealthyCampus.common.network.api.FriendApi;
import com.example.HealthyCampus.common.network.api.HomePageApi;
import com.example.HealthyCampus.common.network.api.MessageApi;
import com.example.HealthyCampus.common.network.api.UploadApi;
import com.example.HealthyCampus.common.network.api.UserApi;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.PictureUtil;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Authenticator;
import okhttp3.Cache;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit.RestAdapter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager INSTANCE;
    private static NetworkManager INSTANCE1;
    private RestAdapter easyImRestAdapter;

    private static HomePageApi homePageApi;
    private static UserApi userApi;
    private static MessageApi messageApi;
    private static FriendApi friendApi;
    private static UploadApi uploadApi;
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
    public static NetworkManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkManager();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp2(ConstantValues.HTTP_CACHE_ENABLE))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_HEALTH)
                    .build();
            INSTANCE.userApi = retrofit.create(UserApi.class);
            INSTANCE.messageApi = retrofit.create(MessageApi.class);
            INSTANCE.friendApi = retrofit.create(FriendApi.class);
        }
        return INSTANCE;
    }

    public static UploadApi getUploadApi() {
        if (uploadApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp2(ConstantValues.HTTP_CACHE_ENABLE))
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_GETE)
                    .build();
            uploadApi = retrofit.create(UploadApi.class);
        }
        return uploadApi;
    }

    /**
     * 创建okhttp,无加密
     *
     * @param cacheable
     * @return
     */
//    private static OkHttpClient createOkHttp3(boolean cacheable) {
//        OkHttpClient okHttpClient;
//        if (cacheable) {
//            okHttpClient = new OkHttpClient.Builder()
//                    .cache(createCache()) //设置缓存
//                    .addInterceptor(new HttpCacheInterceptor())//本地拦截缓存
//                    .addInterceptor(createLogInterceptor()) //请求日志拦截
//                    .authenticator(new AuthorizationRequestInterceptor())
//                    .addInterceptor(new Interceptor() {
//                        @Override
//                        public Response intercept(Chain chain) throws IOException {
//                            Request request = chain.request();
//                            Request.Builder builder = request.newBuilder();
//                            if(request.method() .equals("POST"))
//                            {
//                                RequestBody requestBody = request.body();
//                                if(requestBody instanceof PostJsonBody)
//
//                            }
//                            return chain.proceed()
//                        }
//                    })
//                    .retryOnConnectionFailure(true)
//                    .connectTimeout(15, TimeUnit.SECONDS)
//                    .build();
//        } else {
//            okHttpClient = new OkHttpClient.Builder()
//                    .addInterceptor(createLogInterceptor()) //请求日志拦截
//                    .retryOnConnectionFailure(true)
//                    .authenticator(new AuthorizationRequestInterceptor())
//                    .connectTimeout(15, TimeUnit.SECONDS)
//                    .build();
//        }
//        return okHttpClient;
//    }




    /**
     * 创建okhttp,无加密
     *
     * @param cacheable
     * @return
     */
    public static OkHttpClient createOkHttp2(boolean cacheable) {
        OkHttpClient okHttpClient;
        if (cacheable) {
            okHttpClient = new OkHttpClient.Builder()
                    .cache(createCache()) //设置缓存
                    .addInterceptor(new HttpCacheInterceptor())//本地拦截缓存
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .authenticator(new AuthorizationRequestInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .retryOnConnectionFailure(true)
                    .authenticator(new AuthorizationRequestInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }

    private static Cache createCache() {
        File cacheFile = new File(HealthApp.getAppContext().getExternalCacheDir(), ConstantValues.HTTP_CACHE_DIR);
        Cache cache = new Cache(cacheFile, ConstantValues.HTTP_CACHE_MAXSIZE);
        return cache;
    }

    public  static HttpLoggingInterceptor createLogInterceptor() {
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

    /*----Getter----*/

    public static UserApi getUserApi() {
        return userApi;
    }

    public static MessageApi getMessageApi() {
        return messageApi;
    }

    public static FriendApi getFriendApi() {
        return friendApi;
    }

}
