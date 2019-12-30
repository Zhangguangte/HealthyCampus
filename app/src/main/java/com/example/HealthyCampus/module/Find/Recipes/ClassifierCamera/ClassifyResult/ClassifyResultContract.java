package com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera.ClassifyResult;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.IngredientResultVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface ClassifyResultContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showIngredientSuccess(IngredientResultVo ingredientResultVo);

    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getIngredientResult(String name);


    }
}
