package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.ConsultPictureForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;

public interface ConsultDataSource {


    interface ConsultUpPicture {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(DefaultResponseVo defaultResponseVo) throws Exception;
    }

    void saveConsultPicture(@NonNull ConsultPictureForm consultPictureForm, @NonNull ConsultUpPicture callback);

}
