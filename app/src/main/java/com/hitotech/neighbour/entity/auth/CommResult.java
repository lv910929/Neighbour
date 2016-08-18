package com.hitotech.neighbour.entity.auth;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/17.
 */
public class CommResult implements Serializable{
    /**
     * user_id : 1
     * token : asdfasdfasdfasdf
     */

    private long user_id;
    private String token;
    private int needsignup;
    private int is_binding;

    public long getUser_id() {
        return user_id;
    }

    public void setUser_id(long user_id) {
        this.user_id = user_id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public int getNeedsignup() {
        return needsignup;
    }

    public void setNeedsignup(int needsignup) {
        this.needsignup = needsignup;
    }

    public int getIs_binding() {
        return is_binding;
    }

    public void setIs_binding(int is_binding) {
        this.is_binding = is_binding;
    }
}
