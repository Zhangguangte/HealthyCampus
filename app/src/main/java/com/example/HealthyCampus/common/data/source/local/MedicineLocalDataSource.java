package com.example.HealthyCampus.common.data.source.local;

import android.content.Context;
import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MedicineLocalDataSource implements MedicineDataSource {
    private static MedicineLocalDataSource INSTANCE;

    private MedicineLocalDataSource(@NonNull Context context) {
        checkNotNull(context);
    }

    public static MedicineLocalDataSource getInstance(@NonNull Context context) {
        if (INSTANCE == null) {
            INSTANCE = new MedicineLocalDataSource(context);
        }
        return INSTANCE;
    }

    @Override
    public void getAllClassify(@NonNull MedicineAllClassify callback) {

    }

    @Override
    public void getAllMedicine(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback) {

    }

    @Override
    public void getMedicineDetail(@NonNull RequestForm requestForm, @NonNull MedicineGetMedicineDetail callback) {

    }

    @Override
    public void getAllMedicineByKey(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback) {

    }
}
