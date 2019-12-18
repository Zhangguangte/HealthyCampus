package com.example.HealthyCampus.module.Find.Drug_Bank.Medicine_Detail;


import android.util.Log;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;
import com.example.HealthyCampus.common.data.source.repository.MedicineRepository;
import com.example.HealthyCampus.common.network.vo.MedicineDetailVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MedicineDetailPresenter extends MedicineDetailContract.Presenter {

    @Override
    public void onStart() {

    }


    @Override
    protected void getMedicineDetailById(String id) {           //获得药品的详细信息根据ID
        RequestForm requestForm = new RequestForm("ID",id);
        MedicineRepository.getInstance().getMedicineDetail(requestForm, new MedicineDataSource.MedicineGetMedicineDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }
            @Override
            public void onDataAvailable(MedicineDetailVo medicineVo) throws Exception {
                getView().showMedicineDetail(medicineVo);
            }
        });
    }

    @Override
    protected void getMedicineDetailByName(String id) {           //获得药品的详细信息根据名称
        RequestForm requestForm = new RequestForm("NAME",id);
        MedicineRepository.getInstance().getMedicineDetail(requestForm, new MedicineDataSource.MedicineGetMedicineDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }
            @Override
            public void onDataAvailable(MedicineDetailVo medicineVo) throws Exception {
                getView().showMedicineDetail(medicineVo);
            }
        });
    }

}
