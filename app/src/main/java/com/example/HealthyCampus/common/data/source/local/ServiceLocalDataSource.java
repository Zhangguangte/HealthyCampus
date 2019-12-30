package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.ServiceDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class ServiceLocalDataSource implements ServiceDataSource {
    private static ServiceLocalDataSource INSTANCE;

    private ServiceLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static ServiceLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new ServiceLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void searchBook(@NonNull RequestForm requestForm, @NonNull GetSearchBook callback) {

    }

    @Override
    public void searchBookDetail(@NonNull RequestForm requestForm, @NonNull GetBookDetail callback) {

    }
}
