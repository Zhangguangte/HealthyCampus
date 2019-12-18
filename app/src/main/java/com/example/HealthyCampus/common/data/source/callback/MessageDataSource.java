package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.greendao.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageDataSource {

    interface MessageSearchMessage {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<MessageListVo> messageListVos) throws Exception;

    }

    void lastMessage(@NonNull MessageSearchMessage callback);   //消息界面最后一条消息

    interface MessageAllChat {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<MessageListVo> messageListVos) throws Exception;

    }

    void allChatByRoomId(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback);   //查询所有消息根据房间号

    void allChatByUid(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback);

    interface MessageAddContent {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable();

    }

    void insertContent(@NonNull ChatForm chatForm, @NonNull MessageAddContent callback);

    interface MessageAddCard {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(UserVo userVo) throws Exception;

    }

    void insertCard(@NonNull ChatForm chatForm, @NonNull MessageAddCard callback);

    void searchRoomid(@NonNull RequestForm requestForm, @NonNull MessageSearchRoomid callback);

    interface MessageSearchRoomid {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(MessageListVo messageListVo);

    }


    interface MessageUpPicture {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable();

    }

    void upPicture(@NonNull String bitmapStr, @NonNull MessageUpPicture callback);

    interface MessageAllNotice {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<NoticeVo> noticeVos) throws Exception;

    }

    interface MessagClearNotice {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable() throws Exception;

    }

    interface MessageDeleteNotice {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable() throws Exception;

    }

    interface MessageLookNotice {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable() throws Exception;

    }


    void getAllNotice(@NonNull RequestForm requestForm,@NonNull MessageAllNotice callback);      //获得所有通知

    void clearNotice(@NonNull MessagClearNotice callback);       //清空通知

    void deleteNotice(@NonNull RequestForm requestForm, @NonNull MessageDeleteNotice callback);       //删除通知

    void lookNotice(@NonNull RequestForm requestForm, @NonNull MessageLookNotice callback);       //查看通知
}
