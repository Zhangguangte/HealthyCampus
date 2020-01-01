package com.example.HealthyCampus.module.Mine.Service.Lecture.Detail;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.data.source.repository.ServiceRepository;
import com.example.HealthyCampus.common.network.vo.LectureVo;

import java.util.List;

public class LectureDetailPresenter extends LectureDetailContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getLectureDetail(String id) {
        RequestForm requestForm = new RequestForm(id);
        ServiceRepository.getInstance().getLectureDetail(requestForm, new ServiceDataSource.GetLectureDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(LectureVo lectureVo) throws Exception {
                getView().showSuccess(lectureVo);
            }
        });
    }

}
