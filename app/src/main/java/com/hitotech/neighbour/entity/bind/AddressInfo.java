package com.hitotech.neighbour.entity.bind;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/23.
 */
public class AddressInfo implements Serializable{

    private int city_id;

    private int community_id;

    private int building_id;

    private int house_id;

    public AddressInfo() {
        super();
    }

    public AddressInfo(int city_id, int community_id, int building_id, int house_id) {
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

    public int getCommunity_id() {
        return community_id;
    }

    public void setCommunity_id(int community_id) {
        this.community_id = community_id;
    }

    public int getBuilding_id() {
        return building_id;
    }

    public void setBuilding_id(int building_id) {
        this.building_id = building_id;
    }

    public int getHouse_id() {
        return house_id;
    }

    public void setHouse_id(int house_id) {
        this.house_id = house_id;
    }
}
