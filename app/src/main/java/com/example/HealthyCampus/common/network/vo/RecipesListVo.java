package com.example.HealthyCampus.common.network.vo;

import java.util.List;

/**
 * OK
 */
public class RecipesListVo {
    public List<String> classList;
    public List<FoodMenuVo> foodList;
    public List<String> getClassList() {
        return classList;
    }
    public void setClassList(List<String> classList) {
        this.classList = classList;
    }
    public List<FoodMenuVo> getFoodList() {
        return foodList;
    }
    public void setFoodList(List<FoodMenuVo> foodList) {
        this.foodList = foodList;
    }
}
