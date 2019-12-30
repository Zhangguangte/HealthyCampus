package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.source.callback.ConsultDataSource;
import com.example.HealthyCampus.common.data.form.ConsultPictureForm;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class ConsultLocalDataSource implements ConsultDataSource {
    private static ConsultLocalDataSource INSTANCE;

    private ConsultLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static ConsultLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ConsultLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void saveConsultPicture(@NonNull ConsultPictureForm consultPictureForm, @NonNull ConsultUpPicture callback) {

    }
}
