package com.example.HealthyCampus.module.Find.Recipes.Customization.activity;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface CustomizationContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

        void showRecipesSuccess(List<FoodMenuVo> foodMenuVos);

    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getRecipesByThreeMeals();


    }
}
