package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

public interface BaiduApi {

    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v1/classify/ingredient")
    Observable<IngredientVo> getIngredientResult(@Field("image") String image,@Field("top_num") String top_num, @Query("access_token") String token);

    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v2/dish")
    Observable<DishVo> getDishResult(@Field("image") String image, @Field("top_num") String top_num, @Query("access_token") String token);
}
