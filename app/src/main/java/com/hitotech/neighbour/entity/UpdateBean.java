package com.hitotech.neighbour.entity;

import java.io.Serializable;

/**
 * Created by Lv on 2016/6/1.
 */
public class UpdateBean implements Serializable{

    /**
     * latest_version : 1.0.0
     * latest_version_message : 修复了一些已知bug.
     * download_url : http://app.hitotech.cn/jl.1.0.0.apk.
     * version_compare_result : -1
     * is_must_update : 0
     */

    private String latest_version;
    private String latest_version_message;
    private String download_url;
    private int version_compare_result;
    private int is_must_update;

    public String getLatest_version() {
        return latest_version;
    }

    public void setLatest_version(String latest_version) {
        this.latest_version = latest_version;
    }

    public String getLatest_version_message() {
        return latest_version_message;
    }

    public void setLatest_version_message(String latest_version_message) {
        this.latest_version_message = latest_version_message;
    }

    public String getDownload_url() {
        return download_url;
    }

    public void setDownload_url(String download_url) {
        this.download_url = download_url;
    }

    public int getVersion_compare_result() {
        return version_compare_result;
    }

    public void setVersion_compare_result(int version_compare_result) {
        this.version_compare_result = version_compare_result;
    }

    public int getIs_must_update() {
        return is_must_update;
    }

    public void setIs_must_update(int is_must_update) {
        this.is_must_update = is_must_update;
    }
}
