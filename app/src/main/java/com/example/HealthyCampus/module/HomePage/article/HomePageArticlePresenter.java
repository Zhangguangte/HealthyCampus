package com.example.HealthyCampus.module.HomePage.article;

import com.example.HealthyCampus.common.data.model.HomePageArticleBean;
import com.example.HealthyCampus.common.data.source.HomePageRepository;
import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;

/**
 * OK
 */
public class HomePageArticlePresenter extends HomePageArticleContract.Presenter {


    @Override
    protected void getArticle(String articleId) {
        HomePageRepository.getInstance().getArticle(articleId, new HomePageDataSource.GetArticleCallback() {
            @Override
            public void onLatestNewsLoaded(HomePageArticleBean articleBean) {
                getView().loadComplete();
                if (articleBean != null) {
                    getView().setArticle(articleBean);
                }
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadError();
            }
        });
    }

    @Override
    public void onStart() {
        getView().loading();
    }
}
