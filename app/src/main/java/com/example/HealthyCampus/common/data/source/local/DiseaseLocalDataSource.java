package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class DiseaseLocalDataSource implements DiseaseDataSource {
    private static DiseaseLocalDataSource INSTANCE;

    public DiseaseLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static DiseaseLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new DiseaseLocalDataSource(context);
        }
        return INSTANCE;
    }


    @Override
    public void getDiseaseSort(@NonNull RequestForm requestForm, @NonNull DiseaseGetSort callback) {

    }

    @Override
    public void getDiseaseSortList(@NonNull RequestForm requestForm, @NonNull DiseaseGetSortList callback) {

    }

    @Override
    public void getDiseaseDetail(@NonNull RequestForm requestForm, @NonNull DiseaseGetDetail callback) {

    }
}
