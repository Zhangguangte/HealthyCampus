package com.example.HealthyCampus.module.Message.Address_list;


import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.repository.FriendRepository;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.utils.DialogUtil;

import java.util.ArrayList;

public class AddressListPresenter extends AddressListContract.Presenter {

    @Override
    public void onStart() {

    }

    @Override
    protected void getFriendsInformation() {
      DialogUtil.showProgressDialog(getView().getContext(),"正在加载中");
        FriendRepository.getInstance().allFriend(new FriendDataSource.GetAllFriend(){
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                getView().showViewByDataStatus(false);
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(ArrayList<AddressListVo> addressLists) {
                getView().showViewByDataStatus(addressLists.size()>0);
                getView().showFriends(addressLists);
                getView().listenItemStatus();
            }

            @Override
            public void finish() {
                getView().sidebarShow();
                DialogUtil.dismissProgressDialog();
            }
        });

    }

    @Override
    protected void listenTouch() {
        getView().listenTouchStatus();
    }

    @Override
    protected void listenItem() {
        getView().listenItemStatus();
    }
}
