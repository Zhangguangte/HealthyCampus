package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class UserLocalDataSource implements UserDataSource {
    private static UserLocalDataSource INSTANCE;

    private UserLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static UserLocalDataSource getInstance(@NonNull Context context){
        if(INSTANCE==null)
        {
            INSTANCE= new UserLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void login(@NonNull LoginForm dataForm, @NonNull UserLogin callback) {

    }

    @Override
    public void register(@NonNull RegisterFrom registerFrom, @NonNull UserRegister callback) {

    }

    @Override
    public void searchPhone(@NonNull RegisterFrom registerFrom, @NonNull UserSearchPhone callback) {

    }

    @Override
    public void getUserInformation(@NonNull String userid, @NonNull UserInformation callback) {

    }

    @Override
    public void searchUser(@NonNull String searchWords, @NonNull UserInformation callback) {

    }

    @Override
    public void searchUser(@NonNull RequestForm requestForm, @NonNull UserInformation callback) {

    }
}
