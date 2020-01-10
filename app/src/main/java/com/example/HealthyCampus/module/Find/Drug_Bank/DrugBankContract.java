package com.example.HealthyCampus.module.Find.Drug_Bank;

import android.content.Context;

import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;
import com.example.HealthyCampus.framework.BasePresenter;
import com.example.HealthyCampus.framework.BaseView;

import java.util.List;
import java.util.Map;

interface DrugBankContract {
    interface View extends BaseView {
        Context getContext();

        void showTypeError();

        void showMedicineError();

        void showAllMedicineError();

        void showError(Throwable throwable);

        void showClassify(Map<String, List<String>> classifyMap, List<String> typeList);

        void showAllMedicineSuccess(List<MedicineListVo> medicineListVos,String classifyName);

        void showMedicineByKey(List<MedicineListVo> medicineListVos);

        boolean isClassify(String classifyName);
        boolean isKeyword(String keyword);
    }

    abstract class Presenter extends BasePresenter<View> {
        protected abstract void getAllClassify();

        protected abstract RequestForm changEncapsulation(String scope, String keyWord, int row);       //封装范围数据

        protected abstract void getAllMedicine(String classifyName, int row);

        protected abstract void getAllMedicineByKey(String scope, String keyWord, int row);


    }
}
