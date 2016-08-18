package com.hitotech.neighbour.utils;

import android.text.TextUtils;

import com.hitotech.neighbour.app.NeighbourApplication;
import com.socks.library.KLog;

import java.util.Set;

import cn.jpush.android.api.JPushInterface;
import cn.jpush.android.api.TagAliasCallback;

public class PushManager {

    private static final String PREF_PUSH = "com.hitotech.neighbour.PushManager";

    public static void setAlias(String alias) {

        if (TextUtils.isEmpty(alias)) {
            return;
        }
        JPushInterface.setAlias(NeighbourApplication.getInstance(), alias, new TagAliasCallback() {

            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) { //0表示注册成功
                    KLog.e("JPush注册Alias成功");
                } else {
                    KLog.e("JPush注册Alias失败");
                }
            }

        });

    }

    public static void removeAlias(String alias) {
        JPushInterface.setAlias(NeighbourApplication.getInstance(), alias, new TagAliasCallback() {

            @Override
            public void gotResult(int i, String s, Set<String> set) {
                if (i == 0) { //0表示注册成功
                    KLog.e("JPush注册Alias成功");
                } else {
                    KLog.e("JPush注册Alias失败");
                }
            }
        });
    }

    /**
     * 恢复推送功能
     */
    public static void resumeService() {
        JPushInterface.resumePush(NeighbourApplication.getInstance());
    }

    /**
     * 关闭推送
     */
    public static void stopService() {
        JPushInterface.stopPush(NeighbourApplication.getInstance());
    }

    /**
     * 推送服务是否开启
     *
     * @return
     */
    public static boolean isServiceOn() {
        return !JPushInterface.isPushStopped(NeighbourApplication.getInstance());
    }

}
