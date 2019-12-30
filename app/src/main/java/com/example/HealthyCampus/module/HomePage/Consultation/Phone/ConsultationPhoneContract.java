package com.example.HealthyCampus.module.HomePage.Consultation.Phone;

import android.content.Context;

import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

interface ConsultationPhoneContract {
    interface View extends BaseView {
        Context getContext();



    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
