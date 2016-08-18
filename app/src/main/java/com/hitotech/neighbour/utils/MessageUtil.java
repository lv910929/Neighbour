package com.hitotech.neighbour.utils;

import android.os.Message;

import com.hitotech.neighbour.activity.MainActivity;
import com.hitotech.neighbour.activity.SplashActivity;

/**
 * Created by Lv on 2016/5/27.
 */
public class MessageUtil {

    public static void sendMsgToMain() {
        Message message = MainActivity.handler.obtainMessage(0x001);
        message.sendToTarget();
    }

    public static void returnToMine(){
        Message message = MainActivity.handler.obtainMessage(0x002);
        message.sendToTarget();
    }

    public static void notifyMainFinish(){
        Message message = MainActivity.handler.obtainMessage(0x003);
        message.sendToTarget();
    }

    public static void refreshHouseList(){
        Message message = MainActivity.handler.obtainMessage(0x004);
        message.sendToTarget();
    }

    public static void sendDownloadError(){
        Message message = SplashActivity.handler.obtainMessage(SplashActivity.DOWNLOAD_ERROR);
        message.sendToTarget();
    }
}
