package com.hitotech.neighbour.entity.home;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/24.
 */
public class Banner implements Serializable{

    /**
     * id : 1
     * title : 家和万事兴1
     * thumb : http://apijl.hondapark.cn/data/attachment/2016/05-18/W6N1PXcddHpLNjI6WasoVlekw3MeMl5qFl.jpg
     * description : 家和万事兴1家和万事兴1
     * url : http://www.baidu.com
     * created_at : 2016-05-08 12:00:00
     */

    private int id;
    private String title;
    private String thumb;
    private String description;
    private String url;
    private String created_at;

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

    public String getThumb() {
        return thumb;
    }

    public void setThumb(String thumb) {
        this.thumb = thumb;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
