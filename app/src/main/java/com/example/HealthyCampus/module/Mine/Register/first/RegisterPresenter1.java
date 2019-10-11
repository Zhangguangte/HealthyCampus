package com.example.HealthyCampus.module.Mine.Register.first;

import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;

public class RegisterPresenter1 extends RegisterContract1.Presenter {


    @Override
    public void onStart() {

    }

    @Override
    public void listenRegisterEditText() {
        getView().listenRegisterEditTextStatus();
    }

    @Override
    public void foucusRegisterEditText() {
        getView().focusRegisterEditTextStatus();
    }

    @Override
    public void searchPhone(RegisterFrom registerFrom) {
        getView().showProgressView();
        UserRepository.getInstance().searchPhone(registerFrom, new UserDataSource.UserSearchPhone() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().dismissProgressView();
                getView().phoneExist();
            }

            @Override
            public void onDataAvailable(String username) {
                getView().dismissProgressView();
                getView().phoneNotExist();
            }
        });
    }
}
