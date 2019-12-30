package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class DiseaseRemoteDataSource implements DiseaseDataSource {

    private static DiseaseRemoteDataSource INSTANCE = null;


    public static DiseaseRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new DiseaseRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getDiseaseSort(@NonNull RequestForm requestForm, @NonNull DiseaseGetSort callback) {
        NetworkManager.getInstance().getDiseaseApi()
                .getDiseaseSort(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSort:7"))
                .subscribe(diseaseSortListVos -> {
                    try {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSort success");
                        callback.onDataAvailable(diseaseSortListVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSort:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getDiseaseSortList(@NonNull RequestForm requestForm, @NonNull DiseaseGetSortList callback) {
        NetworkManager.getInstance().getDiseaseApi()
                .getDiseaseSortList(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSortList:7"))
                .subscribe(diseasSortVos -> {
                    try {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSortList success");
                        callback.onDataAvailable(diseasSortVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSortList:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getDiseaseDetail(@NonNull RequestForm requestForm, @NonNull DiseaseGetDetail callback) {
        NetworkManager.getInstance().getDiseaseApi()
                .getDiseaseDetail(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("DiseaseRemoteDe" + "123456", "getDiseaseDetail:7"))
                .subscribe(diseaseDetailVo -> {
                    try {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseDetail success");
                        callback.onDataAvailable(diseaseDetailVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseDetail:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }



}
