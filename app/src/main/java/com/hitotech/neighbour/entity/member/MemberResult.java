package com.hitotech.neighbour.entity.member;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/24.
 */
public class MemberResult implements Serializable{

    private int code;

    private String msg;

    private MemberBean result;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public MemberBean getResult() {
        return result;
    }

    public void setResult(MemberBean result) {
        this.result = result;
    }
}
