package com.example.HealthyCampus.module.Message.Notice;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.repository.MessageRepository;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.utils.DateUtils;

import java.util.List;

public class NoticePresenter extends NoticeContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    protected void clearNotice() {
        MessageRepository.getInstance().clearNotice(new MessageDataSource.MessagClearNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() {
                if (null != getView()) getView().clearSuccess();
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
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<NoticeVo> noticeVos) {
                if (null != getView()) getView().showSuccess(noticeVos);
            }

            @Override
            public void finishList() {
                if (null != getView())
                    getView().loadData();
            }
        });
    }

    @Override
    protected void deleteNotice(String id) {
        RequestForm requestForm = new RequestForm(id);
        MessageRepository.getInstance().deleteNotice(requestForm, new MessageDataSource.MessageDeleteNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() {
                if (null != getView()) getView().showSuccess("删除成功");
            }
        });
    }

    @Override
    protected void lookNotice(String id) {
        RequestForm requestForm = new RequestForm(id, DateUtils.getStringDate());
        MessageRepository.getInstance().lookNotice(requestForm, new MessageDataSource.MessageLookNotice() {
            @Override
            public void onDataNotAvailable(Throwable throwable) {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable() {
            }
        });
    }

}
