package com.example.HealthyCampus.common.network.vo;

import java.io.Serializable;

public class IngredientResultVo  implements Serializable {
    private String name;
    private String synopsis;
    private String nutritive;
    private String effect;
    private String url;
    private String calorie;
    private String components;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public String getNutritive() {
        return nutritive;
    }

    public void setNutritive(String nutritive) {
        this.nutritive = nutritive;
    }

    public String getEffect() {
        return effect;
    }

    public void setEffect(String effect) {
        this.effect = effect;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
}
