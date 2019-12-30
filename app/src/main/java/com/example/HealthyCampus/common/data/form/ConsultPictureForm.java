package com.example.HealthyCampus.common.data.form;

import com.example.HealthyCampus.greendao.model.PatienInforBean;

import java.io.Serializable;
import java.util.List;

public class ConsultPictureForm implements Serializable {
    private String describe;
    private List<String> images;
    private List<PatienInforBean> patienInforBeans;
    private boolean prescription;
    private boolean history;


    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }

    public List<PatienInforBean> getPatienInforBeans() {
        return patienInforBeans;
    }

    public void setPatienInforBeans(List<PatienInforBean> patienInforBeans) {
        this.patienInforBeans = patienInforBeans;
    }

    public boolean isPrescription() {
        return prescription;
    }

    public void setPrescription(boolean prescription) {
        this.prescription = prescription;
    }

    public boolean isHistory() {
        return history;
    }

    public void setHistory(boolean history) {
        this.history = history;
    }
}
