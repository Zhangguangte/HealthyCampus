package com.example.HealthyCampus.common.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.FoodVo;

import java.util.List;

import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action0;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

public class RecipesRemoteDataSource implements RecipesDataSource {

    private static RecipesRemoteDataSource INSTANCE = null;


    public RecipesRemoteDataSource() {
    }

    public static RecipesRemoteDataSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new RecipesRemoteDataSource();
        }
        return INSTANCE;
    }


    @Override
    public void getRecipesByThreeMeals(@NonNull RecipesThreesMeals callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecipesByThreeMeals()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("RecipesRem" + "123456", "getRecipesByThreeMeals:7");
                    }
                })
                .subscribe(new Action1<List<FoodMenuVo>>() {
                    @Override
                    public void call(List<FoodMenuVo> foodMenuVos) {
                        try {
                            Log.e("RecipesRem" + "123456", "getRecipesByThreeMeals success");
                            callback.onDataAvailable(foodMenuVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("RecipesRem" + "123456", "getRecipesByThreeMeals:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getRecipeDetail(@NonNull RequestForm requestForm, @NonNull RecipesGetDetail callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecipeDetail(requestForm)
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("RecipesRem" + "123456", "getRecipeDetail:7");
                    }
                })
                .subscribe(new Action1<FoodVo>() {
                    @Override
                    public void call(FoodVo foodVo) {
                        try {
                            Log.e("RecipesRem" + "123456", "getRecipeDetail success");
                            callback.onDataAvailable(foodVo);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("RecipesRem" + "123456", "getRecipeDetail:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }

    @Override
    public void getRecommendRecipes(@NonNull RecipesGetRecommend callback) {
        NetworkManager.getInstance().getRecipesApi()
                .getRecommendRecipes()
                .subscribeOn(Schedulers.newThread())//子线程访问网络
                .observeOn(AndroidSchedulers.mainThread())//回调到主线程
                .doOnSubscribe(new Action0() {
                    @Override
                    public void call() {
                        Log.e("RecipesRem" + "123456", "getRecommendRecipes:7");
                    }
                })
                .subscribe(new Action1<List<FoodRecommendVo>>() {
                    @Override
                    public void call(List<FoodRecommendVo> foodRecommendVos) {
                        try {
                            Log.e("RecipesRem" + "123456", "getRecommendRecipes success");
                            callback.onDataAvailable(foodRecommendVos);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Action1<Throwable>() {
                    @Override
                    public void call(Throwable throwable) {
                        try {
                            Log.e("RecipesRem" + "123456", "getRecommendRecipes:9");
                            callback.onDataNotAvailable(throwable);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                });
    }
}
