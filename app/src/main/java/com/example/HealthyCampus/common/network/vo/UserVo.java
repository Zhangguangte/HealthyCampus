package com.example.HealthyCampus.common.network.vo;

import com.example.HealthyCampus.dao.Friend;

/**
 * OK
 */
public class UserVo {
    public String id;
    public String username;
    public String nickname;
    public String avatar;
    public String description;
    public String location;
    public String sex;
    public String phone;

    public UserVo() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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

    @Override
    public String toString() {
        return "UserVo{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", avatar='" + avatar + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", sex='" + sex + '\'' +
                '}';
    }

    public Friend generateFriend() {
        Friend friend = new Friend();
        friend.setUid(this.id);
        friend.setUsername(this.username);
        friend.setNickname(this.nickname);
        friend.setAvatar(this.avatar);
        friend.setDescription(this.description);
        friend.setSex(this.sex);
        friend.setLocation(this.location);
        return friend;
    }

    public Friend fixFriend(Friend friend) {
        friend.setUsername(this.username);
        friend.setNickname(this.nickname);
        friend.setAvatar(this.avatar);
        friend.setDescription(this.description);
        friend.setSex(this.sex);
        friend.setLocation(this.location);
        return friend;
    }
}
