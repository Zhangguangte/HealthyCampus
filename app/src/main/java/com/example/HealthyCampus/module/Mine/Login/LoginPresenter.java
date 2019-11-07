package com.example.HealthyCampus.module.Mine.Login;

import android.util.Base64;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.StringUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.greendao.model.User;
import com.example.HealthyCampus.service.UserService;

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    public void login(LoginForm dataForm, String password) {
        UserRepository.getInstance().login(dataForm, new UserDataSource.UserLogin() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().dismissProgressView();
                getView().showLoginError(throwable);
            }

            @Override
            public void loginSuccess(UserVo user) {
                getView().dismissProgressView();
                initUserInformation(user, password);
                ToastUtil.show(getView().getContext(), R.string.user_login_success);
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
    public void initUserInformation(UserVo userVo, String password) {
        UserService.registerJPush(getView().getContext(), userVo.id);
        UserService.persistenceUser(userVo, password);
    }

}
