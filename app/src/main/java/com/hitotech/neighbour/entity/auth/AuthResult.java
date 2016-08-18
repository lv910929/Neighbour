package com.hitotech.neighbour.entity.auth;

/**
 * Created by Lv on 2016/5/17.
 */
public class AuthResult {

    private int code;
    private String msg;
    private CommResult result;

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

    public CommResult getResult() {
        return result;
    }

    public void setResult(CommResult result) {
        this.result = result;
    }
}
