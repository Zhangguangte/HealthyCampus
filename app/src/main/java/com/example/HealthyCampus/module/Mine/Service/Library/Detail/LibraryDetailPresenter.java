package com.example.HealthyCampus.module.Mine.Service.Library.Detail;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;
import com.example.HealthyCampus.common.data.source.repository.ServiceRepository;
import com.example.HealthyCampus.common.network.vo.BookDetailVo;
import com.example.HealthyCampus.common.network.vo.BookVo;

import java.util.List;

public class LibraryDetailPresenter extends LibraryDetailContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void searchBookDetail(String id) {
        RequestForm requestForm = new RequestForm(id);
        ServiceRepository.getInstance().searchBookDetail(requestForm, new ServiceDataSource.GetBookDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView())
                    getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(BookDetailVo bookDetailVo) throws Exception {
                if (null != getView())
                    getView().showSuccess(bookDetailVo);
            }

        });
    }
}
