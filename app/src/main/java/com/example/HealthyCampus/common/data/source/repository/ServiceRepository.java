package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class ServiceRepository implements ServiceDataSource {

    private final ServiceDataSource mServiceRemoteDataSource;
    private final ServiceDataSource mServiceLocalDataSource;
    private static ServiceRepository INSTANCE = null;

    public static ServiceRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Service repository must be init !");
        }
        return INSTANCE;
    }

    private ServiceRepository(ServiceDataSource serviceRemoteDataSource, ServiceDataSource serviceLocalDataSource) {
        mServiceRemoteDataSource = checkNotNull(serviceRemoteDataSource);
        mServiceLocalDataSource = checkNotNull(serviceLocalDataSource);
    }

    public static void initialize(ServiceDataSource serviceRemoteDataSource, ServiceDataSource serviceLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new ServiceRepository(serviceRemoteDataSource, serviceLocalDataSource);
    }


    @Override
    public void searchBook(@NonNull RequestForm requestForm, @NonNull GetSearchBook callback) {
        mServiceRemoteDataSource.searchBook(requestForm, callback);
    }

    @Override
    public void searchBookDetail(@NonNull RequestForm requestForm, @NonNull GetBookDetail callback) {
        mServiceRemoteDataSource.searchBookDetail(requestForm, callback);
    }

    @Override
    public void sendFeed(@NonNull RequestForm requestForm) {
        mServiceRemoteDataSource.sendFeed(requestForm);
    }

    @Override
    public void getTimeTable(@NonNull RequestForm requestForm, @NonNull GetTimeTable callback) {
        mServiceRemoteDataSource.getTimeTable(requestForm, callback);
    }

    @Override
    public void getLectureList(@NonNull RequestForm requestForm, @NonNull GetLectureList callback) {
        mServiceRemoteDataSource.getLectureList(requestForm, callback);
    }

    @Override
    public void getLectureDetail(@NonNull RequestForm requestForm, @NonNull GetLectureDetail callback) {
        mServiceRemoteDataSource.getLectureDetail(requestForm, callback);
    }
}
