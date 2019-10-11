package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.vo.UserVo;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class UserRepository implements UserDataSource {

    private final UserDataSource mUserRemoteDataSource;
    private final UserDataSource mUserLocalDataSource;
    private static UserRepository INSTANCE = null;

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the repository must be init !");
        }
        return INSTANCE;
    }

    private UserRepository(UserDataSource userRemoteDataSource, UserDataSource userLocalDataSource) {
        mUserRemoteDataSource = checkNotNull(userRemoteDataSource);
        mUserLocalDataSource = checkNotNull(userLocalDataSource);
    }

    public static void initialize(UserDataSource userRemoteDataSource, UserDataSource userLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new UserRepository(userRemoteDataSource, userLocalDataSource);
    }


    @Override
    public void login(@NonNull LoginForm dataForm, @NonNull UserLogin callback) {
        mUserRemoteDataSource.login(dataForm,callback);
    }

    @Override
    public void register(@NonNull RegisterFrom registerFrom, @NonNull UserRegister callback) {
        mUserRemoteDataSource.register(registerFrom,callback);
    }

    @Override
    public void searchPhone(@NonNull RegisterFrom registerFrom, @NonNull UserSearchPhone callback) {
        mUserRemoteDataSource.searchPhone(registerFrom,callback);
    }


}
