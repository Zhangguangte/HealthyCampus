package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.DiseaseDataSource;
import com.example.HealthyCampus.common.data.source.callback.RecipesDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class DiseaseRepository implements DiseaseDataSource {

    private final DiseaseDataSource mDiseaseRemoteDataSource;
    private final DiseaseDataSource mDiseaseLocalDataSource;
    private static DiseaseRepository INSTANCE = null;

    public static DiseaseRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Recipes repository must be init !");
        }
        return INSTANCE;
    }

    private DiseaseRepository(DiseaseDataSource diseaseRemoteDataSource, DiseaseDataSource diseaseLocalDataSource) {
        mDiseaseRemoteDataSource = checkNotNull(diseaseRemoteDataSource);
        mDiseaseLocalDataSource = checkNotNull(diseaseLocalDataSource);
    }

    public static void initialize(DiseaseDataSource diseaseRemoteDataSource, DiseaseDataSource diseaseLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new DiseaseRepository(diseaseRemoteDataSource, diseaseLocalDataSource);
    }



    @Override
    public void getDiseaseSort(@NonNull RequestForm requestForm, @NonNull DiseaseGetSort callback) {
        mDiseaseRemoteDataSource.getDiseaseSort(requestForm,callback);
    }

  @Override
    public void getDiseaseSortList(@NonNull RequestForm requestForm, @NonNull DiseaseGetSortList callback) {
        mDiseaseRemoteDataSource.getDiseaseSortList(requestForm,callback);
    }

    @Override
    public void getDiseaseDetail(@NonNull RequestForm requestForm, @NonNull DiseaseGetDetail callback) {
        mDiseaseRemoteDataSource.getDiseaseDetail(requestForm,callback);
    }

}
