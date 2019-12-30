package com.example.HealthyCampus.common.network.vo;

import java.util.List;

public class IngredientVo {
    private long log_id;
    private int result_num;
    private List<Ingredient> result;

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

    public List<Ingredient> getResult() {
        return result;
    }

    public void setResult(List<Ingredient> result) {
        this.result = result;
    }

   public class Ingredient {
        String name;
        double score;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }

    @Override
    public String toString() {
        return "IngredientVo{" +
                "log_id=" + log_id +
                ", result_num=" + result_num +
                ", result=" + result +
                '}';
    }
}
