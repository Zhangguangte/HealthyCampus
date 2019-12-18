package com.example.HealthyCampus.module.Find.Diagnosis.List;

import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface DiagnosisListContract {
    interface View extends BaseView {

        void showError(Throwable throwable);

        void showDiagnosisSortSuccess(List<DiseaseSortListVo> recommendFoodBeans);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getDiseaseSortList(int position);
    }
}
