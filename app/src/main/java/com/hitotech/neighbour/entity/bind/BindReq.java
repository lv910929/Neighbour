package com.hitotech.neighbour.entity.bind;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/22.
 */
public class BindReq implements Serializable{

    private int house_id;

    private String mobile;

    private int is_owner;

    private String verify_code;

    public BindReq(int house_id, String mobile, int is_owner, String verify_code) {
        this.house_id = house_id;
        this.mobile = mobile;
        this.is_owner = is_owner;
        this.verify_code = verify_code;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public int getIs_owner() {
        return is_owner;
    }

    public void setIs_owner(int is_owner) {
        this.is_owner = is_owner;
    }

    public String getVerify_code() {
        return verify_code;
    }

    public void setVerify_code(String verify_code) {
        this.verify_code = verify_code;
    }
}
