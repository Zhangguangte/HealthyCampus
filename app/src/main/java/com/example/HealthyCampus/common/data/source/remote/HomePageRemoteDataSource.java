package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.model.BeforeNewsBean;
import com.example.HealthyCampus.common.data.model.HomePageArticleBean;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;
import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class HomePageRemoteDataSource implements HomePageDataSource {

    private static HomePageRemoteDataSource INSTANCE = null;


    public HomePageRemoteDataSource() {
    }

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
                .subscribe(new Action1<LatestNewsBean>() {
                    @Override
                    public void call(LatestNewsBean LatestNewsBean) {
                        callback.onLatestNewsLoaded(LatestNewsBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull final GetBeforeNewsCallback callback) {
        NetworkManager.getHomePageApi().getHomePageBeforeNews(date).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<BeforeNewsBean>() {
                    @Override
                    public void call(BeforeNewsBean bean) {
                        callback.onBeforeNewsLoaded(bean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull final GetArticleCallback callback) {
        NetworkManager.getHomePageApi().getHomePageArticle(articleId).subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .subscribe(new Action1<HomePageArticleBean>() {
                    @Override
                    public void call(HomePageArticleBean articleBean) {
                        callback.onLatestNewsLoaded(articleBean);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable();
                    }
                });
    }
}
