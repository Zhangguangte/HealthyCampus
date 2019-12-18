package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.ChatForm;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.model.LatestNewsBean;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.network.vo.NoticeVo;
import com.example.HealthyCampus.common.network.vo.UserVo;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import rx.Observable;

public interface MessageApi {

    @POST("/messages/lastMessage/")
    Observable<List<MessageListVo>> lastMessage();

    @POST("/messages/allChatByRoomId/")
    Observable<List<MessageListVo>> allChatByRoomId(@Body RequestForm requestForm);

    @POST("/messages/searchRoomid/")
    Observable<MessageListVo> searchRoomid(@Body RequestForm requestForm);

    @POST("/messages/allChatByUid/")
    Observable<List<MessageListVo>> allChatByUid(@Body RequestForm requestForm);

    @POST("/messages/insertContent/")
    Observable<DefaultResponseVo> insertContent(@Body ChatForm chatForm);

    @POST("/messages/insertCard/")
    Observable<UserVo> insertCard(@Body ChatForm chatForm);

    @POST("/messages/getAllNotice/")
    Observable<List<NoticeVo>> getAllNotice(@Body RequestForm requestForm);


    @POST("/messages/clearNotice/")
    Observable<DefaultResponseVo> clearNotice();


    @POST("/messages/deleteNotice/")
    Observable<DefaultResponseVo> deleteNotice(@Body RequestForm requestForm);

    @POST("/messages/lookNotice/")
    Observable<DefaultResponseVo> lookNotice(@Body RequestForm requestForm);

//    @Headers("Content-Type:application/json;charset=UTF-8")
//    @Multipart
//    @POST("/messages/upPicture/")
//    Observable<DefaultResponseVo> upPicture(@Part List<MultipartBody.Part> partList);

//    @Multipart
//    @POST("/messages/upPicture/")
//    Observable<DefaultResponseVo> upPicture(@Part("upload_file\";filename=\"1.jpg") RequestBody file);


}
