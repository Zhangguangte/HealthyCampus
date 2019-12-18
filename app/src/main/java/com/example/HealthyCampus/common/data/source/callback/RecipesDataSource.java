package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;

import java.util.List;

public interface RecipesDataSource {


    interface RecipesThreesMeals {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<FoodMenuVo> foodMenuVos) throws Exception;
    }

    void getRecipesByThreeMeals(@NonNull RecipesThreesMeals callback);

    interface RecipesGetDetail {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(FoodVo foodVo) throws Exception;
    }

    void getRecipeDetail(@NonNull RequestForm requestForm, @NonNull RecipesGetDetail callback);

   interface RecipesGetRecommend {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<FoodRecommendVo> foodRecommendVos) throws Exception;
    }

    void getRecommendRecipes(@NonNull RecipesGetRecommend callback);


}
