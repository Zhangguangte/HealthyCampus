package com.example.HealthyCampus.module.Message.Notice;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.ArrayList;
import java.util.List;

interface NoticeContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(List<NoticeVo> noticeVos);

        void showSuccess(String message);
        void clearSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void clearNotice();      //清空通知

        protected abstract void getAllNotice();     //获得所有通知

        protected abstract void deleteNotice(String id);     //删除通知
        protected abstract void lookNotice(String id);     //查看通知
    }
}
