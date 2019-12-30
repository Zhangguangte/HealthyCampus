package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.source.callback.ConsultDataSource;
import com.example.HealthyCampus.common.data.form.ConsultPictureForm;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class ConsultRepository implements ConsultDataSource {

    private final ConsultDataSource mConsultRemoteDataSource;
    private final ConsultDataSource mConsultLocalDataSource;
    private static ConsultRepository INSTANCE = null;

    public static ConsultRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Friend repository must be init !");
        }
        return INSTANCE;
    }

    private ConsultRepository(ConsultDataSource consultRemoteDataSource, ConsultDataSource consultLocalDataSource) {
        mConsultRemoteDataSource = checkNotNull(consultRemoteDataSource);
        mConsultLocalDataSource = checkNotNull(consultLocalDataSource);
    }

    public static void initialize(ConsultDataSource consultRemoteDataSource, ConsultDataSource consultLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new ConsultRepository(consultRemoteDataSource, consultLocalDataSource);
    }


    @Override
    public void saveConsultPicture(@NonNull ConsultPictureForm consultPictureForm, @NonNull ConsultUpPicture callback) {
        mConsultRemoteDataSource.saveConsultPicture(consultPictureForm, callback);
    }


}
