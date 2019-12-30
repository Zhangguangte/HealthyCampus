package com.example.HealthyCampus.common.network;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.application.HealthApp;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.Interceptor.AuthorizationRequestInterceptor;
import com.example.HealthyCampus.common.network.Interceptor.HttpCacheInterceptor;
import com.example.HealthyCampus.common.network.api.BaiduApi;
import com.example.HealthyCampus.common.network.api.ConsultApi;
import com.example.HealthyCampus.common.network.api.DiseaseApi;
import com.example.HealthyCampus.common.network.api.FriendApi;
import com.example.HealthyCampus.common.network.api.HomePageApi;
import com.example.HealthyCampus.common.network.api.MedicineApi;
import com.example.HealthyCampus.common.network.api.MessageApi;
import com.example.HealthyCampus.common.network.api.RecipesApi;
import com.example.HealthyCampus.common.network.api.ServiceApi;
import com.example.HealthyCampus.common.network.api.UploadApi;
import com.example.HealthyCampus.common.network.api.UserApi;
import com.example.HealthyCampus.common.utils.LogUtil;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Protocol;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager INSTANCE;

    private static HomePageApi homePageApi;
    private static MedicineApi medicinePageApi;
    private static UserApi userApi;
    private static MessageApi messageApi;
    private static FriendApi friendApi;
    private static UploadApi uploadApi;
    private static RecipesApi recipesApi;
    private static DiseaseApi diseaseApi;
    private static BaiduApi baiduApi;
    private static ConsultApi consultApi;
    private static ServiceApi serviceApi;

    private Handler handler = new Handler(Looper.getMainLooper());

    private NetworkManager() {
    }


    public static HomePageApi getHomePageApi() {
        if (homePageApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp2())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_ZHIHU)
                    .build();

            homePageApi = retrofit.create(HomePageApi.class);
        }
        return homePageApi;
    }

//    public static MedicineApi getMedicineApi() {
//        if (medicinePageApi == null) {
//            Retrofit retrofit = new Retrofit.Builder()
//                    .client(createOkHttp2(ConstantValues.HTTP_CACHE_ENABLE))
//                    .addConverterFactory(GsonConverterFactory.create())
//                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
//                    .baseUrl(ConstantValues.BASE_URL_MEDICINE)
//                    .build();
//
//            medicinePageApi = retrofit.create(MedicineApi.class);
//        }
//        return medicinePageApi;
//    }


    public static NetworkManager getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NetworkManager();
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp2())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_HEALTH)
                    .build();
            userApi = retrofit.create(UserApi.class);
            messageApi = retrofit.create(MessageApi.class);
            friendApi = retrofit.create(FriendApi.class);
            medicinePageApi = retrofit.create(MedicineApi.class);
            recipesApi = retrofit.create(RecipesApi.class);
            diseaseApi = retrofit.create(DiseaseApi.class);
            consultApi = retrofit.create(ConsultApi.class);
            serviceApi = retrofit.create(ServiceApi.class);
        }
        return INSTANCE;
    }

    public static UploadApi getUploadApi() {
        if (uploadApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttp2())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_GETE)
                    .build();
            uploadApi = retrofit.create(UploadApi.class);
        }
        return uploadApi;
    }

    public static BaiduApi getBaiduApi() {
        if (baiduApi == null) {
            Retrofit retrofit = new Retrofit.Builder()
                    .client(createOkHttps())
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .baseUrl(ConstantValues.BASE_URL_BAIDU)
                    .build();

            baiduApi = retrofit.create(BaiduApi.class);
        }
        return baiduApi;
    }


