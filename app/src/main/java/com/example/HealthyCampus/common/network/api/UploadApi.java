package com.example.HealthyCampus.common.network.api;

import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;

import java.util.Map;

import okhttp3.RequestBody;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PartMap;
import rx.Observable;

public interface UploadApi {

//    @Multipart
//    @POST("/GETE/UpImage")
//    Observable<DefaultResponseVo> upPicture(@PartMap Map<String, RequestBody> params);

    @Multipart
    @POST("/messages/upload2IM")
    Observable<DefaultResponseVo> upPicture(@PartMap Map<String, RequestBody> params);


//    @Multipart
//    @POST("/messages/upload2IM/")
//    Observable<DefaultResponseVo> uploadImage2IM(@Part MultipartBody.Part imageFile);

}
