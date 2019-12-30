package com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail;

import android.content.Context;

import com.example.HealthyCampus.common.data.Bean.CookDetailBean;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface RecipesDetailContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

        void showTitle(String title);

        void showMaterialSuccess(List<CookDetailBean> cookDetailBeans);

        void showDetailSuccess(List<String> detailList);

        void showIconSuccess(String url);

        void showGeneralSuccess(String flavor, String productionTime, String mainProcess);

        void showElementSuccess(String components);


    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getRecipeDetail(int type, String id);


    }
}
