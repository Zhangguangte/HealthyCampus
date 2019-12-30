package com.example.HealthyCampus.module.HomePage.Consultation.Picture;


import com.example.HealthyCampus.common.data.source.callback.ConsultDataSource;
import com.example.HealthyCampus.common.data.source.repository.ConsultRepository;
import com.example.HealthyCampus.common.data.form.ConsultPictureForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.greendao.model.PatienInforBean;

import java.util.List;

public class ConsultPicturePresenter extends ConsultPictureContract.Presenter {

    @Override
    public void onStart() {
    }


    @Override
    protected void saveConsultPicture(String describe, List<String> images, List<PatienInforBean> patienInforBeans, boolean prescription, boolean history) {
        ConsultPictureForm consultPictureForm = new ConsultPictureForm();
        consultPictureForm.setDescribe(describe);
        consultPictureForm.setImages(images);
        consultPictureForm.setHistory(history);
        consultPictureForm.setPatienInforBeans(patienInforBeans);
        consultPictureForm.setPrescription(prescription);
        ConsultRepository.getInstance().saveConsultPicture(consultPictureForm, new ConsultDataSource.ConsultUpPicture() {
            @Override
            public void onDataNotAvailable(Throwable throwable) throws Exception {
                getView().showError(throwable);
            }

            @Override
            public void onDataAvailable(DefaultResponseVo defaultResponseVo) throws Exception {
                getView().showSuccess();
            }
        });
    }
}
