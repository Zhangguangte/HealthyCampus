package com.example.HealthyCampus.module.Message.New_friend.List;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;

interface NewFriendListContract {
    interface View extends BaseView {
        Context getContext();

        void clearList();

        void showRequestFriends(ArrayList<RequestFriendVo> requestFriendVos);

        void showSuccess(DefaultResponseVo defaultResponseVo, int position);

        void showError(Throwable throwable);
    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void clearRequestFriends();
        protected abstract void requestFriends();
        protected abstract void saveRequestFriend(RequestForm requestForm, int position);
    }
}
