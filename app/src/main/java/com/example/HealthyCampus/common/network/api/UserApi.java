package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;

import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import rx.Observable;

public interface UserApi {

    @POST("/users/login")
    Observable<UserVo> login(@Body LoginForm dataForm);

    @POST("/users/register")
    Observable<UserVo> register(@Body RegisterFrom registerFrom);

    @POST("/users/register/searchPhone")
    Observable<DefaultResponseVo> searchPhone(@Body RegisterFrom registerFrom);

    @GET("/users/information/{account}")
    Observable<UserVo> getUserInformation(@Path("account") String account);

    @POST("/users/searchUser/{searchWords}")
    Observable<UserVo> searchUser(@Path("searchWords") String searchWords);

    @POST("/users/searchUser")
    Observable<UserVo> searchUser(@Body RequestForm requestForm);

}
