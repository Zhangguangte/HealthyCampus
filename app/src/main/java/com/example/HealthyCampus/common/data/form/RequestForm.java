package com.example.HealthyCampus.common.data.form;

import java.util.Map;

/**
 * OK
 */
public class RequestForm {
    public String quest_id;
    public String content;
    public int type;
    public int row;
    public Map<String, String> map;

    public RequestForm(String quest_id) {
        this.quest_id = quest_id;
    }

    public RequestForm(String quest_id, int row) {
        this.quest_id = quest_id;
        this.row = row;
    }

    public RequestForm(int type, String quest_id) {
        this.quest_id = quest_id;
        this.type = type;
    }

    public RequestForm(int type, int row) {
        this.type = type;
        this.row = row;
    }

    public RequestForm(String quest_id, String content) {
        this.quest_id = quest_id;
        this.content = content;
    }

    public RequestForm(String quest_id, String content, int row) {
        this.quest_id = quest_id;
        this.content = content;
        this.row = row;
    }


    public RequestForm(String quest_id, String content, Map<String, String> map) {
        this.quest_id = quest_id;
        this.content = content;
        this.map = map;
    }

    public RequestForm() {
    }
}