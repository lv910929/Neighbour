package com.hitotech.neighbour.entity;

import java.io.Serializable;

/**
 * Created by Lv on 2016/6/1.
 */
public class UpdateResult implements Serializable{

    private int code;

    private String msg;

    private UpdateBean result;

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

    public UpdateBean getResult() {
        return result;
    }

    public void setResult(UpdateBean result) {
        this.result = result;
    }
}
