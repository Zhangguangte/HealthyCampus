package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class RecipesRepository implements RecipesDataSource {

    private final RecipesDataSource mRecipesRemoteDataSource;
    private final RecipesDataSource mRecipesLocalDataSource;
    private static RecipesRepository INSTANCE = null;

    public static RecipesRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Recipes repository must be init !");
        }
        return INSTANCE;
    }

    private RecipesRepository(RecipesDataSource recipesRemoteDataSource, RecipesDataSource recipeseLocalDataSource) {
        mRecipesRemoteDataSource = checkNotNull(recipesRemoteDataSource);
        mRecipesLocalDataSource = checkNotNull(recipeseLocalDataSource);
    }

    public static void initialize(RecipesDataSource recipesRemoteDataSource, RecipesDataSource recipesLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new RecipesRepository(recipesRemoteDataSource, recipesLocalDataSource);
    }


    @Override
    public void getRecipesByThreeMeals(@NonNull RequestForm requestForm,@NonNull RecipesThreesMeals callback) {
        mRecipesRemoteDataSource.getRecipesByThreeMeals(requestForm,callback);
    }

    @Override
    public void getRecipeDetail(@NonNull RequestForm requestForm, @NonNull RecipesGetDetail callback) {
        mRecipesRemoteDataSource.getRecipeDetail(requestForm, callback);
    }

    @Override
    public void getRecommendRecipes(@NonNull RecipesGetRecommend callback) {
        mRecipesRemoteDataSource.getRecommendRecipes(callback);
    }

    @Override
    public void getDishResult(@NonNull String data, @NonNull RecipesGetDish callback) {
        mRecipesRemoteDataSource.getDishResult(data,callback);
    }

    @Override
    public void getIngredientResult(@NonNull String data, @NonNull RecipesGetIngredient callback) {
        mRecipesRemoteDataSource.getIngredientResult(data,callback);
    }


    @Override
    public void getIngredientResult(@NonNull RequestForm requestForm, @NonNull RecipesGetSearch callback) {
        mRecipesRemoteDataSource.getIngredientResult(requestForm,callback);
    }

    @Override
    public void getRecipesList(@NonNull RequestForm requestForm, @NonNull RecipesGetList callback) {
        mRecipesRemoteDataSource.getRecipesList(requestForm,callback);
    }@Override
    public void getFoodList(@NonNull RequestForm requestForm, @NonNull RecipesGetFood callback) {
        mRecipesRemoteDataSource.getFoodList(requestForm,callback);
    }
}
