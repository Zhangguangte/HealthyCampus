package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.vo.MessageListVo;

import java.util.List;

import rx.Observable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MessageLocalDataSource implements MessageDataSource {
    private static MessageLocalDataSource INSTANCE;

    public MessageLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static MessageLocalDataSource getInstance(@NonNull Context context){
        if(INSTANCE==null)
        {
            INSTANCE= new MessageLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void lastMessage(@NonNull MessageSearchMessage callback) {

    }

    @Override
    public void allChatByRoomId(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback) {

    }

    @Override
    public void allChatByUid(@NonNull RequestForm requestForm, @NonNull MessageAllChat callback) {

    }

    @Override
    public void searchRoomid(@NonNull RequestForm requestForm, @NonNull MessageSearchRoomid callback) {

    }

    @Override
    public void insertContent(@NonNull ChatForm chatForm, @NonNull MessageAddContent callback) {

    }

    @Override
    public void insertCard(@NonNull ChatForm chatForm, @NonNull MessageAddCard callback) {

    }

    @Override
    public void upPicture(@NonNull String bitmapStr, @NonNull MessageUpPicture callback) {

    }

    @Override
    public void getAllNotice(@NonNull RequestForm requestForm, @NonNull MessageAllNotice callback) {

    }

    @Override
    public void clearNotice(@NonNull MessagClearNotice callback) {

    }

    @Override
    public void deleteNotice(@NonNull RequestForm requestForm, @NonNull MessageDeleteNotice callback) {

    }

    @Override
    public void lookNotice(@NonNull RequestForm requestForm, @NonNull MessageLookNotice callback) {

    }


}
