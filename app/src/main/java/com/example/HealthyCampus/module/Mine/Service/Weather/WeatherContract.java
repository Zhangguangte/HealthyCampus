package com.example.HealthyCampus.module.Mine.Service.Weather;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface WeatherContract {
    interface View extends BaseView {
        Context getContext();



    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
