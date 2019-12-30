package com.example.HealthyCampus.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;
import org.greenrobot.greendao.annotation.Generated;

import java.io.Serializable;

@Entity(nameInDb = "PatienInforBean")
public class PatienInforBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id(autoincrement = true)
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    private String card_id;
    private String sex;
    private String birthday;
    private String weight;
    private String allergy;
    private String history;
    private String liver;
    private String kidney;

    @Generated(hash = 1259706540)
    public PatienInforBean(Long id, String name, String card_id, String sex,
                           String birthday, String weight, String allergy, String history,
                           String liver, String kidney) {
        this.id = id;
        this.name = name;
        this.card_id = card_id;
        this.sex = sex;
        this.birthday = birthday;
        this.weight = weight;
        this.allergy = allergy;
        this.history = history;
        this.liver = liver;
        this.kidney = kidney;
    }

    @Generated(hash = 2035765798)
    public PatienInforBean() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCard_id() {
        return card_id;
    }

    public void setCard_id(String card_id) {
        this.card_id = card_id;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getAllergy() {
        return allergy;
    }

    public void setAllergy(String allergy) {
        this.allergy = allergy;
    }

    public String getHistory() {
        return history;
    }

    public void setHistory(String history) {
        this.history = history;
    }

    public String getLiver() {
        return liver;
    }

    public void setLiver(String liver) {
        this.liver = liver;
    }

    public String getKidney() {
        return kidney;
    }

    public void setKidney(String kidney) {
        this.kidney = kidney;
    }
}