//    /**
//     * 创建okhttp,无加密
//     *
//     * @param cacheable
//     * @return
//     */
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
     */
    private static OkHttpClient createOkHttps() {
        OkHttpClient okHttpClient;
        Log.e("NetworkManager" + "123456", "cacheable" + ConstantValues.HTTP_CACHE_ENABLE);
        if (ConstantValues.HTTP_CACHE_ENABLE) {
            okHttpClient = new OkHttpClient.Builder()
                    .cache(createCache()) //设置缓存
                    .addInterceptor(new HttpCacheInterceptor())//本地拦截缓存
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .authenticator(new AuthorizationRequestInterceptor())
                    .retryOnConnectionFailure(true)
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();
        } else {
            okHttpClient = new OkHttpClient.Builder()
                    .addInterceptor(createLogInterceptor()) //请求日志拦截
                    .retryOnConnectionFailure(true)
                    .protocols(Collections.singletonList(Protocol.HTTP_1_1))
                    .authenticator(new AuthorizationRequestInterceptor())
                    .connectTimeout(15, TimeUnit.SECONDS)
                    .build();
        }
        return okHttpClient;
    }


    /**
     * 创建okhttp,无加密
     */
    private static OkHttpClient createOkHttp2() {
        OkHttpClient okHttpClient;
        Log.e("NetworkManager" + "123456", "cacheable" + ConstantValues.HTTP_CACHE_ENABLE);
        if (ConstantValues.HTTP_CACHE_ENABLE) {
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
        return new Cache(cacheFile, ConstantValues.HTTP_CACHE_MAXSIZE);
    }

    public static HttpLoggingInterceptor createLogInterceptor() {
        HttpLoggingInterceptor.Logger logger = message -> LogUtil.logI("http", message);
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(logger);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        return loggingInterceptor;
    }

    public static void getAsync(String url, DataCallBack callBack) {
        getInstance().p_getAsync(url, callBack);
    }

    public static void postAsyncParams(String url, Map<String, String> params, DataCallBack callBack) {
        getInstance().p_postAsyncParams(url, params, callBack);
    }

    private void p_getAsync(String url, final DataCallBack callBack) {
        final Request request = new Request.Builder().url(url).build();
        OkHttpClient client = new OkHttpClient();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                deliverFailure(request, e, callBack);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    assert response.body() != null;
                    deliverSuccess(response.body().string(), callBack);
                } catch (IOException e) {
                    deliverFailure(request, e, callBack);
                }
            }
        });
    }

    private void p_postAsyncParams(String url, Map<String, String> params, final DataCallBack callBack) {
        RequestBody requestBody;
        if (params == null) {
            params = new HashMap<>();
        }
        OkHttpClient client = new OkHttpClient();
        FormBody.Builder builder = new FormBody.Builder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String key = entry.getKey().toString();
            String value = entry.getValue() == null ? "" : entry.getValue();
            builder.add(key, value);
        }
        requestBody = builder.build();
        final Request request = new Request.Builder().url(url).post(requestBody).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NonNull Call call, @NonNull IOException e) {
                deliverFailure(request, e, callBack);
            }

            @Override
            public void onResponse(@NonNull Call call, @NonNull Response response) {
                try {
                    assert response.body() != null;
                    deliverSuccess(response.body().string(), callBack);
                } catch (IOException e) {
                    deliverFailure(request, e, callBack);
                }
            }
        });
    }


    //*************数据回调接口************************
    public interface DataCallBack {
        void requestFailure(Request request, Exception e);

        void requestSuccess(String result);
    }

    //*************************数据请求成功或者失败分发方法**********************

    /**
     * 进行分发请求失败的数据情况
     */
    private void deliverFailure(final Request request, final IOException e, final DataCallBack callBack) {
        handler.post(() -> {
            if (callBack != null) {
                callBack.requestFailure(request, e);
            }
        });
    }

    /**
     * 请求分发请求成功的数据情况
     */
    private void deliverSuccess(final String result, final DataCallBack callBack) {
        handler.post(() -> {
            if (callBack != null) {
                callBack.requestSuccess(result);
            }
        });
    }
    /*----Getter----*/

    public UserApi getUserApi() {
        return userApi;
    }

    public MessageApi getMessageApi() {
        return messageApi;
    }

    public FriendApi getFriendApi() {
        return friendApi;
    }

    public MedicineApi getMedicineApi() {
        return medicinePageApi;
    }

    public RecipesApi getRecipesApi() {
        return recipesApi;
    }

    public DiseaseApi getDiseaseApi() {
        return diseaseApi;
    }

    public ConsultApi getConsultApi() {
        return consultApi;
    }

    public ServiceApi getServiceApi() {
        return serviceApi;
    }
}
