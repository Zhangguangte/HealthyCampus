package com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.repository.DiseaseRepository;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;

import java.util.List;

public class DiseaseSortPresenter extends DiseaseSortContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getDiseaseSort(int row, String content, int type) {
        RequestForm requestForm = new RequestForm(type + "", "%("+content+")%", row);
        DiseaseRepository.getInstance().getDiseaseSort(requestForm, new DiseaseDataSource.DiseaseGetSort() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<DiseaseSortVo> diseasSortVos) throws Exception {
                getView().showDiagnosisSortListSuccess(diseasSortVos);
            }
        });
    }




}
