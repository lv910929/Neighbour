package com.hitotech.neighbour.entity.member;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/24.
 */
public class HouseBean implements Serializable{
    /**
     * city_id : 1
     * city_name : 常州
     * community_id : 1
     * community_name : 御城
     * building_id : 9
     * building_name : 2
     * house_id : 590
     * house_name : 乙单元1004
     */

    private int city_id;
    private String city_name;
    private int community_id;
    private String community_name;
    private int building_id;
    private String building_name;
    private int house_id;
    private String house_name;
    private String service_phone;//物业服务电话

    public HouseBean() {
    }

    public HouseBean(int city_id, int community_id, int building_id, int house_id) {
        this.city_id = city_id;
        this.community_id = community_id;
        this.building_id = building_id;
        this.house_id = house_id;
    }

    public int getCity_id() {
        return city_id;
    }

    public void setCity_id(int city_id) {
        this.city_id = city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public String getCommunity_name() {
        return community_name;
    }

    public void setCommunity_name(String community_name) {
        this.community_name = community_name;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public String getBuilding_name() {
        return building_name;
    }

    public void setBuilding_name(String building_name) {
        this.building_name = building_name;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }

    public String getHouse_name() {
        return house_name;
    }

    public void setHouse_name(String house_name) {
        this.house_name = house_name;
    }

    public String getService_phone() {
        return service_phone;
    }

    public void setService_phone(String service_phone) {
        this.service_phone = service_phone;
    }
}
