package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;

import java.util.ArrayList;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class FriendRemoteDataSource implements FriendDataSource {

    private static FriendRemoteDataSource INSTANCE = null;


    public FriendRemoteDataSource() {
    }

    public static FriendRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FriendRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void allFriend(@NonNull GetAllFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .allFriend()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("allFriend" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<ArrayList<AddressListVo>>() {
                    @Override
                    public void call(ArrayList<AddressListVo> addressLists) {
                        Log.e("allFriend" + "123456", "allFriend success");
                        callback.onDataAvailable(addressLists);
                        callback.finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("allFriend" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                        callback.finish();
                    }
                });
    }

    @Override
    public void requestFriends(@NonNull GetRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .requestFriends()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("requestFriends" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<ArrayList<RequestFriendVo>>() {
                    @Override
                    public void call(ArrayList<RequestFriendVo> requestFriendVos) {
                        Log.e("requestFriends" + "123456", "requestFriends success");
                        callback.onDataAvailable(requestFriendVos);
                        callback.finish();
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("requestFriends" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                        callback.finish();
                    }
                });
    }

    @Override
    public void clearRequestFriends(@NonNull ClearRequestFriends callback) {
        NetworkManager.getInstance().getFriendApi()
                .clearRequestFriends()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("clearRequestFri" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("clearRequestFri" + "123456", "requestFriends success");
                        callback.onDataAvailable(defaultResponseVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("clearRequestFri" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }


    @Override
    public void sendRequestFriend(@NonNull RequestForm requestForm, @NonNull SendRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .sendRequestFriend(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("FriendRemoteDat" + "123456", "sendRequestFriend:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("FriendRemoteDat" + "123456", "sendRequestFriend success");
                        callback.onDataAvailable(defaultResponseVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void saveRequestFriend(@NonNull RequestForm requestForm, @NonNull SaveRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .saveRequestFriend(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("FriendRemoteDat" + "123456", "saveRequestFriend:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("FriendRemoteDat" + "123456", "saveRequestFriend success");
                        callback.onDataAvailable(defaultResponseVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("FriendRemoteDat" + "123456", "saveRequestFriend error");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }

    @Override
    public void refuseRequestFriend(@NonNull RequestForm requestForm, @NonNull RefuseRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .refuseRequestFriend(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("FriendRemoteDat" + "123456", "refuseRequestFriend:7");
                    }
                })
                .subscribe(new Action1<DefaultResponseVo>() {
                    @Override
                    public void call(DefaultResponseVo defaultResponseVo) {
                        Log.e("FriendRemoteDat" + "123456", "refuseRequestFriend success");
                        callback.onDataAvailable(defaultResponseVo);
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        Log.e("FriendRemoteDat" + "123456", "refuseRequestFriend error");
                        callback.onDataNotAvailable(throwable);
                    }
                });
    }
}
