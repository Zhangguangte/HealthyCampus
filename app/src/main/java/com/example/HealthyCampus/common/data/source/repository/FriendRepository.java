package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class FriendRepository implements FriendDataSource {

    private final FriendDataSource mFriendRemoteDataSource;
    private final FriendDataSource mFriendLocalDataSource;
    private static FriendRepository INSTANCE = null;

    public static FriendRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Friend repository must be init !");
        }
        return INSTANCE;
    }

    private FriendRepository(FriendDataSource friendRemoteDataSource, FriendDataSource friendLocalDataSource) {
        mFriendRemoteDataSource = checkNotNull(friendRemoteDataSource);
        mFriendLocalDataSource = checkNotNull(friendLocalDataSource);
    }

    public static void initialize(FriendDataSource friendRemoteDataSource, FriendDataSource friendLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new FriendRepository(friendRemoteDataSource, friendLocalDataSource);
    }


    @Override
    public void allFriend(@NonNull GetAllFriend callback) {
        mFriendRemoteDataSource.allFriend(callback);
    }

    @Override
    public void requestFriends(@NonNull GetRequestFriend callback) {
        mFriendRemoteDataSource.requestFriends(callback);
    }

    @Override
    public void clearRequestFriends(@NonNull ClearRequestFriends callback) {
        mFriendRemoteDataSource.clearRequestFriends(callback);
    }

    @Override
    public void sendRequestFriend(RequestForm requestForm, @NonNull SendRequestFriend callback) {
        mFriendRemoteDataSource.sendRequestFriend(requestForm,callback);
    }

    @Override
    public void saveRequestFriend(@NonNull RequestForm requestForm, @NonNull SaveRequestFriend callback) {
        mFriendRemoteDataSource.saveRequestFriend(requestForm,callback);
    }
    @Override
    public void refuseRequestFriend(@NonNull RequestForm requestForm, @NonNull RefuseRequestFriend callback) {
        mFriendRemoteDataSource.refuseRequestFriend(requestForm,callback);
    }


}
