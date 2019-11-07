package com.example.HealthyCampus.module.Message.New_friend;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;

interface NewFriendContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(DefaultResponseVo defaultResponseVo,int position);

        void showRequestFriends(ArrayList<RequestFriendVo> requestFriendVos);

        void initRvNotice();

    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void requestFriends();

        protected abstract void saveRequestFriend(RequestForm requestForm,int position);
    }
}
