package com.hitotech.neighbour.entity.auth;

/**
 * Created by Lv on 2016/5/22.
 */
public class SmsRequest {

    private String mobile;

    public SmsRequest(String mobile) {
        this.mobile = mobile;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }
}
