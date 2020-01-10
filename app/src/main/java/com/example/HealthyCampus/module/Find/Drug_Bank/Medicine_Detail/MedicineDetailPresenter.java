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
        getMedicineDetail(new RequestForm("ID", id));
    }

    @Override
    protected void getMedicineDetailByName(String id) {           //获得药品的详细信息根据名称
        getMedicineDetail(new RequestForm("NAME", id));
    }

    @Override
    protected void getMedicineDetailByCode(String code) {           //获得药品的详细信息根据条形码
        getMedicineDetail(new RequestForm("CODE", code));
    }

    private void getMedicineDetail(RequestForm requestForm) {
        MedicineRepository.getInstance().getMedicineDetail(requestForm, new MedicineDataSource.MedicineGetMedicineDetail() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView())

                    getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(MedicineDetailVo medicineVo) throws Exception {
                if (null != getView()) getView().showMedicineDetail(medicineVo);
            }
        });
    }

}
