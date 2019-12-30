package com.example.HealthyCampus.module.Find.News;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface HealthNewsContract {
    interface View extends BaseView {
        Context getContext();

    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
