package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;
import com.example.HealthyCampus.common.network.vo.IngredientResultVo;
import com.example.HealthyCampus.common.network.vo.RecipesListVo;

import java.util.LinkedList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface RecipesApi {

    @POST("/recipes/getRecipesByThreeMeals/")
    Observable<LinkedList<FoodMenuVo>> getRecipesByThreeMeals(@Body RequestForm requestForm);

    @POST("/recipes/getRecipeDetail/")
    Observable<FoodVo> getRecipeDetail(@Body RequestForm requestForm);

    @POST("/recipes/getRecommendRecipes/")
    Observable<List<FoodRecommendVo>> getRecommendRecipes();

    @POST("/recipes/getIngredientResult/")
    Observable<IngredientResultVo> getIngredientResult(@Body RequestForm requestForm);

    @POST("/recipes/getRecipesList/")
    Observable<RecipesListVo> getRecipesList(@Body RequestForm requestForm);

   @POST("/recipes/getFoodList/")
    Observable<List<FoodMenuVo>> getFoodList(@Body RequestForm requestForm);

}
