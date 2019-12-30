package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.source.callback.ConsultDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.data.form.ConsultPictureForm;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ConsultRemoteDataSource implements ConsultDataSource {

    private static ConsultRemoteDataSource INSTANCE = null;


    private ConsultRemoteDataSource() {
    }

    public static ConsultRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConsultRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void saveConsultPicture(@NonNull ConsultPictureForm consultPictureForm, @NonNull ConsultUpPicture callback) {
        NetworkManager.getInstance().getConsultApi()
                .saveConsultPicture(consultPictureForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("ConsultRemoteDa" + "123456", "sendRequestFriend:7"))
                .subscribe(defaultResponseVo -> {
                    try {
                        Log.e("ConsultRemoteDa" + "123456", "sendRequestFriend success");
                        callback.onDataAvailable(defaultResponseVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

}
