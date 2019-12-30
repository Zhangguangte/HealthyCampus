package com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information.Add;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface InformationAddContract {
    interface View extends BaseView {
        Context getContext();



    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void upPicture(String path, String name, String account);
    }
}
