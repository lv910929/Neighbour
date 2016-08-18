package com.hitotech.neighbour.webview;

import android.app.Activity;
import android.content.Context;
import android.os.Message;
import android.webkit.JavascriptInterface;

import com.alipay.sdk.app.PayTask;
import com.google.gson.JsonObject;
import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.WebviewActivity;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MessageUtil;
import com.hitotech.neighbour.utils.PushManager;
import com.hitotech.neighbour.utils.SharePrefrenceUtil;
import com.hitotech.neighbour.utils.StringUtil;
import com.hitotech.neighbour.utils.UrlParseUtil;
import com.hitotech.neighbour.wxapi.WXHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Lv on 2016/4/3.
 */
public class JavaScriptObject {

    Context mContext;

    public JavaScriptObject(Context mContext) {
        this.mContext = mContext;
    }

    //跳转到下一个webview
    @JavascriptInterface
    public void pushcontroller(String urlString) {
        String title = "";
        String loadUrl = "";
        Map<String, String> urlMap = UrlParseUtil.parseUrl(urlString);
        title = urlMap.get("title");
        loadUrl = UrlParseUtil.spliceUrl(urlMap);
        IntentUtil.redirectWebView(mContext, title, loadUrl);
    }

    //退回我的界面
    @JavascriptInterface
    public void popcontroller() {
        ((Activity) mContext).finish();
        ((Activity) mContext).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    //切换住址
    @JavascriptInterface
    public void changeaddress() {
        IntentUtil.redirectMain(mContext);
        MessageUtil.sendMsgToMain();
    }

    //新增住址
    @JavascriptInterface
    public void addhousinfo() {
        IntentUtil.redirectBind(mContext, NeighbourApplication.houseBean);
    }

    //退出登录
    @JavascriptInterface
    public void logout() {
        PushManager.removeAlias(null);
        SharePrefrenceUtil.clearLoginToken();
        MessageUtil.notifyMainFinish();
        IntentUtil.redirectLogin(mContext);
    }

    /*
     * JS调用android的实现微信分享
     * */
    @JavascriptInterface
    public void shareaction(String shareMsg) {
        Map<String, String> shareMap = parseShareMsg(shareMsg);
        String title = shareMap.get("title");
        String content = shareMap.get("content");
        String url = shareMap.get("url");
        if (WXHelper.isWXAppInstalled()) {
            if (WXHelper.checkWXAppSupport()) {
                DialogUtil.showShareDialog(mContext, title, content, url);
            } else {
                DialogUtil.showInfoDialog(mContext, "提示", "抱歉，您的微信不支持分享功能，请先升级");
            }
        } else {
            DialogUtil.showInfoDialog(mContext, "提示", "您尚未安装微信,请先安装微信");
        }
    }

    private Map<String, String> parseShareMsg(String shareMsg) {

        Map<String, String> shareMap = new HashMap<>();
        if (!shareMsg.equals("")) {
            String[] shareStrings = shareMsg.split("&");
            String[] titleStrings = shareStrings[0].split("=");
            shareMap.put("title", StringUtil.decodeString(titleStrings[1]));
            String[] contentStrings = shareStrings[1].split("=");
            shareMap.put("content", StringUtil.decodeString(contentStrings[1]));
            String[] urlStrings = shareStrings[2].split("=");
            shareMap.put("url", StringUtil.decodeString(urlStrings[1]));
        }
        return shareMap;
    }

    //解析js传来的微信支付字符串
    private Map<String, String> parsePayMap(String payContent) {
        Map<String, String> payMap = new HashMap<>();
        if (!payContent.equals("") && payContent.contains("&")) {
            String[] payParameters = payContent.split("&");
            for (int i = 0; i < payParameters.length; i++) {
                String[] payParameterDetails = payParameters[i].split("=");
                payMap.put(payParameterDetails[0], payParameterDetails[1]);
            }
        }
        return payMap;
    }

    //支付宝支付
    public void alipayaction(final String payContent) {

        Runnable payRunnable = new Runnable() {
            @Override
            public void run() {
                PayTask alipay = new PayTask((Activity) mContext);
                String result = alipay.pay(payContent, true);
                Message msg = WebviewActivity.handler.obtainMessage();
                msg.what = WebviewActivity.ALI_PAY_FLAG;
                msg.obj = result;
                msg.sendToTarget();
            }
        };
        // 必须异步调用
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    //微信支付方法
    @JavascriptInterface
    public void payaction(String payContent) {
        if (WXHelper.isWXAppInstalled()) {
            if (WXHelper.checkWXPaySupport()) {
                Map<String, String> payMap = parsePayMap(payContent);
                WXHelper.sendPayReq(payMap);
            } else {
                DialogUtil.showInfoDialog(mContext, "提示", "抱歉，您的微信不支持支付功能，请先升级");
            }
        } else {
            DialogUtil.showInfoDialog(mContext, "提示", "您尚未安装微信,请先安装微信");
        }
    }

    //微信支付回调
    public JsonObject payBack(int resultCode) {
        JsonObject jsonObject = new JsonObject();
        if (resultCode == 1) {
            jsonObject.addProperty("code", "success");
        } else {
            jsonObject.addProperty("code", "error");
        }
        return jsonObject;
    }

}
