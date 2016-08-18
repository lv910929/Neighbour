package com.hitotech.neighbour.entity.member;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/24.
 */
public class MemberBean implements Serializable {

    private String member_avatar;

    private String member_nickname;

    private String member_house;

    private String credit_total;

    private String service_phone;

    private int order_service_count;

    private int service_count;

    private int cars_count;

    public String getMember_avatar() {
        return member_avatar;
    }

    public void setMember_avatar(String member_avatar) {
        this.member_avatar = member_avatar;
    }

    public String getMember_nickname() {
        return member_nickname;
    }

    public void setMember_nickname(String member_nickname) {
        this.member_nickname = member_nickname;
    }

    public String getMember_house() {
        return member_house;
    }

    public void setMember_house(String member_house) {
        this.member_house = member_house;
    }

    public String getCredit_total() {
        return credit_total;
    }

    public void setCredit_total(String credit_total) {
        this.credit_total = credit_total;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }

    public int getOrder_service_count() {
        return order_service_count;
    }

    public void setOrder_service_count(int order_service_count) {
        this.order_service_count = order_service_count;
    }

    public int getService_count() {
        return service_count;
    }

    public void setService_count(int service_count) {
        this.service_count = service_count;
    }

    public int getCars_count() {
        return cars_count;
    }

    public void setCars_count(int cars_count) {
        this.cars_count = cars_count;
    }

    @Override
    public String toString() {
        return "MemberBean{" +
                "member_nickname='" + member_nickname + '\'' +
                ", member_house='" + member_house + '\'' +
                ", credit_total='" + credit_total + '\'' +
                ", service_phone='" + service_phone + '\'' +
                ", order_service_count=" + order_service_count +
                ", service_count=" + service_count +
                ", cars_count=" + cars_count +
                '}';
    }
}
