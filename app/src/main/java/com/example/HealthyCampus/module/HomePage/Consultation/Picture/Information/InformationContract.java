package com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface InformationContract {
    interface View extends BaseView {
        Context getContext();



    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void upPicture(String path, String name, String account);
    }
}
