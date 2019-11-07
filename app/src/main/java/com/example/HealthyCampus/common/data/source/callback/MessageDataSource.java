package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.network.vo.MessageListVo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageDataSource {

    interface MessageSearchMessage {

        void onDataNotAvailable(Throwable throwable);

        void onDataAvailable(List<MessageListVo> messageListVos);

    }


    void lastMessage(@NonNull MessageSearchMessage callback);

}
