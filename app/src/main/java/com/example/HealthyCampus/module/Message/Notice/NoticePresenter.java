package com.example.HealthyCampus.module.Message.Notice;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.repository.FriendRepository;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.LogUtil;

import java.util.ArrayList;
import java.util.List;

public class NoticePresenter extends NoticeContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    protected void clearNotice() {
        MessageRepository.getInstance().clearNotice(new MessageDataSource.MessagClearNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() throws Exception {
                getView().clearSuccess();
            }
        });
    }

    @Override
    protected void getAllNotice() {
        String date = SPHelper.getString("NOTICE_DATE");
        if (date == null || "".equals(date))
            date = "0000-00-00 00:00:00";
        RequestForm requestForm = new RequestForm(date);
        MessageRepository.getInstance().getAllNotice(requestForm, new MessageDataSource.MessageAllNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<NoticeVo> noticeVos) throws Exception {
                getView().showSuccess(noticeVos);
            }
        });
    }

    @Override
    protected void deleteNotice(String id) {
        RequestForm requestForm = new RequestForm(id);
        MessageRepository.getInstance().deleteNotice(requestForm, new MessageDataSource.MessageDeleteNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() throws Exception {
                getView().showSuccess("删除成功");
            }
        });
    }

    @Override
    protected void lookNotice(String id) {
        RequestForm requestForm = new RequestForm(id,DateUtils.getStringDate());
        MessageRepository.getInstance().lookNotice(requestForm, new MessageDataSource.MessageLookNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() throws Exception {
            }
        });
    }

}
