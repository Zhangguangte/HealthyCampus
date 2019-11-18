package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MessageRepository implements MessageDataSource {

    private final MessageDataSource mMessageRemoteDataSource;
    private final MessageDataSource mMessageLocalDataSource;
    private static MessageRepository INSTANCE = null;

    public static MessageRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Message repository must be init !");
        }
        return INSTANCE;
    }

    private MessageRepository(MessageDataSource messageRemoteDataSource, MessageDataSource messageLocalDataSource) {
        mMessageRemoteDataSource = checkNotNull(messageRemoteDataSource);
        mMessageLocalDataSource = checkNotNull(messageLocalDataSource);
    }

    public static void initialize(MessageDataSource messageRemoteDataSource, MessageDataSource messageLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new MessageRepository(messageRemoteDataSource, messageLocalDataSource);
    }


    @Override
    public void lastMessage(@NonNull MessageSearchMessage callback) {
        mMessageRemoteDataSource.lastMessage(callback);
    }

    @Override
    public void allChatByRoomId(RequestForm requestForm, @NonNull MessageAllChat callback) {
        mMessageRemoteDataSource.allChatByRoomId(requestForm, callback);
    }

    @Override
    public void allChatByUid(RequestForm requestForm, @NonNull MessageAllChat callback) {
        mMessageRemoteDataSource.allChatByUid(requestForm, callback);
    }
    @Override
    public void searchRoomid(RequestForm requestForm, @NonNull MessageSearchRoomid callback) {
        mMessageRemoteDataSource.searchRoomid(requestForm, callback);
    }

    @Override
    public void insertContent(ChatForm chatForm, @NonNull MessageAddContent callback) {
        mMessageRemoteDataSource.insertContent(chatForm, callback);
    }
    @Override
    public void insertCard(ChatForm chatForm, @NonNull MessageAddCard callback) {
        mMessageRemoteDataSource.insertCard(chatForm, callback);
    }

    @Override
    public void upPicture(String bitmapStr, @NonNull MessageUpPicture callback) {
        mMessageRemoteDataSource.upPicture(bitmapStr, callback);
    }
}
