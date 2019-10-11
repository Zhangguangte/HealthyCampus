package com.example.HealthyCampus.module.HomePage.list;

import com.example.HealthyCampus.common.data.model.BeforeNewsBean;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;
import com.example.HealthyCampus.common.data.source.repository.HomePageRepository;
import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;

/**
 * OK
 */
public class HomePageListPresenter extends HomePageListContract.Presenter {
    @Override
    protected void getLatestNews() {
        HomePageRepository.getInstance().getLatestNews(new HomePageDataSource.GetLatestNewsCallback() {
            @Override
            public void onLatestNewsLoaded(LatestNewsBean latestNewsBean) {
                if (latestNewsBean != null) {
                    getView().refreshList(latestNewsBean);
                }
                getView().loadComplete();
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadComplete();
            }
        });
    }

    @Override
    protected void getBeforeNews(String date) {
        HomePageRepository.getInstance().getBeforeNews(date, new HomePageDataSource.GetBeforeNewsCallback() {
            @Override
            public void onBeforeNewsLoaded(BeforeNewsBean bean) {
                if (bean != null) {
                    getView().addList(bean);
                }
                getView().loadComplete();
            }

            @Override
            public void onDataNotAvailable() {
                getView().loadComplete();
            }
        });
    }

    @Override
    public void onStart() {

    }
}
