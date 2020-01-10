package com.example.HealthyCampus.module.Mine.Login;

import android.text.TextUtils;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.helper.UserHelper;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.StringUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;

public class LoginPresenter extends LoginContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    public void login(LoginForm dataForm, String password) {
        UserRepository.getInstance().login(dataForm, new UserDataSource.UserLogin() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) {
                    getView().dismissProgressView();
                    getView().showLoginError(throwable);
                }
            }

            @Override
            public void loginSuccess(UserVo user) throws Exception {
                if (null != getView()) {
                    if (!TextUtils.isEmpty(user.getId())) {
                        getView().dismissProgressView();
                        initUserInformation(user, password);
                        getView().jumpToMain();
                    } else {
                        ToastUtil.show(getView().getContext(), "非法登录");
                    }
                }
            }
        });

    }

    @Override
    public void listenLoginEditText() throws Exception {
        getView().listenLoginEditTextStatus();
    }

    @Override
    public LoginForm encapsulationData(String username, String _password) {
//        String deviceId = SPHelper.getString(SPHelper.REGISTER_ID);
        final String password = StringUtil.toMD5(_password);
        return new LoginForm("", username, password);
    }

    @Override
    public void foucusLoginEditText() throws Exception {
        getView().focusLoginEditTextStatus();
    }


    @Override
    public void initUserInformation(UserVo userVo, String password) throws Exception {
        UserHelper.persistenceUser(userVo, password);
    }

}
