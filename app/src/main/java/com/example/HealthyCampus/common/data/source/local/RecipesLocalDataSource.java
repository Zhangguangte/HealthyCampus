package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class RecipesLocalDataSource implements RecipesDataSource {
    private static RecipesLocalDataSource INSTANCE;

    private RecipesLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static RecipesLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new RecipesLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getRecipesByThreeMeals(@NonNull RequestForm requestForm, @NonNull RecipesThreesMeals callback) {

    }

    @Override
    public void getRecipeDetail(@NonNull RequestForm requestForm, @NonNull RecipesGetDetail callback) {

    }

    @Override
    public void getRecommendRecipes(@NonNull RecipesGetRecommend callback) {

    }

    @Override
    public void getDishResult(@NonNull String data, @NonNull RecipesGetDish callback) {

    }

    @Override
    public void getIngredientResult(@NonNull String data, @NonNull RecipesGetIngredient callback) {

    }

    @Override
    public void getIngredientResult(@NonNull RequestForm requestForm, @NonNull RecipesGetSearch callback) {

    }

    @Override
    public void getRecipesList(@NonNull RequestForm requestForm, @NonNull RecipesGetList callback) {

    }

    @Override
    public void getFoodList(@NonNull RequestForm requestForm, @NonNull RecipesGetFood callback) {

    }

}
