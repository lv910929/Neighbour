package com.hitotech.neighbour.entity.bind;

/**
 * Created by Lv on 2016/5/23.
 */
public class BindResult {

    private int code;

    private String msg;

    private BindBean result;

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

    public BindBean getResult() {
        return result;
    }

    public void setResult(BindBean result) {
        this.result = result;
    }
}
