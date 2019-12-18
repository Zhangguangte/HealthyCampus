package com.example.HealthyCampus.common.data.Bean;

import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.widgets.chat.ChatStroke;

public class ChatItemBean {

    private boolean isSelf;  //发送者
    private String time;    // 时间
    private int direction;  //方向
    private String nickname;    //昵称
    private String type;
    private String content;
    private String belongId;
    private String file_path;
    private String picture;
    private String sentstatus;

    private String avatar;

    public ChatItemBean() {
        direction = ChatStroke.DIR_RIGHT;
        isSelf = true;
        sentstatus = "SENDING";
    }

    @Override
    public String toString() {
        return "ChatItemBean{" +
                "time='" + time + '\'' +
                ", direction=" + direction +
                ", nickname='" + nickname + '\'' +
                ", type='" + type + '\'' +
                ", content='" + content + '\'' +
                ", file_path='" + file_path + '\'' +
                ", picture='" + picture + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }


    public ChatItemBean(MessageListVo messageListVo, String userid) {
        type = messageListVo.getType();
        content = messageListVo.getContent();
        time = messageListVo.getCreate_time();
        nickname = messageListVo.getAnother_name();
        file_path = messageListVo.getFile_path();
        belongId = messageListVo.getBelongId();
//        direction = !userid.equals(messageListVo.getBelongId()) ? ChatStroke.DIR_LEFT : ChatStroke.DIR_RIGHT;
        isSelf = userid.equals(messageListVo.getBelongId());
        sentstatus = messageListVo.getSentStatus();
    }

    public String getSentstatus() {
        return sentstatus;
    }

    public void setSentstatus(String sentstatus) {
        this.sentstatus = sentstatus;
    }

    public boolean isSelf() {
        return isSelf;
    }

    public void setSelf(boolean self) {
        isSelf = self;
    }

    public String getBelongId() {
        return belongId;
    }

    public void setBelongId(String belongId) {
        this.belongId = belongId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getDirection() {
        return direction;
    }

    public void setDirection(int direction) {
        this.direction = direction;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }


    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
}
