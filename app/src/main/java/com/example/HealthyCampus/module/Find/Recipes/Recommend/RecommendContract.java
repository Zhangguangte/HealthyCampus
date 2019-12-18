package com.example.HealthyCampus.module.Find.Recipes.Recommend;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface RecommendContract {
    interface View extends BaseView {

        void showError(Throwable throwable);

        void showRecommendSuccess(List<FoodRecommendVo> recommendFoodBeans);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getRecommendRecipes();
    }
}
