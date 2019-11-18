package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.greendao.model.User;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageDataSource {

    interface MessageSearchMessage {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(List<MessageListVo> messageListVos);

    }

    void lastMessage(@NonNull MessageSearchMessage callback);

    interface MessageAllChat {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(List<MessageListVo> messageListVos);

    }

    void allChatByRoomId(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback);

    void allChatByUid(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback);

    interface MessageAddContent {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable();

    }

    void insertContent(@NonNull ChatForm chatForm, @NonNull MessageAddContent callback);

    interface MessageAddCard {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(UserVo userVo);

    }

    void insertCard(@NonNull ChatForm chatForm, @NonNull MessageAddCard callback);

    void searchRoomid(@NonNull RequestForm requestForm, @NonNull MessageSearchRoomid callback);

    interface MessageSearchRoomid {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(MessageListVo messageListVo);

    }


    interface MessageUpPicture {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable();

    }

    void upPicture(@NonNull String bitmapStr, @NonNull MessageUpPicture callback);


}
