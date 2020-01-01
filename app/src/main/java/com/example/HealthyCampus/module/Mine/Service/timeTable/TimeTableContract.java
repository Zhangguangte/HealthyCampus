package com.example.HealthyCampus.module.Mine.Service.timeTable;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.CourseVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface TimeTableContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(List<CourseVo> courses);

    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getTimeTable(String major);

    }
}
