package com.hitotech.neighbour.entity.bind;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/23.
 */
public class BindItem implements Serializable{

    private int id;

    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
