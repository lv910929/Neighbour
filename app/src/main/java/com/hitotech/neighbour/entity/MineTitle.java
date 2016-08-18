package com.hitotech.neighbour.entity;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Lv on 2016/5/14.
 */
public class MineTitle implements Serializable{

    private int id;

    private String title;

    private List<MineItem> mineItems;

    public MineTitle(int id, String title, List<MineItem> mineItems) {
        this.id = id;
        this.title = title;
        this.mineItems = mineItems;
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

    public List<MineItem> getMineItems() {
        return mineItems;
    }

    public void setMineItems(List<MineItem> mineItems) {
        this.mineItems = mineItems;
    }
}
