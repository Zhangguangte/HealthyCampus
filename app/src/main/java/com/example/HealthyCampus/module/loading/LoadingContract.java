package com.example.HealthyCampus.module.Loading;

import android.content.Context;
import android.view.animation.Animation;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

/**
 * OK
 */
interface LoadingContract {
    interface View extends BaseView {
        Context getContext();
        Animation createBackgroundAnimation();
        Animation createLogoAnimation();
        void jumpToMain();
    }

    abstract class Presenter extends BasePresenter<View> {
        //public abstract void getData(String content);
    }
}
