package com.example.HealthyCampus.module.Message.New_friend.Add_Friend;


import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.data.source.repository.UserRepository;
import com.example.HealthyCampus.common.network.vo.UserVo;

public class AddFriendPresenter extends AddFriendContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    protected void searchUser(String searchWords) {
        UserRepository.getInstance().searchUser(searchWords, new UserDataSource.UserInformation() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(UserVo userVo) {
                getView().initInsert(searchWords);
                getView().jumpToMsg(userVo);
            }
        });
    }
}
