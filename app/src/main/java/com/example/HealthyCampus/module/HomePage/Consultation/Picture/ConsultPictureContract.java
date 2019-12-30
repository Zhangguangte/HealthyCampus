package com.example.HealthyCampus.module.HomePage.Consultation.Picture;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;
import com.example.HealthyCampus.greendao.model.PatienInforBean;

import java.util.List;

interface ConsultPictureContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess();
    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void saveConsultPicture(String describe, List<String> images, List<PatienInforBean> patienInforBeans, boolean prescription, boolean history);
    }
}
