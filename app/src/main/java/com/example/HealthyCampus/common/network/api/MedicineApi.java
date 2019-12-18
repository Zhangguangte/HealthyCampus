package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.data.Bean.MedicineClassify;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MedicineDetailVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Body;
import retrofit2.http.POST;
import rx.Observable;

public interface MedicineApi {

    @POST("/medicine/getClassify/")
    Observable<List<MedicineVo>> getAllClassify();

    @POST("/medicine/getAllMedicine/")
    Observable<List<MedicineListVo>> getAllMedicine(@Body RequestForm requestForm);

    @POST("/medicine/getMedicineDetail/")
    Observable<MedicineDetailVo> getMedicineDetail(@Body RequestForm requestForm);


    @POST("/medicine/getAllMedicineByKey/")
    Observable<List<MedicineListVo>> getAllMedicineByKey(@Body RequestForm requestForm);

}
