package com.example.HealthyCampus.common.network.vo;

public class FoodMenuVo {
    private String id;
    private String pictureUrl;
    private String dishName;
    private String calorie;
    private String type;

    private int mold;       //标题，内容

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getMold() {
        return mold;
    }

    public void setMold(int mold) {
        this.mold = mold;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getCalorie() {
        return calorie;
    }

    public void setCalorie(String calorie) {
        this.calorie = calorie;
    }
}
