package com.example.HealthyCampus.module.Message.New_friend.Add_Friend.Add_Friend_Msg;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface AddFriendMsgContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void sendSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void sendRequestFriend(RequestForm requestForm);

        protected abstract RequestForm encapsulationData(String quest_id, String content);     //封装数据
    }
}
