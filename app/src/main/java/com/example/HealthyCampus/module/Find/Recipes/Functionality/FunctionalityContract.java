package com.example.HealthyCampus.module.Find.Recipes.Functionality;

import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface FunctionalityContract {
    interface View extends BaseView {

    }

    abstract class Presenter extends BasePresenter<View> {

    }
}
