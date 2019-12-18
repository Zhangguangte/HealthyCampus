package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;

import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface DiseaseApi {

    @POST("/disease/getDiseaseSortList/")
    Observable<List<DiseaseSortListVo>> getDiseaseSortList(@Body RequestForm requestForm);

    @POST("/disease/getDiseaseSort/")
    Observable<List<DiseaseSortVo>> getDiseaseSort (@Body RequestForm requestForm);

   @POST("/disease/getDiseaseDetail/")
    Observable<DiseaseDetailVo> getDiseaseDetail (@Body RequestForm requestForm);


}
