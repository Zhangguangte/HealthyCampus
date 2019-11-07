package com.example.HealthyCampus.common.network.vo;

import com.example.HealthyCampus.dao.Friend;

import java.io.Serializable;
import java.util.Date;


public class UserVo implements Serializable {

    public String id;
    public String account;
    public String nickname;
    public String avatar;
    public String description;
    public String sex;
    public String location;
    public String phone;
    public String createTime;
    public String lastmodifyTime;
    public String initials;
    public boolean isfriends;

    public UserVo() {
    }

    public String getInitials() {
        return initials;
    }

    public void setInitials(String initials) {
        this.initials = initials;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }


    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getLastmodifyTime() {
        return lastmodifyTime;
    }

    public void setLastmodifyTime(String lastmodifyTime) {
        this.lastmodifyTime = lastmodifyTime;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public boolean isIsfriends() {
        return isfriends;
    }

    public void setIsfriends(boolean isfriends) {
        this.isfriends = isfriends;
    }

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", account='" + account + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", createTime=" + createTime +
                ", lastmodifyTime=" + lastmodifyTime +
                ", isfriends=" + isfriends +
                '}';
    }
}
