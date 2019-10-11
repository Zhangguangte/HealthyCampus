package com.example.HealthyCampus.module.Mine.Register.second;

import android.content.Context;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

public interface RegisterContract2 {

    //专门来处理view的变化
    interface View extends BaseView {
        Context getContext();

        void setRegisterHint(EditText editText);     //设置输入框提示内容

        void focusRegisterEditTextStatus();        //焦点输入框变化

        void setShowPassword(ImageView imageView, EditText editText, boolean isShow);                     //设置密码可视

        void listenRegisterEditTextStatus();        //监听输入框输入变化

        void setSeePasswordEnable(ImageView imageView,boolean value);    //可视密码图标点击

        void setSignButtonEnable();     //注册发送点击

        void setPageEnable();         //界面点击

        void jumpToMain();             //跳转主界面

        void jumpToRegister1();             //跳转注册界面1

        void jumpToLogin();        //跳转登录界面

        void initPhone();             //初始化电话

        void showRegisterError(Throwable throwable);        //注册失败

        void showProgressView();       //显示加载视图

        void initProgressView();       //设置加载视图

        void dismissProgressView();       //关闭加载视图

        void showTipsView(String username);       //注册成功视图
    }

    //处理业务逻辑
    abstract class Presenter extends BasePresenter<RegisterContract2.View> {
        public abstract void listenRegisterEditText();         //监听按钮

        public abstract void foucusRegisterEditText();         //焦点输入框

        public abstract void register(RegisterFrom registerFrom);        //注册用户信息

        public abstract RegisterFrom encapsulationData(String phone, String _password);     //封装数据

    }

}
