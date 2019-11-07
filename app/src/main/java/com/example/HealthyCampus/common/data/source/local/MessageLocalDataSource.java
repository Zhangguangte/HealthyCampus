package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.source.callback.MessageDataSource;
import com.example.HealthyCampus.common.data.source.callback.UserDataSource;
import com.example.HealthyCampus.common.network.vo.MessageListVo;

import java.util.List;

import rx.Observable;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MessageLocalDataSource implements MessageDataSource {
    private static MessageLocalDataSource INSTANCE;

    public MessageLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static MessageLocalDataSource getInstance(@NonNull Context context){
        if(INSTANCE==null)
        {
            INSTANCE= new MessageLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void lastMessage(@NonNull MessageSearchMessage callback) {

    }
}
