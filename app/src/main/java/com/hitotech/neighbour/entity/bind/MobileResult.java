package com.hitotech.neighbour.entity.bind;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/22.
 */
public class MobileResult implements Serializable{

    private int code;

    private String msg;

    private Mobile result;

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

    public Mobile getResult() {
        return result;
    }

    public void setResult(Mobile result) {
        this.result = result;
    }
}
