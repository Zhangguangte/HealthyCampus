package com.example.HealthyCampus.module.Find.Nearby;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface NearbyContract {
    interface View extends BaseView {
        Context getContext();

    }

    abstract class Presenter extends BasePresenter<View> {


    }

}
