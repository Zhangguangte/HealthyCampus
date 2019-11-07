package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.UserVo;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class MessageRemoteDataSource implements MessageDataSource {

    private static MessageRemoteDataSource INSTANCE = null;


    public MessageRemoteDataSource() {
    }

    public static MessageRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MessageRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void lastMessage(@NonNull MessageSearchMessage callback) {


        NetworkManager.getInstance().getMessageApi()
                .lastMessage()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("UserRemoteDa" + "123456", "register:7");
                    }
                })
                .subscribe(new Action1<List<MessageListVo>>() {
                    @Override
                    public void call(List<MessageListVo> messageListVos) {
                        Log.e("ChatListPresenter" + "123456", "allchat success");
                        for (MessageListVo messageListVo : messageListVos) {
                            Log.e("ChatListPresenter" + "123456", "messageListVo.toString" + messageListVo.toString());
                        }
                        Log.e("ChatListPresenter" + "123456", "messageListVo.toString" + messageListVos.size());
                        callback.onDataAvailable(messageListVos);
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
