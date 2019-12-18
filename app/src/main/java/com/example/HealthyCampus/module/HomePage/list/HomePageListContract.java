package com.example.HealthyCampus.module.HomePage.list;

import com.example.HealthyCampus.common.data.model.BeforeNewsBean;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

/**
 * OK
 */
public interface HomePageListContract {

    interface View extends BaseView {
        void refreshList(LatestNewsBean latestNewsBean);

        void addList(BeforeNewsBean beforeNewsBean);
        void loadComplete();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getLatestNews();

        protected abstract void getBeforeNews(String date);

    }
}
