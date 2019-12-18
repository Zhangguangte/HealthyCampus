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
        void onLatestNewsLoaded(LatestNewsBean latestNewsBean) throws Exception;

        void onDataNotAvailable() throws Exception;

    }

    interface GetBeforeNewsCallback {

        void onBeforeNewsLoaded(BeforeNewsBean bean) throws Exception;

        void onDataNotAvailable() throws Exception;
    }

    interface GetArticleCallback {

        void onLatestNewsLoaded(HomePageArticleBean homePageArticleBean) throws Exception;

        void onDataNotAvailable() throws Exception;
    }

    void getLatestNews(@NonNull GetLatestNewsCallback callback);

    void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback);

    void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback);
}
