package com.example.HealthyCampus.common.network.vo;

import java.io.Serializable;
import java.util.List;

public class FoodVo implements Serializable {
    private String id;
    private String dishName;
    private String practice;
    private String ingredients;
    private String pictureUrl;
    private String mainProcess;
    private String calorie;
    private String components;

    private String flavor;
    private String productionTime;

    public String getFlavor() {
        return flavor;
    }

    public void setFlavor(String flavor) {
        this.flavor = flavor;
    }

    public String getProductionTime() {
        return productionTime;
    }

    public void setProductionTime(String productionTime) {
        this.productionTime = productionTime;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getMainProcess() {
        return mainProcess;
    }

    public void setMainProcess(String mainProcess) {
        this.mainProcess = mainProcess;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }


    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }

    public String getComponents() {
        return components;
    }

    public void setComponents(String components) {
        this.components = components;
    }

    public String getPractice() {
        return practice;
    }

    public void setPractice(String practice) {
        this.practice = practice;
    }


    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

}
