package com.hitotech.neighbour.entity.auth;

/**
 * Created by Lv on 2016/5/22.
 */
public class LoginRequest {

    private String mobile;

    private String password;

    public LoginRequest(String mobile, String password) {
        this.mobile = mobile;
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
