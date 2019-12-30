package com.example.HealthyCampus.common.network.vo;

import java.util.ArrayList;
import java.util.List;

public class DishVo {
    private long log_id;
    private int result_num;
    private List<Dish> result;

    public long getLog_id() {
        return log_id;
    }

    public void setLog_id(long log_id) {
        this.log_id = log_id;
    }

    public int getResult_num() {
        return result_num;
    }

    public void setResult_num(int result_num) {
        this.result_num = result_num;
    }

    public List<Dish> getResult() {
        return result;
    }

    public void setResult(List result) {
        this.result = result;
    }

    public class Dish {
        String name;
        float calorie;
        float probability;
        Object baike_info;
        String baike_url;
        String image_url;
        String description;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public float getCalorie() {
            return calorie;
        }

        public void setCalorie(float calorie) {
            this.calorie = calorie;
        }

        public float getProbability() {
            return probability;
        }

        public void setProbability(float probability) {
            this.probability = probability;
        }

        public Object getBaike_info() {
            return baike_info;
        }

        public void setBaike_info(Object baike_info) {
            this.baike_info = baike_info;
        }

        public String getBaike_url() {
            return baike_url;
        }

        public void setBaike_url(String baike_url) {
            this.baike_url = baike_url;
        }

        public String getImage_url() {
            return image_url;
        }

        public void setImage_url(String image_url) {
            this.image_url = image_url;
        }

        public String getDescription() {
            return description;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        @Override
        public String toString() {
            return "Dish{" +
                    "name='" + name + '\'' +
                    ", calorie=" + calorie +
                    ", probability=" + probability +
                    ", baike_info=" + baike_info +
                    ", baike_url='" + baike_url + '\'' +
                    ", image_url='" + image_url + '\'' +
                    ", description='" + description + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "DishVo{" +
                "log_id=" + log_id +
                ", result_num=" + result_num +
                ", result=" + result +
                '}';
    }
}
