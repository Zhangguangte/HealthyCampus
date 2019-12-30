package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;
import com.example.HealthyCampus.common.network.vo.IngredientResultVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;
import com.example.HealthyCampus.common.network.vo.RecipesListVo;

import java.util.List;

public interface RecipesDataSource {


    interface RecipesThreesMeals {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<FoodMenuVo> foodMenuVos) throws Exception;
    }

    void getRecipesByThreeMeals(@NonNull RequestForm requestForm,@NonNull RecipesThreesMeals callback);

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

    interface RecipesGetDish {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(DishVo dishVo) throws Exception;

        void onFinish() throws Exception;
    }

    void getDishResult(@NonNull String data, @NonNull RecipesGetDish callback);

    interface RecipesGetIngredient {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(IngredientVo ingredientVo) throws Exception;

        void onFinish() throws Exception;
    }

    void getIngredientResult(@NonNull String data, @NonNull RecipesGetIngredient callback);

    interface RecipesGetSearch {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(IngredientResultVo ingredientResultVo) throws Exception;
    }

    void getIngredientResult(@NonNull RequestForm requestForm, @NonNull RecipesGetSearch callback);

    interface RecipesGetList {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(RecipesListVo recipesListVo) throws Exception;
    }

    void getRecipesList(@NonNull RequestForm requestForm, @NonNull RecipesGetList callback);

    interface RecipesGetFood {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<FoodMenuVo> foodMenuVos) throws Exception;
    }

    void getFoodList(@NonNull RequestForm requestForm, @NonNull RecipesGetFood callback);


}
