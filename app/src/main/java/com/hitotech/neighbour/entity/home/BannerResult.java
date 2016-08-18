package com.hitotech.neighbour.entity.home;

/**
 * Created by Lv on 2016/5/24.
 */
public class BannerResult {

    private int code;

    private String msg;

    private BannerList result;

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

    public BannerList getResult() {
        return result;
    }

    public void setResult(BannerList result) {
        this.result = result;
    }
}
