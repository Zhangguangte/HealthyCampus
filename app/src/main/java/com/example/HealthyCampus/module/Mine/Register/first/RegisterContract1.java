package com.example.HealthyCampus.module.Mine.Register.first;

import android.content.Context;
import android.widget.EditText;

import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

public interface RegisterContract1 {

    //专门来处理view的变化
    interface View extends BaseView {
        Context getContext();

        void initSMS();    //初始化短信配置

        void generatePictureCode();      //设置图片验证码

        void setSMSTextViewEnable();     //短信发送点击

        void setNextButtonEnable();     //短信发送点击

        void setRegisterHint(EditText editText);     //设置输入框提示内容

        void listenRegisterEditTextStatus();        //监听输入框输入变化

        void focusRegisterEditTextStatus();        //焦点输入框变化

        void jumpToNext();        //跳转注册界面2

        void jumpToLogin();        //跳转登录界面

        void phoneExist();       //号码已存在

        void phoneNotExist();       //号码不存在

        void showProgressView();       //显示加载视图

        void initProgressView();       //设置加载视图

        void dismissProgressView();       //关闭加载视图

    }

    //处理业务逻辑
    abstract class Presenter extends BasePresenter<RegisterContract1.View> {

        public abstract void listenRegisterEditText();         //监听按钮

        public abstract void foucusRegisterEditText();         //焦点输入框

        public abstract void searchPhone(RegisterFrom registerFrom);         //查询手机号码

    }

}
