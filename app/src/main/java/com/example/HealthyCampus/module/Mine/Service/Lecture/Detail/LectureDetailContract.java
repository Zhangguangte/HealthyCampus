package com.example.HealthyCampus.module.Mine.Service.Lecture.Detail;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.CourseVo;
import com.example.HealthyCampus.common.network.vo.LectureVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface LectureDetailContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(LectureVo lectureVo);

    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getLectureDetail(String id);
    }
}
