package com.hitotech.neighbour.entity;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/14.
 */
public class MineItem implements Serializable {

    private int id;

    private int resId;

    private String title;

    private String content;

    private int notifyNum;

    private String url;

    public MineItem(int id, int resId, String title, String content,String url) {
        this.id = id;
        this.resId = resId;
        this.title = title;
        this.content = content;
        this.url = url;
        notifyNum = 0;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getResId() {
        return resId;
    }

    public void setResId(int resId) {
        this.resId = resId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getNotifyNum() {
        return notifyNum;
    }

    public void setNotifyNum(int notifyNum) {
        this.notifyNum = notifyNum;
    }
}
