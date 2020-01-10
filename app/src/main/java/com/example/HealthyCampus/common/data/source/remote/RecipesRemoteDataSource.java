package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RecipesRemoteDataSource implements RecipesDataSource {

    private static RecipesRemoteDataSource INSTANCE = null;


    public static RecipesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RecipesRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getRecipesByThreeMeals(@NonNull RequestForm requestForm, @NonNull RecipesThreesMeals callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecipesByThreeMeals(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getRecipesByThreeMeals:7"))
                .subscribe((Action1<List<FoodMenuVo>>) foodMenuVos -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecipesByThreeMeals success");
                        callback.onDataAvailable(foodMenuVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecipesByThreeMeals:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getRecipeDetail(@NonNull RequestForm requestForm, @NonNull RecipesGetDetail callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecipeDetail(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getRecipeDetail:7"))
                .subscribe(foodVo -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecipeDetail success");
                        callback.onDataAvailable(foodVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecipeDetail:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getRecommendRecipes(@NonNull RecipesGetRecommend callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecommendRecipes()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getRecommendRecipes:7"))
                .subscribe(foodRecommendVos -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecommendRecipes success");
                        callback.onDataAvailable(foodRecommendVos);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecommendRecipes:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


    //通过图片识别，识别菜肴
    @Override
    public void getDishResult(@NonNull String data, @NonNull RecipesGetDish callback) {
        NetworkManager.getBaiduApi()
                .getDishResult(data, "5", SPHelper.getString(SPHelper.BAIDU_TOKEN))
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getDishResult:7"))
                .subscribe(dishVo -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getDishResult success");
                        callback.onDataAvailable(dishVo);
                        callback.onFinish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getDishResult:9");
                        callback.onDataNotAvailable(throwable);
                        callback.onFinish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    //通过图片识别，识别食材
    @Override
    public void getIngredientResult(@NonNull String data, @NonNull RecipesGetIngredient callback) {
        NetworkManager.getBaiduApi()
                .getIngredientResult(data, "5", SPHelper.getString(SPHelper.BAIDU_TOKEN))
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getIngredientResult:7"))
                .subscribe(ingredientVo -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getIngredientResult success");
                        callback.onDataAvailable(ingredientVo);
                        callback.onFinish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getIngredientResult:9");
                        callback.onDataNotAvailable(throwable);
                        callback.onFinish();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getIngredientResult(@NonNull RequestForm requestForm, @NonNull RecipesGetSearch callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getIngredientResult(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getIngredientResult:7"))
                .subscribe(ingredientResultVo -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getIngredientResult success");
                        callback.onDataAvailable(ingredientResultVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getIngredientResult:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getRecipesList(@NonNull RequestForm requestForm, @NonNull RecipesGetList callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecipesList(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getRecipesList:7"))
                .subscribe(recipesListVo -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecipesList success");
                        callback.onDataAvailable(recipesListVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getRecipesList:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }

    @Override
    public void getFoodList(@NonNull RequestForm requestForm, @NonNull RecipesGetFood callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getFoodList(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(() -> Log.e("RecipesRem" + "123456", "getFoodList:7"))
                .subscribe(recipesListVo -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getFoodList success");
                        callback.onDataAvailable(recipesListVo);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }, throwable -> {
                    try {
                        Log.e("RecipesRem" + "123456", "getFoodList:9");
                        callback.onDataNotAvailable(throwable);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
    }


}
