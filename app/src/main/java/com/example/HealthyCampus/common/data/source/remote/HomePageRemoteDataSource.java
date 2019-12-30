package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class HomePageRemoteDataSource implements HomePageDataSource {

    private static HomePageRemoteDataSource INSTANCE = null;


    public static HomePageRemoteDataSource getInstance(){
        if(INSTANCE==null)
        {
            INSTANCE=new HomePageRemoteDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void getLatestNews(@NonNull final GetLatestNewsCallback callback) {
        NetworkManager.getHomePageApi().getHomePageLatestNews().subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(LatestNewsBean -> {
                    try {
                        callback.onLatestNewsLoaded(LatestNewsBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        callback.onDataNotAvailable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull final GetBeforeNewsCallback callback) {
        NetworkManager.getHomePageApi().getHomePageBeforeNews(date).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(bean -> {
                    try {
                        callback.onBeforeNewsLoaded(bean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        callback.onDataNotAvailable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull final GetArticleCallback callback) {
        NetworkManager.getHomePageApi().getHomePageArticle(articleId).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(articleBean -> {
                    try {
                        callback.onLatestNewsLoaded(articleBean);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        callback.onDataNotAvailable();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
