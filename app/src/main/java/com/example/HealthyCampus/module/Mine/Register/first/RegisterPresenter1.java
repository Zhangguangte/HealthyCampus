package com.example.HealthyCampus.module.Mine.Register.first;

import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;

public class RegisterPresenter1 extends RegisterContract1.Presenter {


    @Override
    public void onStart() {

    }

    @Override
    protected void listenRegisterEditText() throws Exception {
        getView().listenRegisterEditTextStatus();
    }

    @Override
    protected void foucusRegisterEditText() throws Exception {
        getView().focusRegisterEditTextStatus();
    }

    @Override
    protected void searchPhone(RegisterFrom registerFrom) throws Exception {
        getView().showProgressView();
        UserRepository.getInstance().searchPhone(registerFrom, new UserDataSource.UserSearchPhone() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) {
                    getView().dismissProgressView();
                    getView().showError(throwable);
                }
            }

            @Override
            public void onDataAvailable(String username) throws Exception {
                if (null != getView()) {
                    getView().dismissProgressView();
                    getView().phoneNotExist();
                }
            }
        });
    }
}
