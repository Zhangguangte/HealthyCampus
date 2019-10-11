package com.example.HealthyCampus.module.Mine.Login;

import android.util.Base64;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.utils.StringUtil;

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    public void login(LoginForm dataForm) {
        getView().showProgressView();
        UserRepository.getInstance().login(dataForm, new UserDataSource.UserLogin() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().dismissProgressView();
                getView().showLoginError(throwable);
            }

            @Override
            public void loginSuccess() {
                getView().dismissProgressView();
                getView().jumpToMain();
            }
        });

    }

    @Override
    public void listenLoginEditText() {
        getView().listenLoginEditTextStatus();
    }

    @Override
    public LoginForm encapsulationData(String username, String _password) {
        String deviceId = SPHelper.getString(SPHelper.REGISTER_ID);
        final String password = StringUtil.toMD5(_password);
        return new LoginForm(deviceId, username, password);
    }

    @Override
    public void foucusLoginEditText() {
        getView().focusLoginEditTextStatus();
    }

    @Override
    public void setAuthCode(String username, String password) {
        String authCode = username + ":" + password;
        authCode = "Basic " + Base64.encodeToString(authCode.getBytes(), Base64.DEFAULT);
        SPHelper.setString(SPHelper.AUTH_CODE, authCode);
    }


}
