package com.example.HealthyCampus.module.Mine.Service.Library;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.BookVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;

interface LibraryContract {
    interface View extends BaseView {
        Context getContext();

        void showError(Throwable throwable);

        void showSuccess(List<BookVo> bookVos);


    }

    abstract class Presenter extends BasePresenter<View> {

        protected abstract void searchBook(String bookName);
    }
}
