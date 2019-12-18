package com.example.HealthyCampus.module.Mine.Register.second;

import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;

public class RegisterPresenter2 extends RegisterContract2.Presenter {


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
    protected void register(RegisterFrom registerFrom) throws Exception {
        getView().showProgressView();
        UserRepository.getInstance().register(registerFrom, new UserDataSource.UserRegister() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().dismissProgressView();
                getView().showRegisterError(throwable);
            }
            @Override
            public void registerSuccess(String username) throws Exception {
//                getView().setPageEnable();
                getView().dismissProgressView();
                getView().showTipsView(username);
            }
        });
    }

    @Override
    protected RegisterFrom encapsulationData(String phone, String _password) {
        RegisterFrom registerFrom = new RegisterFrom();
        registerFrom.setPassword(_password);
        registerFrom.setPhone(phone);
        return registerFrom;
    }
}
