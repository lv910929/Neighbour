package com.hitotech.neighbour.entity.bind;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lv on 2016/5/22.
 */
public class BindItemResult implements Serializable{

    private int code;

    private String msg;

    private List<BindItem> result;

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

    public List<BindItem> getResult() {
        return result;
    }

    public void setResult(List<BindItem> result) {
        this.result = result;
    }
}
