package com.example.HealthyCampus.module.Mine.Service.Library;


import android.app.Service;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.data.source.repository.ServiceRepository;
import com.example.HealthyCampus.common.network.vo.BookVo;

import java.util.List;

public class LibraryPresenter extends LibraryContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void searchBook(String bookName) {
        RequestForm requestForm = new RequestForm("", "%" + bookName + "%");
        ServiceRepository.getInstance().searchBook(requestForm, new ServiceDataSource.GetSearchBook() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView())
                    getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(List<BookVo> bookVos) throws Exception {
                if (null != getView())
                    getView().showSuccess(bookVos);
            }

        });
    }
}
