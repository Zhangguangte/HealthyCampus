package com.example.HealthyCampus.common.network.vo;

public class FoodRecommendVo {
    private String id;
    private String picture;
    private String title;
    private String description;

    private int mold;

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

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
