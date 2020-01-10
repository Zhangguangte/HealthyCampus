package com.example.HealthyCampus.module.Find.Recipes.Recommend;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;
import com.example.HealthyCampus.common.data.source.repository.RecipesRepository;

import java.util.List;

public class RecommendPresenter extends RecommendContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getRecommendRecipes() {
        RecipesRepository.getInstance().getRecommendRecipes(new RecipesDataSource.RecipesGetRecommend() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView())       getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<FoodRecommendVo> foodRecommendVos) throws Exception {
                if (null != getView())        getView().showRecommendSuccess(foodRecommendVos);
            }
        });
    }
}
