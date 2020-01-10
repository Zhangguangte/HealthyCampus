package com.example.HealthyCampus.module.Find.Recipes.Functionality.List;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.network.vo.RecipesListVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface RecipesListContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

        void showRecipesSuccess(List<FoodMenuVo> foodMenuVos);

        void showRecipesListSuccess(List<String> list);

        boolean isList(String list);
    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getRecipesList(int type, int row);

        protected abstract void getFoodList(String name, int row);

    }
}
