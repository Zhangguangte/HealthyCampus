package com.example.HealthyCampus.module.Find.Diagnosis.List;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.repository.DiseaseRepository;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;

import java.util.List;

public class DiagnosisListPresenter extends DiagnosisListContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getDiseaseSortList(int position) {
        RequestForm requestForm = new RequestForm(position + "");
        DiseaseRepository.getInstance().getDiseaseSortList(requestForm, new DiseaseDataSource.DiseaseGetSortList() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView())
                    getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<DiseaseSortListVo> diseaseSortListVos) throws Exception {
                if (null != getView())
                    getView().showDiagnosisSortSuccess(diseaseSortListVos);
            }
        });
    }
}
