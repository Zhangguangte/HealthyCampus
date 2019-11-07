package com.example.HealthyCampus.module.Message.New_friend.Apply_Friend;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;

interface ApplyFriendContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(DefaultResponseVo defaultResponseVo, int i);

        void initApplyView();
        void requestResponse(String userid);

        void jumpToUserInformation(UserVo userVo);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void saveRequestFriend(RequestForm requestForm);

        protected abstract void refuseRequestFriend(RequestForm requestForm);

        protected abstract void searchUser(RequestForm requestForm);
    }
}
