package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.network.vo.UserVo;

public interface UserDataSource {


    interface UserLogin {
        void onDataNotAvailable(Throwable throwable);

        void loginSuccess();

    }

    interface UserRegister {

        void onDataNotAvailable(Throwable throwable);

        void registerSuccess(String username);

    }

    interface UserSearchPhone {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(String username);

    }


    void login(@NonNull LoginForm dataForm, @NonNull UserLogin callback);

    void register(@NonNull RegisterFrom registerFrom, @NonNull UserRegister callback);

    void searchPhone(@NonNull RegisterFrom registerFrom, @NonNull UserSearchPhone callback);

}
