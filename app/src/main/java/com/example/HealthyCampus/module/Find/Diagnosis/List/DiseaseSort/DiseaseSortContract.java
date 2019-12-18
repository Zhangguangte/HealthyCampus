package com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface DiseaseSortContract {
    interface View extends BaseView {
        Context getContext();


        void showError(Throwable throwable);

        void showDiagnosisSortListSuccess(List<DiseaseSortVo> diseasSortVos);
    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getDiseaseSort(int row, String content,int type);

    }
}
