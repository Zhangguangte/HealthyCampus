package com.example.HealthyCampus.module.HomePage.article;

import com.example.HealthyCampus.common.data.model.HomePageArticleBean;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

/**
 * OK
 */
public interface HomePageArticleContract {

    interface View extends BaseView{
        String getArticleId();
        void setArticle(HomePageArticleBean articleBean);
        void loading();
        void loadComplete();
        void loadError();
        void backAction();
    }

    abstract class Presenter extends BasePresenter<View>{
        protected abstract void getArticle(String articleId);
    }
}
