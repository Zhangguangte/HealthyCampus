package com.example.HealthyCampus.module.Find.Diagnosis;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;
import java.util.Map;

interface DiagnosisContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

        void showDiseaseSuccess(List<DiseaseSortVo> diseaseSortVos);

    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getDiseaseInfo(String keyword, int row);

    }
}
