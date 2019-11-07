package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class FriendRepository implements FriendDataSource {

    private final FriendDataSource mMessageRemoteDataSource;
    private final FriendDataSource mMessageLocalDataSource;
    private static FriendRepository INSTANCE = null;

    public static FriendRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Friend repository must be init !");
        }
        return INSTANCE;
    }

    private FriendRepository(FriendDataSource friendRemoteDataSource, FriendDataSource friendLocalDataSource) {
        mMessageRemoteDataSource = checkNotNull(friendRemoteDataSource);
        mMessageLocalDataSource = checkNotNull(friendLocalDataSource);
    }

    public static void initialize(FriendDataSource friendRemoteDataSource, FriendDataSource friendLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new FriendRepository(friendRemoteDataSource, friendLocalDataSource);
    }


    @Override
    public void allFriend(@NonNull GetAllFriend callback) {
        mMessageRemoteDataSource.allFriend(callback);
    }

    @Override
    public void requestFriends(@NonNull GetRequestFriend callback) {
        mMessageRemoteDataSource.requestFriends(callback);
    }

    @Override
    public void clearRequestFriends(@NonNull ClearRequestFriends callback) {
        mMessageRemoteDataSource.clearRequestFriends(callback);
    }

    @Override
    public void sendRequestFriend(RequestForm requestForm, @NonNull SendRequestFriend callback) {
        mMessageRemoteDataSource.sendRequestFriend(requestForm,callback);
    }

    @Override
    public void saveRequestFriend(@NonNull RequestForm requestForm, @NonNull SaveRequestFriend callback) {
        mMessageRemoteDataSource.saveRequestFriend(requestForm,callback);
    }
    @Override
    public void refuseRequestFriend(@NonNull RequestForm requestForm, @NonNull RefuseRequestFriend callback) {
        mMessageRemoteDataSource.refuseRequestFriend(requestForm,callback);
    }


}
