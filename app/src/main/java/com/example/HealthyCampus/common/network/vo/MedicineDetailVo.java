package com.example.HealthyCampus.common.network.vo;

/**
 * 药品详细信息
 */
public class MedicineDetailVo {
    private String id;
    private String image;
    private String detail;
    private String name;
    private String spec;
    private String unit;
    private String price;
    private String zhuzhi;

    public String getZhuzhi() {
        return zhuzhi;
    }

    public void setZhuzhi(String zhuzhi) {
        this.zhuzhi = zhuzhi;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
