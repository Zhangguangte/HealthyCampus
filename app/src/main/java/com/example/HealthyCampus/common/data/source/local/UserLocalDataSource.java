package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.HomePageDataSource;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.vo.UserVo;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class UserLocalDataSource implements UserDataSource {
    private static UserLocalDataSource INSTANCE;

    public UserLocalDataSource(@NonNull Context context) {
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
}
