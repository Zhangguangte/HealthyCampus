package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.model.BeforeNewsBean;
import com.example.HealthyCampus.common.data.model.HomePageArticleBean;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;

/**
 * OK
 */
public interface HomePageDataSource {

    interface GetLatestNewsCallback {
        void onLatestNewsLoaded(LatestNewsBean latestNewsBean);

        void onDataNotAvailable();

    }

    interface GetBeforeNewsCallback {

        void onBeforeNewsLoaded(BeforeNewsBean bean);

        void onDataNotAvailable();
    }

    interface GetArticleCallback {

        void onLatestNewsLoaded(HomePageArticleBean homePageArticleBean);

        void onDataNotAvailable();
    }

    void getLatestNews(@NonNull GetLatestNewsCallback callback);

    void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback);

    void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback);
}
