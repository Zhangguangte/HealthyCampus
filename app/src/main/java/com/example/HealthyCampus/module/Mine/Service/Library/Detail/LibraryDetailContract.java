package com.example.HealthyCampus.module.Mine.Service.Library.Detail;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.BookDetailVo;
import com.example.HealthyCampus.common.network.vo.BookVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface LibraryDetailContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(BookDetailVo bookDetailVo);


    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void searchBookDetail(String id);
    }
}
