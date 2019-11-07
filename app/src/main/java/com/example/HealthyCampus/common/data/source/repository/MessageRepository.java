package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
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
    public void lastMessage( @NonNull MessageSearchMessage callback) {
        mMessageRemoteDataSource.lastMessage(callback);
    }
}
