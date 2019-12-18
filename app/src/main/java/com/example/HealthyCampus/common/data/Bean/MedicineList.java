package com.example.HealthyCampus.common.data.Bean;

import java.util.List;

public class MedicineList {
    private int total;
    private List<MedicineSummary> tngou;

    public int getTotal() {
        return total;
    }

    public List<MedicineSummary> getTngou() {
        return tngou;
    }

    public class MedicineSummary {
        private int count;
        private String description;
        private int fcount;
        private int id;
        private String img;
        private String keywords;
        private String name;
        private float price;
        private int rcount;
        private String tag;
        private String type;

        public int getCount() {
            return count;
        }

        public String getDescription() {
            return description;
        }

        public int getFcount() {
            return fcount;
        }

        public int getId() {
            return id;
        }

        public String getImg() {
            return img;
        }

        public String getKeywords() {
            return keywords;
        }

        public String getName() {
            return name;
        }

        public float getPrice() {
            return price;
        }

        public int getRcount() {
            return rcount;
        }

        public String getTag() {
            return tag;
        }

        public String getType() {
            return type;
        }

        @Override
        public String toString() {
            return "MedicineSummary{" +
                    "count=" + count +
                    ", description='" + description + '\'' +
                    ", fcount=" + fcount +
                    ", id=" + id +
                    ", img='" + img + '\'' +
                    ", keywords='" + keywords + '\'' +
                    ", name='" + name + '\'' +
                    ", price=" + price +
                    ", rcount=" + rcount +
                    ", tag='" + tag + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }
}
