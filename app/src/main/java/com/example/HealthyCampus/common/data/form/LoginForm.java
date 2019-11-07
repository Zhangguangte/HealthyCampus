package com.example.HealthyCampus.common.data.form;

/**
 * OK
 */
public class LoginForm {
    public String device_id;
    public String account;
    public String password;

    public LoginForm(String device_id, String account, String password) {
        this.device_id = device_id;
        this.account = account;
        this.password = password;
    }

    public LoginForm() {
    }
}