package com.example.HealthyCampus.module.Find.Recipes.Customization.Fragment;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface CustomizationContract {
    interface View extends BaseView {
    }

    abstract class Presenter extends BasePresenter<View> {
    }
}
