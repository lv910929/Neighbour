package com.hitotech.neighbour.callback;

/**
 * Created by Lv on 2016/3/27.
 */
public interface CommCallBack {

    /**
     * 成功的回调
     */
    void onSuccess(Object result);

    /**
     * 失败的回调
     */
    void onFail(Object result);
}
