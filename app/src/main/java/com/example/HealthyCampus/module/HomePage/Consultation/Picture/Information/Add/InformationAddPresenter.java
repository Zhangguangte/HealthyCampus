package com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information.Add;


import android.util.Log;

import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.utils.PictureUtil;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.example.HealthyCampus.common.network.NetworkManager.createLogInterceptor;

public class InformationAddPresenter extends InformationAddContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void upPicture(String path, String name, String account) {
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(createLogInterceptor()).build(); //请求日志拦截;
        MultipartBody.Builder mul = new MultipartBody.Builder();
        RequestBody body;
        String bitmapStr = PictureUtil.imageToBase64(path.replace("file://", ""));
        mul.addFormDataPart(name, bitmapStr);
        body = mul.build();
        Request request = new Request.Builder().url("http://192.168.2.109:8095/GETE/UpImage").post(body)
                .addHeader("account", SPHelper.getString(SPHelper.USER_ID)).build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("ChatPresenter123456", "SavePicture:不可以可以3");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Log.v("ChatPresenter123456", "SavePicture:可以可以3");
            }
        });
    }

}
