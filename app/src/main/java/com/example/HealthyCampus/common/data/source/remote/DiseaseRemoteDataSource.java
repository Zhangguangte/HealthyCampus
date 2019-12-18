package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class DiseaseRemoteDataSource implements DiseaseDataSource {

    private static DiseaseRemoteDataSource INSTANCE = null;


    public DiseaseRemoteDataSource() {
    }

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
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSort:7");
                    }
                })
                .subscribe(new Action1<List<DiseaseSortVo>>() {
                    @Override
                    public void call(List<DiseaseSortVo> diseaseSortListVos) {
                        try {
                            Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSort success");
                            callback.onDataAvailable(diseaseSortListVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSort:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getDiseaseSortList(@NonNull RequestForm requestForm, @NonNull DiseaseGetSortList callback) {
        NetworkManager.getInstance().getDiseaseApi()
                .getDiseaseSortList(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSortList:7");
                    }
                })
                .subscribe(new Action1<List<DiseaseSortListVo>>() {
                    @Override
                    public void call(List<DiseaseSortListVo> diseasSortVos) {
                        try {
                            Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSortList success");
                            callback.onDataAvailable(diseasSortVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("DiseaseRemoteDe" + "123456", "getDiseaseSortList:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getDiseaseDetail(@NonNull RequestForm requestForm, @NonNull DiseaseGetDetail callback) {
        NetworkManager.getInstance().getDiseaseApi()
                .getDiseaseDetail(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("DiseaseRemoteDe" + "123456", "getDiseaseDetail:7");
                    }
                })
                .subscribe(new Action1<DiseaseDetailVo>() {
                    @Override
                    public void call(DiseaseDetailVo diseaseDetailVo) {
                        try {
                            Log.e("DiseaseRemoteDe" + "123456", "getDiseaseDetail success");
                            callback.onDataAvailable(diseaseDetailVo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("DiseaseRemoteDe" + "123456", "getDiseaseDetail:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

}
