package com.hitotech.neighbour.wxapi;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.data.WXConstant;
import com.hitotech.neighbour.utils.ImageUtil;
import com.hitotech.neighbour.utils.MD5;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.modelmsg.WXImageObject;
import com.tencent.mm.sdk.modelmsg.WXMediaMessage;
import com.tencent.mm.sdk.modelmsg.WXTextObject;
import com.tencent.mm.sdk.modelmsg.WXWebpageObject;
import com.tencent.mm.sdk.modelpay.PayReq;

import java.util.Map;
import java.util.Random;

/**
 * Created by Lv on 2016/4/3.
 */
public class WXHelper {

    private static final int THUMB_SIZE = 150;
    private static final String SDCARD_ROOT = Environment.getExternalStorageDirectory().getAbsolutePath();

    //检查微信是否安装
    public static boolean isWXAppInstalled() {
        boolean result = false;
        if (NeighbourApplication.api.isWXAppInstalled()) {
            result = true;
        }
        return result;
    }

    //检查微信是否支持
    public static boolean checkWXAppSupport() {
        boolean result = false;
        int wxSdkVersion = NeighbourApplication.api.getWXAppSupportAPI();
        if (wxSdkVersion >= NeighbourApplication.TIMELINE_SUPPORTED_VERSION) {
            result = true;
        }
        return result;
    }

    //检查微信支付是否支持
    public static boolean checkWXPaySupport() {
        boolean result = false;
        int wxSdkVersion = NeighbourApplication.api.getWXAppSupportAPI();
        if (wxSdkVersion >= Build.PAY_SUPPORTED_SDK_INT) {
            result = true;
        }
        return result;
    }

    //调用微信，申请用户授权
    public static void registerToWX() {

        SendAuth.Req req = new SendAuth.Req();
        //授权读取用户信息
        req.scope = "snsapi_userinfo";
        //自定义信息
        req.state = buildTransaction("register");
        //向微信发送请求
        NeighbourApplication.api.sendReq(req);
    }

    /**
     * 发送文本到微信
     */
    public static void sendTextToWX(String text, boolean isWX) {
        // 初始化一个WXTextObject对象
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        // 用WXTextObject对象初始化一个WXMediaMessage对象
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        // 发送文本类型的消息时，title字段不起作用
        // msg.title = "Will be ignored";
        msg.description = text;
        // 构造一个Req
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("text"); // transaction字段用于唯一标识一个请求
        req.message = msg;
        req.scene = isWX ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        // 调用api接口发送数据到微信
        NeighbourApplication.api.sendReq(req);
    }

    /**
     * 发送图片到微信
     */
    public static void sendImgToWX(Context context, boolean isWX) {
        Bitmap bmp = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        WXImageObject imgObj = new WXImageObject(bmp);
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = ImageUtil.bmpToByteArray(thumbBmp, true);  // 设置缩略图
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = isWX ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        NeighbourApplication.api.sendReq(req);
    }

    /**
     * 发送web到微信
     */
    public static void sendWebPageToWX(Context context, String title, String description,String url, boolean isWX) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = description;
        Bitmap thumb = BitmapFactory.decodeResource(context.getResources(), R.mipmap.ic_launcher);
        msg.thumbData = ImageUtil.bmpToByteArray(thumb, true);
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = isWX ? SendMessageToWX.Req.WXSceneSession : SendMessageToWX.Req.WXSceneTimeline;
        NeighbourApplication.api.sendReq(req);
    }

    //发送支付请求
    public static void sendPayReq(Map<String,String> resultunifiedorder){
        PayReq payReq = new PayReq();
        payReq.appId = WXConstant.APP_ID;
        payReq.partnerId = resultunifiedorder.get("partnerId");
        payReq.prepayId = resultunifiedorder.get("prepayId");
        payReq.packageValue = "Sign=WXPay";
        payReq.nonceStr = resultunifiedorder.get("noncestr");
        payReq.timeStamp = resultunifiedorder.get("timestamp");
        payReq.sign = resultunifiedorder.get("sign");
        NeighbourApplication.api.sendReq(payReq);
    }

    private static String genNonceStr() {
        Random random = new Random();
        return MD5.getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }

    private static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    private static String buildTransaction(String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }

}
