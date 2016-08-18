package com.hitotech.neighbour.entity.auth;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/22.
 */
public class ResetRequest implements Serializable{

    private String mobile;

    private String code;

    private String password;

    private String password_confirm;

    public ResetRequest(String mobile, String code, String password, String password_confirm) {
        this.mobile = mobile;
        this.code = code;
        this.password = password;
        this.password_confirm = password_confirm;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword_confirm() {
        return password_confirm;
    }

    public void setPassword_confirm(String password_confirm) {
        this.password_confirm = password_confirm;
    }
}
