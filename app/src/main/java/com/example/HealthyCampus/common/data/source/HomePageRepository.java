package com.example.HealthyCampus.common.data.source;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * OK
 */
public class HomePageRepository implements HomePageDataSource {

    private final HomePageDataSource mHomePageRemoteDataSource;
    private final HomePageDataSource mHomePageLocalDataSource;

    private static HomePageRepository INSTANCE = null;

    private HomePageRepository(HomePageDataSource homePageRemoteDataSource, HomePageDataSource homePageLocalDataSource) {
        mHomePageRemoteDataSource = checkNotNull(homePageRemoteDataSource);
        mHomePageLocalDataSource = checkNotNull(homePageLocalDataSource);
    }

    public static void initialize(HomePageDataSource homePageRemoteDataSource, HomePageDataSource homePageLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new HomePageRepository(homePageRemoteDataSource, homePageLocalDataSource);
    }

    public static HomePageRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the repository must be init !");
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public void getLatestNews(@NonNull GetLatestNewsCallback callback) {
        mHomePageRemoteDataSource.getLatestNews(callback);
    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback) {
        mHomePageRemoteDataSource.getBeforeNews(date, callback);
    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback) {
        mHomePageRemoteDataSource.getArticle(articleId, callback);
    }

}
