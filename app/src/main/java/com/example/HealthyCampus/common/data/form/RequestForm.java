package com.example.HealthyCampus.common.data.form;

/**
 * OK
 */
public class RequestForm {
    public String quest_id;
    public String content;

    public RequestForm(String quest_id) {
        this.quest_id = quest_id;
    }

    public RequestForm(String quest_id, String content) {
        this.quest_id = quest_id;
        this.content = content;
    }

    public RequestForm() {
    }
}