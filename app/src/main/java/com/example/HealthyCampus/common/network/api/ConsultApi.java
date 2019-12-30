package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.ConsultPictureForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface ConsultApi {

    @POST("/consult/saveConsultPicture/")
    Observable<DefaultResponseVo> saveConsultPicture(@Body ConsultPictureForm consultPictureForm);
}
