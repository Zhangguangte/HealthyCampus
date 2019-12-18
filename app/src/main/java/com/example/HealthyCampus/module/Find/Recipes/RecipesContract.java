package com.example.HealthyCampus.module.Find.Recipes;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface RecipesContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

    }

    abstract class Presenter extends BasePresenter<View> {



    }
}
