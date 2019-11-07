package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class FriendLocalDataSource implements FriendDataSource {
    private static FriendLocalDataSource INSTANCE;

    public FriendLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static FriendLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new FriendLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void allFriend(@NonNull GetAllFriend callback) {

    }

    @Override
    public void requestFriends(@NonNull GetRequestFriend callback) {

    }

    @Override
    public void clearRequestFriends(@NonNull ClearRequestFriends callback) {

    }

    @Override
    public void sendRequestFriend(@NonNull RequestForm requestForm, @NonNull SendRequestFriend callback) {

    }

    @Override
    public void saveRequestFriend(@NonNull RequestForm requestForm, @NonNull SaveRequestFriend callback) {

    }

    @Override
    public void refuseRequestFriend(@NonNull RequestForm requestForm, @NonNull RefuseRequestFriend callback) {

    }
}
