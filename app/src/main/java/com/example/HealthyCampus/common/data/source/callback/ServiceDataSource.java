package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.BookDetailVo;
import com.example.HealthyCampus.common.network.vo.BookVo;

import java.util.List;

public interface ServiceDataSource {


    interface GetSearchBook {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<BookVo> bookVos) throws Exception;
    }

    void searchBook(@NonNull RequestForm requestForm, @NonNull GetSearchBook callback);

    interface GetBookDetail {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(BookDetailVo bookDetailVo) throws Exception;
    }

    void searchBookDetail(@NonNull RequestForm requestForm, @NonNull GetBookDetail callback);

}
