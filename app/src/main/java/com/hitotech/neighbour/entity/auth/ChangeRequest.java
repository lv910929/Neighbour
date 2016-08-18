package com.hitotech.neighbour.entity.auth;

import java.io.Serializable;

/**
 * Created by Lv on 2016/5/22.
 */
public class ChangeRequest implements Serializable{

    private String old_password;

    private String new_password;

    private String new_password_confirm;

    public ChangeRequest(String old_password, String new_password, String new_password_confirm) {
        this.old_password = old_password;
        this.new_password = new_password;
        this.new_password_confirm = new_password_confirm;
    }

    public String getOld_password() {
        return old_password;
    }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public String getNew_password() {
        return new_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public String getNew_password_confirm() {
        return new_password_confirm;
    }

    public void setNew_password_confirm(String new_password_confirm) {
        this.new_password_confirm = new_password_confirm;
    }
}
