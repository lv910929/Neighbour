package com.hitotech.neighbour.entity.home;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/27.
 */
public class UrlResult implements Serializable {

    /**
     * code : 200
     * msg :
     * result : {"noticeUrl":"http://api.jl.hitotech.cn/notice.html","repairSendUrl":"http://api.jl.hitotech.cn/repair.html","propertyFeeUrl":"http://api.jl.hitotech.cn/property_fee.html","suggestUrl":"http://api.jl.hitotech.cn/suggest.html","visitorUrl":"http://api.jl.hitotech.cn/visitor.html","serviceUrl":"http://api.jl.hitotech.cn/service.html","profileUrl":"http://api.jl.hitotech.cn/profile.html","settingUrl":"http://api.jl.hitotech.cn/setting.html","myCarUrl":"http://api.jl.hitotech.cn/my_car.html","addressUrl":"http://api.jl.hitotech.cn/address.html","balanceUrl":"http://api.jl.hitotech.cn/balance.html","myOrderUrl":"http://api.jl.hitotech.cn/my_order.html","myServiceUrl":"http://api.jl.hitotech.cn/my_service.html","myServiceOrderUrl":"http://api.jl.hitotech.cn/my_service_order.html","incomeUrl":"http://api.jl.hitotech.cn/income.html"}
     */
    private int code;
    private String msg;
    private UrlBean result;

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

    public UrlBean getResult() {
        return result;
    }

    public void setResult(UrlBean result) {
        this.result = result;
    }

}
