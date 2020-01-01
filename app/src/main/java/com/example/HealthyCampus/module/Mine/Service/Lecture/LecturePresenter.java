package com.example.HealthyCampus.module.Mine.Service.Lecture;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.data.source.repository.DiseaseRepository;
import com.example.HealthyCampus.common.data.source.repository.ServiceRepository;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.network.vo.LectureVo;

import java.util.List;

public class LecturePresenter extends LectureContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getLectureList(String college,int row) {
        RequestForm requestForm = new RequestForm( "", "("+college+")",row);
        ServiceRepository.getInstance().getLectureList(requestForm, new ServiceDataSource.GetLectureList() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<LectureVo> lectureVos) throws Exception {
                getView().showSuccess(lectureVos);
            }
        });
    }
}
