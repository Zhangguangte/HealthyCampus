package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.BookDetailVo;
import com.example.HealthyCampus.common.network.vo.BookVo;
import com.example.HealthyCampus.common.network.vo.CourseVo;
import com.example.HealthyCampus.common.network.vo.LectureVo;

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

    void sendFeed(@NonNull RequestForm requestForm);

    interface GetTimeTable {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<CourseVo> courses) throws Exception;
    }

    void getTimeTable(@NonNull RequestForm requestForm, @NonNull GetTimeTable callback);

    interface GetLectureList {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<LectureVo> lectureVos) throws Exception;
    }

    void getLectureList(@NonNull RequestForm requestForm, @NonNull GetLectureList callback);

    interface GetLectureDetail {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(LectureVo lectureVo) throws Exception;
    }

    void getLectureDetail(@NonNull RequestForm requestForm, @NonNull GetLectureDetail callback);

}
