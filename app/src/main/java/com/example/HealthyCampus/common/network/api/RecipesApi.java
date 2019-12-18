package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;

import java.util.LinkedList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface RecipesApi {

    @POST("/recipes/getRecipesByThreeMeals/")
    Observable<LinkedList<FoodMenuVo>> getRecipesByThreeMeals();

    @POST("/recipes/getRecipeDetail/")
    Observable<FoodVo> getRecipeDetail(@Body RequestForm requestForm);

    @POST("/recipes/getRecommendRecipes/")
    Observable<List<FoodRecommendVo>> getRecommendRecipes();


}
