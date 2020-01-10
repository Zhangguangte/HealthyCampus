package com.example.HealthyCampus.common.network.vo;

import com.example.HealthyCampus.greendao.model.NoticeBean;

public class NoticeVo {
    private Long n_id;      //Green数据库内的ID
    private String id;      //服务器的ID
    private String create_time;
    private String content;
    private String status;
    private String noticeType;

    public NoticeVo(NoticeBean bean) {
        this.id = bean.getN_id();
        this.create_time = bean.getCreate_time();
        this.content = bean.getContent();
        this.status = bean.getStatus();
        this.noticeType = bean.getNoticeType();
        this.n_id = bean.getId();
    }

    public Long getN_id() {
        return n_id;
    }

    public void setN_id(Long n_id) {
        this.n_id = n_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNoticeType() {
        return noticeType;
    }

    public void setNoticeType(String noticeType) {
        this.noticeType = noticeType;
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

}
