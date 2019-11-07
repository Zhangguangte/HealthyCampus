package com.example.HealthyCampus.module.Message.New_friend.List;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.repository.FriendRepository;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;

import java.util.ArrayList;

public class NewFriendListPresenter extends NewFriendListContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    public void clearRequestFriends() {
        FriendRepository.getInstance().clearRequestFriends(new FriendDataSource.ClearRequestFriends(){
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) {
                  getView().clearList();
            }
        });
    }

    @Override
    public void saveRequestFriend(RequestForm requestForm,int position) {
        FriendRepository.getInstance().saveRequestFriend(requestForm,new FriendDataSource.SaveRequestFriend() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) {
                getView().showSuccess(defaultResponseVo,position);
            }
        });
    }

    @Override
    public void requestFriends() {
        FriendRepository.getInstance().requestFriends(new FriendDataSource.GetRequestFriend() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(ArrayList<RequestFriendVo> requestFriendVos) {
                getView().showRequestFriends(requestFriendVos);
            }

            @Override
            public void finish() {
            }
        });
    }


}