package com.example.HealthyCampus.module.Find.News.list.Article;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface HealthNewsArticleContract {
    interface View extends BaseView {
        Context getContext();

    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
