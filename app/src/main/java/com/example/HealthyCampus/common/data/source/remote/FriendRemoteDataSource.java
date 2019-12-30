package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;

import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class FriendRemoteDataSource implements FriendDataSource {

    private static FriendRemoteDataSource INSTANCE = null;


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
                .doOnSubscribe(() -> Log.e("allFriend" + "123456", "register:7"))
                .subscribe(addressLists -> {
                    Log.e("allFriend" + "123456", "allFriend success");
                    try {
                        callback.onDataAvailable(addressLists);
                        callback.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("allFriend" + "123456", "register:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                        callback.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void requestFriends(@NonNull GetRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .requestFriends()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("requestFriends" + "123456", "register:7"))
                .subscribe(requestFriendVos -> {
                    Log.e("requestFriends" + "123456", "requestFriends success");
                    try {
                        callback.onDataAvailable(requestFriendVos);
                        callback.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    Log.e("requestFriends" + "123456", "register:9");
                    try {
                        callback.onDataNotAvailable(throwable);
                        callback.finish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void clearRequestFriends(@NonNull ClearRequestFriends callback) {
        NetworkManager.getInstance().getFriendApi()
                .clearRequestFriends()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("clearRequestFri" + "123456", "register:7"))
                .subscribe(defaultResponseVo -> {
                    try {
                        Log.e("clearRequestFri" + "123456", "requestFriends success");
                        callback.onDataAvailable(defaultResponseVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("clearRequestFri" + "123456", "register:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    @Override
    public void sendRequestFriend(@NonNull RequestForm requestForm, @NonNull SendRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .sendRequestFriend(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("FriendRemoteDat" + "123456", "sendRequestFriend:7"))
                .subscribe(defaultResponseVo -> {
                    Log.e("FriendRemoteDat" + "123456", "sendRequestFriend success");
                    callback.onDataAvailable(defaultResponseVo);
                }, throwable -> {
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void saveRequestFriend(@NonNull RequestForm requestForm, @NonNull SaveRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .saveRequestFriend(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("FriendRemoteDat" + "123456", "saveRequestFriend:7"))
                .subscribe(defaultResponseVo -> {
                    try {
                        Log.e("FriendRemoteDat" + "123456", "saveRequestFriend success");
                        callback.onDataAvailable(defaultResponseVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void refuseRequestFriend(@NonNull RequestForm requestForm, @NonNull RefuseRequestFriend callback) {
        NetworkManager.getInstance().getFriendApi()
                .refuseRequestFriend(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("FriendRemoteDat" + "123456", "refuseRequestFriend:7"))
                .subscribe(defaultResponseVo -> {
                    try {
                        Log.e("FriendRemoteDat" + "123456", "refuseRequestFriend success");
                        callback.onDataAvailable(defaultResponseVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("FriendRemoteDat" + "123456", "refuseRequestFriend error");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }
}
