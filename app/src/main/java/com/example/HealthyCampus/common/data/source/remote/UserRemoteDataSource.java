package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.LogUtil;

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

        NetworkManager.getInstance().getUserApi()
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
                        callback.loginSuccess(userVo);
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
        NetworkManager.getInstance().getUserApi()
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
        NetworkManager.getInstance().getUserApi()
                .searchPhone(registerFrom)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "searchPhone:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("UserRemoteDa" + "123456", "searchPhone:8");
                        Log.e("UserRemoteDa" + "123456", "searchPhone:defaultResponseVo"+defaultResponseVo.toString());
                        callback.onDataAvailable(defaultResponseVo.message);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("UserRemoteDa" + "123456", "searchPhone:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void getUserInformation(@NonNull String account, @NonNull UserInformation callback) {
        NetworkManager.getInstance().getUserApi()
                .getUserInformation(account)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "getUserInformation:7");
                    }
                })
                .subscribe(new Action1<UserVo>() {
                    @Override
                    public void call(UserVo userVo) {
                        Log.e("UserRemoteDa" + "123456", "getUserInformation:8");
                        callback.onDataAvailable(userVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("UserRemoteDa" + "123456", "getUserInformation:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void searchUser(@NonNull String searchWords, @NonNull UserInformation callback) {
        NetworkManager.getInstance().getUserApi()
                .searchUser(searchWords)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "searchUser:7");
                    }
                })
                .subscribe(new Action1<UserVo>() {
                    @Override
                    public void call(UserVo userVo) {
                        Log.e("UserRemoteDa" + "123456", "searchUser:8");
                        callback.onDataAvailable(userVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("UserRemoteDa" + "123456", "searchUser:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void searchUser(@NonNull RequestForm requestForm, @NonNull UserInformation callback) {
        NetworkManager.getInstance().getUserApi()
                .searchUser(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "searchUser:7");
                    }
                })
                .subscribe(new Action1<UserVo>() {
                    @Override
                    public void call(UserVo userVo) {
                        Log.e("UserRemoteDa" + "123456", "searchUser:8");
                        callback.onDataAvailable(userVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("UserRemoteDa" + "123456", "searchUser:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }
}
