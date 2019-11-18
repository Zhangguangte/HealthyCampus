package com.example.HealthyCampus.module.Message.Chat.Card;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;

interface CardContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);



    }

    abstract class Presenter extends BasePresenter<View> {
    }
}
