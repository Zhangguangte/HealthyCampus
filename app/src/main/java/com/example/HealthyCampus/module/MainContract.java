package com.example.HealthyCampus.module;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

/**
 * OK
 */
interface MainContract {
    interface View extends BaseView {
        Context getContext();

        void showSnackBar(int resId);
        void finishView();
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void exitApp();
    }

}
