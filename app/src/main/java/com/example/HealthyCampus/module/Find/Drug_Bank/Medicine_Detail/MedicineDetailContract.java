package com.example.HealthyCampus.module.Find.Drug_Bank.Medicine_Detail;

import android.content.Context;

import com.example.HealthyCampus.common.network.vo.MedicineDetailVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;
import java.util.Map;

interface MedicineDetailContract {
    interface View extends BaseView {
        Context getContext();

        void showMedicineDetail(MedicineDetailVo medicineVo);

        void showError(Throwable throwable);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getMedicineDetailById(String id);

        protected abstract void getMedicineDetailByName(String name);


    }
}
