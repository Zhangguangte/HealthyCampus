package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

/**
 * OK
 */
public class HomePageLocalDataSource implements HomePageDataSource {

    private static HomePageDataSource INSTANCE;

    public HomePageLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static HomePageDataSource getInstance(@NonNull Context context){
        if(INSTANCE==null)
        {
            INSTANCE= new HomePageLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getLatestNews(@NonNull GetLatestNewsCallback callback) {

    }

    @Override
    public void getBeforeNews(@NonNull String date, @NonNull GetBeforeNewsCallback callback) {

    }

    @Override
    public void getArticle(@NonNull String articleId, @NonNull GetArticleCallback callback) {

    }
}
