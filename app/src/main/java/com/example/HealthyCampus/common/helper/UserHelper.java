package com.example.HealthyCampus.common.helper;

import android.util.Base64;

import com.example.HealthyCampus.common.network.vo.UserVo;


public class UserHelper {


    public static void persistenceUser(UserVo user, String password) {
        SPHelper.setString(SPHelper.USER_ID, user.id);
        SPHelper.setString(SPHelper.ACCOUNT, user.account);
        SPHelper.setString(SPHelper.NICKNAME, user.nickname);
        SPHelper.setString(SPHelper.USER_AVATAR, user.avatar);
        SPHelper.setString(SPHelper.USER_DESCRIPTION, user.description);
        SPHelper.setString(SPHelper.USER_LOCATION, user.location);
        SPHelper.setString(SPHelper.USER_SEX, user.sex);
        SPHelper.setString(SPHelper.USER_PHONE, user.phone);
        SPHelper.setString(SPHelper.USER_BRITHDAY, user.createTime);
        SPHelper.setString(SPHelper.USER_MODIFYTIME, user.lastmodifyTime);
        String authCode = user.account + ":" + password;
        authCode = "Basic " + Base64.encodeToString(authCode.getBytes(), Base64.DEFAULT);
        SPHelper.setString(SPHelper.AUTH_CODE, authCode.trim());
    }


    private static UserHelper instance;

    public static UserHelper getInstance() {
        if (instance == null) {
            instance = new UserHelper();
        }
        return instance;
    }

}
