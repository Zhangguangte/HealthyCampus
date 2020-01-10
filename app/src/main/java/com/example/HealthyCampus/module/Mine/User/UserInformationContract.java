package com.example.HealthyCampus.module.Mine.User;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface UserInformationContract {
    interface View extends BaseView {
        Context getContext();
        void initUserInfo(UserVo userVo);           //他人信息

        void initUserInfo();            //自己信息

        void backResult();          //返回上一activity

        void showError(Throwable throwable);        //获取信息失败
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getUserInformation(String account);

        protected abstract void searchUser(RequestForm requestForm);
    }
}
