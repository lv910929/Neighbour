package com.hitotech.neighbour.entity.auth;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/21.
 */
public class RegRequest implements Serializable{

    private String mobile;

    private String code;

    private String password;

    private String oauthtype;

    private String openid;

    private String unionid;

    public RegRequest() {
        super();
    }

    public RegRequest(String mobile, String code, String password) {
        this.mobile = mobile;
        this.code = code;
        this.password = password;
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

    public String getOauthtype() {
        return oauthtype;
    }

    public void setOauthtype(String oauthtype) {
        this.oauthtype = oauthtype;
    }

    public String getOpenid() {
        return openid;
    }

    public void setOpenid(String openid) {
        this.openid = openid;
    }

    public String getUnionid() {
        return unionid;
    }

    public void setUnionid(String unionid) {
        this.unionid = unionid;
    }
}
