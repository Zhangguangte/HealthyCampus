package com.example.HealthyCampus.common.network.vo;

public class MedicineListVo {
    private String id;
    private String price;
    private String goodName;
    private String description;
    private String isOct;    //非处方药
    private String image;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String goodName) {
        this.goodName = goodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIsOct() {
        return isOct;
    }

    public void setIsOct(String isOct) {
        this.isOct = isOct;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "MedicineListVo{" +
                "price='" + price + '\'' +
                ", goodName='" + goodName + '\'' +
                ", description='" + description + '\'' +
                ", isOct='" + isOct + '\'' +
                ", image='" + image + '\'' +
                '}';
    }
}
