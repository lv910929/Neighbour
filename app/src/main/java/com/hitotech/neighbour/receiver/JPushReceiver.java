package com.hitotech.neighbour.receiver;

/**
 * Created by dfqin on 15/9/18.
 */

import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hitotech.neighbour.data.NeighbourConfig;
import com.hitotech.neighbour.utils.SystemUtils;
import com.socks.library.KLog;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

/**
 * 自定义接收器
 * 如果不定义这个 Receiver，则：
 * 1) 默认用户会打开主界面
 * 2) 接收不到自定义消息
 */
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle = intent.getExtras();
        KLog.i("[JPushReceiver] onReceive - " + intent.getAction() + ", extras: " + getBundleStr(bundle));

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            KLog.i("[JPushReceiver] 接收Registration Id : " + regId);
            //sendPushToken(regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            KLog.i("[JPushReceiver] 接收到推送下来的自定义消息: " + bundle.getString(JPushInterface.EXTRA_MESSAGE));
        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            KLog.i("[JPushReceiver] 接收到推送下来的通知");
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            KLog.i("[JPushReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            String data = bundle.getString(JPushInterface.EXTRA_EXTRA);
            try {
                JSONObject obj = new JSONObject(data);
                String url = obj.optString("scheme");
                /*Intent in = LKIntentFactory.geneCommonBuilder(url).build();
                in.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(in);*/
            } catch (JSONException e) {
                e.printStackTrace();
            } catch (ActivityNotFoundException e) {
                e.printStackTrace();
            }

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            if (!SystemUtils.isAppAlive(context, NeighbourConfig.PACKAGE_NAME)) {
                Intent launchIntent = context.getPackageManager().getLaunchIntentForPackage(NeighbourConfig.PACKAGE_NAME);
                launchIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
                context.startActivity(launchIntent);
            }
        } else if (JPushInterface.ACTION_CONNECTION_CHANGE.equals(intent.getAction())) {
            boolean connected = intent.getBooleanExtra(JPushInterface.EXTRA_CONNECTION_CHANGE, false);
            KLog.i("[JPushReceiver]" + intent.getAction() + " connected state change to " + connected);
        } else {
            KLog.i("[JPushReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    // 获取 Bundle 内的数据
    private String getBundleStr(Bundle bundle) {
        StringBuilder sb = new StringBuilder();
        for (String key : bundle.keySet()) {
            if (key.equals(JPushInterface.EXTRA_NOTIFICATION_ID)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getInt(key));
            } else if (key.equals(JPushInterface.EXTRA_CONNECTION_CHANGE)) {
                sb.append("\nkey:" + key + ", value:" + bundle.getBoolean(key));
            } else {
                sb.append("\nkey:" + key + ", value:" + bundle.getString(key));
            }
        }
        return sb.toString();
    }

}

