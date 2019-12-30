package com.example.HealthyCampus.common.network.vo;

/**
 * OK
 */
public class DefaultResponseVo {
    public int code;
    public String message;

    public DefaultResponseVo() {
    }

    public DefaultResponseVo(int code, String message) {
        this.code = code;
        this.message = message;
    }

}
