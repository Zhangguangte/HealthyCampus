package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;

import java.util.ArrayList;

import retrofit2.http.Body;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface FriendApi {

    @POST("/friends/allFriend/")
    Observable<ArrayList<AddressListVo>> allFriend();

    @POST("/friends/requestFriends/")
    Observable<ArrayList<RequestFriendVo>> requestFriends();


    @POST("/friends/clearRequestFriends/")
    Observable<DefaultResponseVo> clearRequestFriends();


    @POST("/friends/sendRequestFriend/")
    Observable<DefaultResponseVo> sendRequestFriend(@Body RequestForm requestForm);

    @POST("/friends/saveRequestFriend/")
    Observable<DefaultResponseVo> saveRequestFriend(@Body RequestForm requestForm);

    @POST("/friends/refuseRequestFriend/")
    Observable<DefaultResponseVo> refuseRequestFriend(@Body RequestForm requestForm);

}
