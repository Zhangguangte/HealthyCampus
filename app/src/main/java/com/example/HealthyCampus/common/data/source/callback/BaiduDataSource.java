package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;

import java.util.List;

public interface BaiduDataSource {


    interface DiseaseGetSort {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<DiseaseSortVo> diseasSortVos) throws Exception;
    }

    void getDiseaseSort(@NonNull RequestForm requestForm, @NonNull DiseaseGetSort callback);

}
