package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.model.LatestNewsBean;
import com.example.HealthyCampus.common.network.vo.MessageListVo;

import java.util.List;

import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageApi {

    @POST("/messages/lastMessage/")
    Observable<List<MessageListVo>> lastMessage();

}
