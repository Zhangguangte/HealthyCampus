package com.example.HealthyCampus.module.Message.New_friend.Add_Friend;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface AddFriendContract {
    interface View extends BaseView {
        Context getContext();

        void initQuery();       //初始化历史搜索查询

        void initSearchDB(); /*数据库*/

        void initRecyclerView(); /*搜索历史*/

        void initEditKey(); /*搜索文本监听*/

        void showError(Throwable throwable);

        void jumpToMsg(UserVo userVo);       //跳到添加好友消息界面

        void initInsert(String name);       //添加搜索历史
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void searchUser(String searchWords);
    }
}
