package com.example.HealthyCampus.module.Mine.User;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.network.vo.UserVo;

public class UserInformationPresenter extends UserInformationContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    protected void getUserInformation(String account) {
        UserRepository.getInstance().getUserInformation(account, new UserDataSource.UserInformation() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(UserVo userVo) throws Exception {
                if (null != getView()) {
                    userVo.isfriends = true;
                    getView().initUserInfo(userVo);
                }
            }

        });
    }

    @Override
    protected void searchUser(RequestForm requestForm) {
        UserRepository.getInstance().searchUser(requestForm, new UserDataSource.UserInformation() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(UserVo userVo) throws Exception {
                if (null != getView()) {
                    userVo.isfriends = false;
                    getView().initUserInfo(userVo);
                }
            }

        });
    }


}
