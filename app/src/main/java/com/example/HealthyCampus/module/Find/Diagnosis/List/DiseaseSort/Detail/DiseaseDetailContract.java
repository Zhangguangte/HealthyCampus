package com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.Detail;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface DiseaseDetailContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

        void showDiseaseDetailSuccess(DiseaseDetailVo diseaseDetailVo);
    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getDiseaseDetailById(String id);

        protected abstract void getDiseaseDetailByName(String name);

    }
}
