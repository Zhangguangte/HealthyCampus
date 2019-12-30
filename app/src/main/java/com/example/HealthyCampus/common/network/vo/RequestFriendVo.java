package com.example.HealthyCampus.common.network.vo;

import java.io.Serializable;


public class RequestFriendVo implements Serializable {

    /**
     * Serializable
     */
    private static final long serialVersionUID = 1L;

    public String id;
    public String user_id;
    public String request_user_id;
    public String nickname;
    public String content;
    public String status;
    public String headImg;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getRequest_user_id() {
        return request_user_id;
    }

    public void setRequest_user_id(String request_user_id) {
        this.request_user_id = request_user_id;
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

    @Override
    public String toString() {
        return "RequestFriendVo{" +
                "id='" + id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", request_user_id='" + request_user_id + '\'' +
                ", nickname='" + nickname + '\'' +
                ", content='" + content + '\'' +
                ", status='" + status + '\'' +
                ", headImg='" + headImg + '\'' +
                '}';
    }
}
