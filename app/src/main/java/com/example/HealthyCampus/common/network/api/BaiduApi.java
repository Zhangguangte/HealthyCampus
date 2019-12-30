package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

public interface BaiduApi {

    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v1/classify/ingredient?access_token=24.36b6b51a8e091934131c9a27af5afa26.2592000.1579269909.282335-14346167")
    Observable<IngredientVo> getIngredientResult(@Field("image") String image,@Field("top_num") String top_num);

    @FormUrlEncoded
    @POST("/rest/2.0/image-classify/v2/dish?access_token=24.14d8dbd1585611b32ee339190001e082.2592000.1579258292.282335-18052930")
    Observable<DishVo> getDishResult(@Field("image") String image,@Field("top_num") String top_num);
}
