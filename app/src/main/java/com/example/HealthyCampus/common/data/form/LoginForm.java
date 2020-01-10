package com.example.HealthyCampus.common.data.form;

/**
 * OK
 */
public class LoginForm {
    private String device_id;
    private String account;
    private String password;

    public LoginForm(String device_id, String account, String password) {
        this.device_id = device_id;
        this.account = account;
        this.password = password;
    }

    public LoginForm() {
    }

    public String getDevice_id() {
        return device_id;
    }

    public void setDevice_id(String device_id) {
        this.device_id = device_id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}