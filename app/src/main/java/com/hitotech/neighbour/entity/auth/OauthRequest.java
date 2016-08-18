package com.hitotech.neighbour.entity.auth;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/22.
 */
public class OauthRequest implements Serializable{

    private String oauthtype;

    private String openid;

    private String unionid;

    public OauthRequest(String oauthtype, String openid, String unionid) {
        this.oauthtype = oauthtype;
        this.openid = openid;
        this.unionid = unionid;
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
