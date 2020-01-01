package com.example.HealthyCampus.module.Mine.Service.Lecture;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.network.vo.LectureVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface LectureContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(List<LectureVo> lectureVos);

    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void getLectureList(String college,int row);
    }
}
