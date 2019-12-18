package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;

import java.util.List;

public interface DiseaseDataSource {


    interface DiseaseGetSort {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<DiseaseSortVo> diseasSortVos) throws Exception;
    }

    void getDiseaseSort(@NonNull RequestForm requestForm, @NonNull DiseaseGetSort callback);


    interface DiseaseGetSortList {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<DiseaseSortListVo> diseaseSortListVos) throws Exception;

    }

    void getDiseaseSortList(@NonNull RequestForm requestForm, @NonNull DiseaseGetSortList callback);


    interface DiseaseGetDetail {
        void onDataNotAvailable(Throwable throwable) throws Exception;
        void onDataAvailable(DiseaseDetailVo diseaseDetailVo) throws Exception;
    }

    void getDiseaseDetail(@NonNull RequestForm requestForm, @NonNull DiseaseGetDetail callback);


}
