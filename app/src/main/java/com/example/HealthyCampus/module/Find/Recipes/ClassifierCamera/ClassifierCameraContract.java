package com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DishVo;
import com.example.HealthyCampus.common.network.vo.IngredientVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface ClassifierCameraContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showDishSuccess(DishVo dishVo);

        void showIngredientSuccess(IngredientVo ingredientVo);

        void enableClick(boolean val);
    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getDishResult(String data);

        protected abstract void getIngredientResult(String data);
    }
}
