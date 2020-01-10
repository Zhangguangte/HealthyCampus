package com.example.HealthyCampus.common.data.form;

import com.example.HealthyCampus.common.utils.StringUtil;

public class RegisterFrom {
    private String password;
    private String phone;


    public RegisterFrom(String password, String phone) {
        this.password = password;
        this.phone = phone;
    }

    public RegisterFrom() {
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }



    public boolean validate() {
        return !StringUtil.isEmpty(phone) && !StringUtil.isEmpty(password);
    }
}
