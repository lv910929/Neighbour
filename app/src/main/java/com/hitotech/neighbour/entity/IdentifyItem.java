package com.hitotech.neighbour.entity;

/**
 * Created by Lv on 2016/5/18.
 */
public class IdentifyItem {

    private int id;

    private String title;

    public IdentifyItem(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
