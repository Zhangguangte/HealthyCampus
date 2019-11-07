package com.example.HealthyCampus.module.Message.Address_list;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;

interface AddressListContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showFriends(ArrayList<AddressListVo> addressLists);

        void listenTouchStatus();        //触碰监听

        void listenItemStatus();        //单项监听

        void sidebarShow();        //字母排序显示

        void showViewByDataStatus(boolean value);        //空视图显示根据是否有数据
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getFriendsInformation();    //获得好友头像和昵称信息

        protected abstract void listenTouch();       //触碰监听

        protected abstract void  listenItem();        //单项监听
    }
}
