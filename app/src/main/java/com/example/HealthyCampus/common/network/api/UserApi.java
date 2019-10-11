package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.LoginForm;
import com.example.HealthyCampus.common.data.form.RegisterFrom;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface UserApi {

    /**
     * 账号登录
     *
     * @param dataForm 用户信息
     * @return
     */

    @POST("/users/login")
    Observable<UserVo> login(@Body LoginForm dataForm);

    /**
     * 账号注册
     *
     * @param registerFrom 用户信息
     * @return
     */

    @POST("/users/register")
    Observable<DefaultResponseVo> register(@Body RegisterFrom registerFrom);

    /**
     * 账号注册
     *
     * @param registerFrom 电话号码
     * @return
     */

    @POST("/users/register/searchPhone")
    Observable<DefaultResponseVo> searchPhone(@Body RegisterFrom registerFrom);

}
