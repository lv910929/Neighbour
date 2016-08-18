package com.hitotech.neighbour.app;

import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Handler;

import com.hitotech.neighbour.data.WXConstant;
import com.hitotech.neighbour.entity.home.UrlBean;
import com.hitotech.neighbour.entity.member.HouseBean;
import com.squareup.okhttp.OkHttpClient;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Lv on 2016/5/13.
 */
public class NeighbourApplication extends Application {

    public static final int TIMELINE_SUPPORTED_VERSION = 0x21020001;

    private static NeighbourApplication instance;

    public static IWXAPI api;

    private static Handler mHandler;
    //公共的保存信息的地方
    public static SharedPreferences sharedPreferences;
    //用于判断是否登录
    public static boolean hasLogin;
    //用于判断是否绑定
    public static boolean hasBind;
    //蓝牙已连接
    public static boolean blueToothConnected;
    // 用于存放倒计时时间
    public static Map<String, Long> validateTimeMap;

    public static String user_id;

    public static String token;

    public static HouseBean houseBean;

    public static UrlBean urlBean;

    public static boolean isAddHouse;

    public static boolean isMineTab;

    public static NeighbourApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        initBase();
        sharedPreferences = instance.getSharedPreferences("config", Context.MODE_PRIVATE);
        hasLogin = sharedPreferences.getBoolean("hasLogin", false);
        user_id = sharedPreferences.getString("user_id", "");
        token = sharedPreferences.getString("token", "");
        hasBind = sharedPreferences.getBoolean("hasBind", false);
        api = WXAPIFactory.createWXAPI(this, WXConstant.APP_ID);
        NeighbourApplication.api.registerApp(WXConstant.APP_ID);
        isAddHouse = false;
        isMineTab = false;
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
    }

    public static Handler getHandler() {
        if (mHandler == null) {
            mHandler = new Handler();
        }
        return mHandler;
    }

    private void initBase() {
        instance = this;
        mHandler = new Handler();
        blueToothConnected = false;
    }

    public static OkHttpClient defaultOkHttpClient() {
        OkHttpClient client = new OkHttpClient();
        client.setConnectTimeout(10 * 1000, TimeUnit.MILLISECONDS);
        client.setReadTimeout(20 * 1000, TimeUnit.MILLISECONDS);
        client.setWriteTimeout(30 * 1000, TimeUnit.MILLISECONDS);
        return client;
    }

}
