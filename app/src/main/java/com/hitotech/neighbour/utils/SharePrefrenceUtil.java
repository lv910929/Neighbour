package com.hitotech.neighbour.utils;

import android.content.SharedPreferences;

import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.entity.auth.CommResult;
import com.socks.library.KLog;

/**
 * Created by Lv on 2016/5/22.
 */
public class SharePrefrenceUtil {

    public static void saveRegisterToken(CommResult commResult) {
        SharedPreferences.Editor editor = NeighbourApplication.sharedPreferences.edit();
        editor.putString("user_id", commResult.getUser_id() + "");
        editor.putString("token", commResult.getToken());
        editor.commit();
        NeighbourApplication.user_id = commResult.getUser_id() + "";
        NeighbourApplication.token = commResult.getToken();
        KLog.i("user_id-------" + NeighbourApplication.user_id);
        KLog.i("token-------" + NeighbourApplication.token);
    }

    public static void saveLoginToken(CommResult commResult) {
        SharedPreferences.Editor editor = NeighbourApplication.sharedPreferences.edit();
        editor.putString("user_id", commResult.getUser_id() + "");
        editor.putString("token", commResult.getToken());
        editor.putBoolean("hasLogin", true);
        if (commResult.getIs_binding() > 0) {//说明已绑定
            editor.putBoolean("hasBind", true);
            NeighbourApplication.hasBind = true;
        }
        editor.commit();
        NeighbourApplication.hasLogin = true;
        NeighbourApplication.user_id = commResult.getUser_id() + "";
        NeighbourApplication.token = commResult.getToken();
        KLog.i("user_id-------" + NeighbourApplication.user_id);
        KLog.i("token-------" + NeighbourApplication.token);
    }

    public static void saveBindInfo() {
        SharedPreferences.Editor editor = NeighbourApplication.sharedPreferences.edit();
        editor.putBoolean("hasBind", true);
        editor.commit();
        NeighbourApplication.hasBind = true;
    }

    public static void clearLoginToken() {
        SharedPreferences.Editor editor = NeighbourApplication.sharedPreferences.edit();
        editor.putString("user_id", "");
        editor.putString("token", "");
        editor.putBoolean("hasLogin", false);
        editor.putBoolean("hasBind", false);
        editor.commit();
        NeighbourApplication.hasLogin = false;
        NeighbourApplication.hasBind = false;
        NeighbourApplication.user_id = "";
        NeighbourApplication.token = "";
        NeighbourApplication.houseBean = null;
        NeighbourApplication.isAddHouse = false;
    }

}
