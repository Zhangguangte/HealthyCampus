package com.example.HealthyCampus.module.Find.Diagnosis;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;
import com.example.HealthyCampus.common.data.source.repository.DiseaseRepository;
import com.example.HealthyCampus.common.data.source.repository.MedicineRepository;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiagnosisPresenter extends DiagnosisContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getDiseaseInfo(String keyword, int row) {
        RequestForm requestForm = new RequestForm( "2", "%"+keyword+"%", row);
        DiseaseRepository.getInstance().getDiseaseSort(requestForm, new DiseaseDataSource.DiseaseGetSort() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<DiseaseSortVo> diseasSortVos) throws Exception {
                getView().showDiseaseSuccess(diseasSortVos);
            }
        });
    }
}
