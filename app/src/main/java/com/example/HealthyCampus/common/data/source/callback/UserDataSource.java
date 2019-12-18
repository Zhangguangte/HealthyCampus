package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.UserVo;

public interface UserDataSource {


    interface UserLogin {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void loginSuccess(UserVo user) throws Exception;

    }

    interface UserRegister {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void registerSuccess(String username) throws Exception;

    }

    interface UserSearchPhone {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(String username) throws Exception;

    }

    interface UserInformation {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(UserVo userVo) throws Exception;

    }


    void login(@NonNull LoginForm dataForm, @NonNull UserLogin callback);

    void register(@NonNull RegisterFrom registerFrom, @NonNull UserRegister callback);

    void searchPhone(@NonNull RegisterFrom registerFrom, @NonNull UserSearchPhone callback);

    void getUserInformation(@NonNull String userid, @NonNull UserInformation callback);

    void searchUser(@NonNull String searchWords, @NonNull UserInformation callback);

    void searchUser(@NonNull RequestForm requestForm, @NonNull UserInformation callback);

}
