package com.example.HealthyCampus.common.network.vo;

public class NoticeVo {
    private String id;
    private String create_time;
    private String content;
    private String sender;
    private String status;
    private String noticeType;

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

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "NoticeVo{" +
                "create_time='" + create_time + '\'' +
                ", content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
