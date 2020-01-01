package com.example.HealthyCampus.module.Mine.Service.timeTable;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.data.source.repository.ServiceRepository;
import com.example.HealthyCampus.common.network.vo.CourseVo;

import java.util.List;

public class TimeTablePresenter extends TimeTableContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void getTimeTable(String major) {
        RequestForm requestForm = new RequestForm("", major);
        ServiceRepository.getInstance().getTimeTable(requestForm, new ServiceDataSource.GetTimeTable() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if(null != getView())
                {
                    getView().showError(throwable);
                }
            }

            @Override
            public void onDataAvailable(List<CourseVo> courses) throws Exception {
                if(null != getView())
                {
                    getView().showSuccess(courses);
                }
            }

        });
    }
}
