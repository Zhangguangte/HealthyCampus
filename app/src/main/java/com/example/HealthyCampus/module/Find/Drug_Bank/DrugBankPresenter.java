package com.example.HealthyCampus.module.Find.Drug_Bank;


import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.data.source.callback.MedicineDataSource;
import com.example.HealthyCampus.common.data.source.repository.MedicineRepository;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.network.vo.MedicineVo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DrugBankPresenter extends DrugBankContract.Presenter {

    @Override
    public void onStart() {
    }

    @Override
    protected void getAllClassify() {           //获得类型、分类
        MedicineRepository.getInstance().getAllClassify(new MedicineDataSource.MedicineAllClassify() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) {
                    getView().showError(throwable);
                    getView().showTypeError();
                }
            }

            @Override
            public void onDataAvailable(List<MedicineVo> medicineVos) throws Exception {
                if (null != getView()) {
                    Map<String, List<String>> classifyMap = new HashMap<>();
                    List<String> type = new ArrayList<>();
                    for (MedicineVo medicineVo : medicineVos) {
                        type.add(medicineVo.getTypeName());
                        classifyMap.put(medicineVo.getTypeName(), java.util.Arrays.asList(medicineVo.getClassifyName().split(",")));
                    }
                    getView().showClassify(classifyMap, type);
                }
            }
        });
    }

    @Override
    protected RequestForm changEncapsulation(String scope, String keyWord, int row) {
        RequestForm requestForm;
        if ("药品名".equals(scope)) {
            requestForm = new RequestForm("goods_name ", keyWord);
        } else if ("处方药".equals(scope)) {
            requestForm = new RequestForm(" is_otc = 1 and goods_name", keyWord);
        } else if ("非处方药".equals(scope)) {
            requestForm = new RequestForm(" is_otc = 0 and goods_name", keyWord);
        } else if ("批准文号".equals(scope)) {
            requestForm = new RequestForm("approval_number", keyWord);
        } else if ("厂家".equals(scope)) {
            requestForm = new RequestForm("manufacturer", keyWord);
        } else if ("主治".equals(scope)) {
            requestForm = new RequestForm("zhuzhi", keyWord);
        } else
            requestForm = new RequestForm("goods_name", keyWord);
        requestForm.row = row;
        return requestForm;
    }

    @Override
    protected void getAllMedicine(String classifyName, int row) {        //根据分类名，获得分类数据
        RequestForm requestForm = new RequestForm("", classifyName, row);
        MedicineRepository.getInstance().getAllMedicine(requestForm, new MedicineDataSource.MedicineGetAllMedicine() {

            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) {
                    if (getView().isClassify(classifyName)) {
                        getView().showError(throwable);
                        getView().showAllMedicineError();
                    }
                }
            }

            @Override
            public void onDataAvailable(List<MedicineListVo> medicineListVos) throws
                    Exception {
                if (null != getView()) {
                    getView().showAllMedicineSuccess(medicineListVos, classifyName);
                }
            }
        });
    }

    @Override
    protected void getAllMedicineByKey(String scope, String keyWord, int row) {      //根据范围、关键字，获得药品数据
        MedicineRepository.getInstance().getAllMedicineByKey(changEncapsulation(scope, "%" + keyWord + "%", row), new MedicineDataSource.MedicineGetAllMedicine() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                if (null != getView()) {
                    if (getView().isKeyword(keyWord)) {
                        getView().showError(throwable);
                        getView().showMedicineError();
                    }
                }
            }

            @Override
            public void onDataAvailable(List<MedicineListVo> medicineListVos) throws Exception {
                if (null != getView()) {
                    if (getView().isKeyword(keyWord)) {
                        getView().showMedicineByKey(medicineListVos);
                    }
                }
            }
        });
    }


}
