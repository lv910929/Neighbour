package com.hitotech.neighbour.utils;

import android.app.Application;
import android.content.Context;
import android.os.Build;
import android.os.Environment;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 * Created by Lv on 2016/6/1.
 */
public class DeviceManager {

    public static DeviceManager instance;
    private Context context;
    private String deviceId;
    private int screenWidth;
    private int screenHeigh;
    private final String DEVICEID_DIR = "Local.System";
    private final String CONFIG = "config";

    public static DeviceManager instance(Application app) {
        if (instance == null) {
            instance = new DeviceManager(app);
        }

        return instance;
    }

    private DeviceManager(Context context) {
        this.context = context;
    }

    public String deviceID() {
        if (!TextUtils.isEmpty(this.deviceId)) {
            return this.deviceId;
        } else {
            this.deviceId = this.getCacheDeviceId();
            if (TextUtils.isEmpty(this.deviceId)) {
                this.deviceId = this.geneDeviceId();
                this.cacheDeviceId(this.deviceId);
            }

            return this.deviceId;
        }
    }

    private String getCacheDeviceId() {
        File file = null;
        String deviceId = null;
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            file = Environment.getExternalStorageDirectory();
        } else {
            file = this.context.getFilesDir();
        }

        file = new File(new File(file, "Local.System"), "config");
        if (file.exists()) {
            try {
                FileInputStream e = new FileInputStream(file);
                byte[] buf = new byte[1024];
                int l = e.read(buf);
                e.close();
                deviceId = new String(buf, 0, l, "UTF-8");
            } catch (Exception var7) {

            }
        }

        return deviceId;
    }

    private String geneDeviceId() {
        StringBuilder sb = new StringBuilder();
        sb.append("a_");

        String deviceIdentity;
        try {
            TelephonyManager androidId = (TelephonyManager) this.context.getSystemService(Context.TELEPHONY_SERVICE);
            if (androidId != null) {
                deviceIdentity = androidId.getDeviceId();
                if (TextUtils.isEmpty(deviceIdentity)) {
                    sb.append("NullImei");
                } else {
                    sb.append(deviceIdentity);
                }

                sb.append("_");
            }
        } catch (Exception var4) {

        }

        String androidId1 = Settings.Secure.getString(this.context.getContentResolver(), "android_id");
        if (TextUtils.isEmpty(androidId1)) {
            sb.append("NullAndroidId");
        } else {
            sb.append(androidId1);
        }

        sb.append("_");
        deviceIdentity = Build.VERSION.RELEASE + "_" + Build.MODEL + "_" + Build.BRAND;
        sb.append(deviceIdentity);
        return sb.toString();
    }

    private void cacheDeviceId(String deviceId) {
        File file = null;
        String state = Environment.getExternalStorageState();
        if ("mounted".equals(state)) {
            file = Environment.getExternalStorageDirectory();
        } else {
            file = this.context.getFilesDir();
        }

        file = new File(file, "Local.System");
        if (!file.exists() && !file.mkdir()) {

        } else {
            file = new File(file, "config");

            try {
                FileOutputStream e = new FileOutputStream(file);
                e.write(deviceId.getBytes("UTF-8"));
                e.close();
            } catch (Exception var6) {

            }
        }
    }

    public static int getScreenWidth(Context context) {
        int screenWidth = 0;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        screenWidth = dm.widthPixels;
        return screenWidth;
    }

    public int getScreenHeight(Context context) {
        int screenHeigh = 0;
        DisplayMetrics dm = new DisplayMetrics();
        WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        manager.getDefaultDisplay().getMetrics(dm);
        screenHeigh = dm.heightPixels;
        return screenHeigh;
    }

}
