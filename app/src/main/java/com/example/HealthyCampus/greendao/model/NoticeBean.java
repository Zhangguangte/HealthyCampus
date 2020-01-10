package com.example.HealthyCampus.greendao.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class NoticeBean {
    @Id(autoincrement = true)
    private Long id;
    private String create_time;
    private String content;
    private String status;
    private String noticeType;
    private String n_id;


    @Generated(hash = 2022474031)
    public NoticeBean(Long id, String create_time, String content, String status,
            String noticeType, String n_id) {
        this.id = id;
        this.create_time = create_time;
        this.content = content;
        this.status = status;
        this.noticeType = noticeType;
        this.n_id = n_id;
    }

    @Generated(hash = 303198189)
    public NoticeBean() {
    }

    public Long getId() {
        return id;
    }

    public String getN_id() {
        return n_id;
    }

    public void setN_id(String n_id) {
        this.n_id = n_id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCreate_time() {
        return create_time;
    }

    public void setCreate_time(String create_time) {
        this.create_time = create_time;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
    }
}
