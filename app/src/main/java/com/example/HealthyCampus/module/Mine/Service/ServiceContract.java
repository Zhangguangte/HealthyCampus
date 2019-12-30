package com.example.HealthyCampus.module.Mine.Service;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface ServiceContract {
    interface View extends BaseView {
        Context getContext();



    }

    abstract class Presenter extends BasePresenter<View> {


    }
}
