package com.example.HealthyCampus.common.data.source.repository;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.FriendDataSource;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;

import static com.google.gson.internal.$Gson$Preconditions.checkNotNull;

public class MedicineRepository implements MedicineDataSource {

    private final MedicineDataSource mMedicineRemoteDataSource;
    private final MedicineDataSource mMedicineLocalDataSource;
    private static MedicineRepository INSTANCE = null;

    public static MedicineRepository getInstance() {
        if (INSTANCE == null) {
            throw new NullPointerException("the Friend repository must be init !");
        }
        return INSTANCE;
    }

    private MedicineRepository(MedicineDataSource medicineRemoteDataSource, MedicineDataSource medicineLocalDataSource) {
        mMedicineRemoteDataSource = checkNotNull(medicineRemoteDataSource);
        mMedicineLocalDataSource = checkNotNull(medicineLocalDataSource);
    }

    public static void initialize(MedicineDataSource medicineRemoteDataSource, MedicineDataSource medicineLocalDataSource) {
        INSTANCE = null;
        INSTANCE = new MedicineRepository(medicineRemoteDataSource, medicineLocalDataSource);
    }


    @Override
    public void getAllClassify(@NonNull MedicineAllClassify callback) {
        mMedicineRemoteDataSource.getAllClassify(callback);
    }

    @Override
    public void getAllMedicine(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback) {
        mMedicineRemoteDataSource.getAllMedicine(requestForm, callback);
    }

    @Override
    public void getMedicineDetail(@NonNull RequestForm requestForm, @NonNull MedicineGetMedicineDetail callback) {
        mMedicineRemoteDataSource.getMedicineDetail(requestForm, callback);
    }

    public void getAllMedicineByKey(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback) {
        mMedicineRemoteDataSource.getAllMedicineByKey(requestForm, callback);
    }
}
