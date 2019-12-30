package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class ServiceRemoteDataSource implements ServiceDataSource {

    private static ServiceRemoteDataSource INSTANCE = null;


    private ServiceRemoteDataSource() {
    }

    public static ServiceRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ServiceRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void searchBook(@NonNull RequestForm requestForm, @NonNull GetSearchBook callback) {
        NetworkManager.getInstance().getServiceApi()
                .searchBook(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("ServiceRemoteDa" + "123456", "searchBook:7"))
                .subscribe(bookVos -> {
                    try {
                        Log.e("ServiceRemoteDa" + "123456", "searchBook success");
                        callback.onDataAvailable(bookVos);
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

    @Override
    public void searchBookDetail(@NonNull RequestForm requestForm, @NonNull GetBookDetail callback) {
        NetworkManager.getInstance().getServiceApi()
                .searchBookDetail(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("ServiceRemoteDa" + "123456", "searchBookDetail:7"))
                .subscribe(bookDetailVo -> {
                    try {
                        Log.e("ServiceRemoteDa" + "123456", "searchBookDetail success");
                        callback.onDataAvailable(bookDetailVo);
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
