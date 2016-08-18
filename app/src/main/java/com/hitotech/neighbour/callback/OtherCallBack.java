package com.hitotech.neighbour.callback;

/**
 * Created by Lv on 2016/4/28.
 */
public interface OtherCallBack {

    /**
     * 成功的回调
     */
    void onSuccess(int what, Object result);

    /**
     * 失败的回调
     */
    void onFail(int what, Object result);
}
