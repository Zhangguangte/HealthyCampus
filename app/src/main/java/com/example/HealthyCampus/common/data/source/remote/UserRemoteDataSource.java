package com.example.HealthyCampus.common.data.source.remote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.model.HomePageArticleBean;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class UserRemoteDataSource implements UserDataSource {

    private static UserRemoteDataSource INSTANCE = null;


    public UserRemoteDataSource() {
    }

    public static UserRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void login(@NonNull LoginForm dataForm, @NonNull final UserLogin callback) {
        NetworkManager.getUserApi()
                .login(dataForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
//                        Log.e("UserRemoteDa" + "123456", "7:");
                    }
                })
                .subscribe(new Action1<UserVo>() {
                    @Override
                    public void call(UserVo userVo) {
//                        LogUtil.logE("UserRemoteDa" + "123456", "9:" + userVo);
//                        LogUtil.logE("UserRemoteDa" + "123456", "Login Success");
                        callback.loginSuccess();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void register(@NonNull RegisterFrom registerFrom, @NonNull UserRegister callback) {
        NetworkManager.getUserApi()
                .register(registerFrom)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("UserRemoteDa" + "123456", "register:8");
                        Log.e("UserRemoteDa" + "123456", "register:defaultResponseVo"+defaultResponseVo.toString());
                        callback.registerSuccess(defaultResponseVo.message);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("UserRemoteDa" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void searchPhone(@NonNull RegisterFrom registerFrom, @NonNull UserSearchPhone callback) {
        NetworkManager.getUserApi()
                .searchPhone(registerFrom)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("UserRemoteDa" + "123456", "register:8");
                        Log.e("UserRemoteDa" + "123456", "register:defaultResponseVo"+defaultResponseVo.toString());
                        callback.onDataAvailable(defaultResponseVo.message);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("UserRemoteDa" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }
}
