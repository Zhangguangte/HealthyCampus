package com.example.HealthyCampus.module.Mine.Physique;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface PhysiqueContract {
    interface View extends BaseView {
        Context getContext();



    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
