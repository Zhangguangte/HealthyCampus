package com.example.HealthyCampus.module.Mine.Feedback;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface FeedbackContract {
    interface View extends BaseView {
        Context getContext();


    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void sendFeed(String advice, String contract);
    }
}
