package com.example.HealthyCampus.common.data.source.callback;

import android.support.annotation.NonNull;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.MedicineDetailVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;

import java.util.List;

public interface MedicineDataSource {

    interface MedicineAllClassify {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<MedicineVo> medicineVos) throws Exception;
    }

    void getAllClassify(@NonNull MedicineAllClassify callback);

    interface MedicineGetAllMedicine {
        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(List<MedicineListVo> medicineVos) throws Exception;
    }

    void getAllMedicine(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback);    //获得所有查询的药品数据

    interface MedicineGetMedicineDetail {

        void onDataNotAvailable(Throwable throwable) throws Exception;

        void onDataAvailable(MedicineDetailVo medicineVo) throws Exception;
    }

    void getMedicineDetail(@NonNull RequestForm requestForm, @NonNull MedicineGetMedicineDetail callback);


    void getAllMedicineByKey(@NonNull RequestForm requestForm, @NonNull MedicineGetAllMedicine callback);



}
