package com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.Detail;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.repository.DiseaseRepository;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;

import java.util.List;

public class DiseaseDetailPresenter extends DiseaseDetailContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getDiseaseDetailById(String id) {
        RequestForm requestForm = new RequestForm("ID", id);
        DiseaseRepository.getInstance().getDiseaseDetail(requestForm, new DiseaseDataSource.DiseaseGetDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DiseaseDetailVo diseaseDetailVo) throws Exception {
                if (null != getView()) getView().showDiseaseDetailSuccess(diseaseDetailVo);
            }
        });
    }

    @Override
    protected void getDiseaseDetailByName(String name) {
        RequestForm requestForm = new RequestForm("NAME", name);
        DiseaseRepository.getInstance().getDiseaseDetail(requestForm, new DiseaseDataSource.DiseaseGetDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DiseaseDetailVo diseaseDetailVo) throws Exception {
                if (null != getView()) getView().showDiseaseDetailSuccess(diseaseDetailVo);
            }
        });
    }



}
