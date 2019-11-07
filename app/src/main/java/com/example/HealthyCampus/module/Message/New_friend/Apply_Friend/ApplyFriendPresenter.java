package com.example.HealthyCampus.module.Message.New_friend.Apply_Friend;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.FriendRepository;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.network.vo.UserVo;

import java.util.ArrayList;

public class ApplyFriendPresenter extends ApplyFriendContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    public void saveRequestFriend(RequestForm requestForm) {
        FriendRepository.getInstance().saveRequestFriend(requestForm,new FriendDataSource.SaveRequestFriend() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) {
                getView().showSuccess(defaultResponseVo,0);
            }
        });
    }

    @Override
    public void refuseRequestFriend(RequestForm requestForm) {
        FriendRepository.getInstance().refuseRequestFriend(requestForm,new FriendDataSource.RefuseRequestFriend() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) {
                getView().showSuccess(defaultResponseVo,1);
            }
        });
    }

    @Override
    protected void searchUser(RequestForm requestForm) {
        UserRepository.getInstance().searchUser(requestForm, new UserDataSource.UserInformation() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(UserVo userVo) {
                getView().jumpToUserInformation(userVo);
            }

        });
    }

}
