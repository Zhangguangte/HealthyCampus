package com.example.HealthyCampus.module.Message;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface MessageContract {
    interface View extends BaseView {
        void noChatItemVisible(boolean visible);
    }

    abstract class Presenter extends BasePresenter<View> {
    }
}
