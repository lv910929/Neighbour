package com.hitotech.neighbour.utils;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.AddressInfoActivity;
import com.hitotech.neighbour.activity.IdentifyInfoActivity;
import com.hitotech.neighbour.activity.LoginActivity;
import com.hitotech.neighbour.activity.MainActivity;
import com.hitotech.neighbour.activity.RegisterActivity;
import com.hitotech.neighbour.activity.ResetPswActivity;
import com.hitotech.neighbour.activity.SelectBuildingActivity;
import com.hitotech.neighbour.activity.SelectCityActivity;
import com.hitotech.neighbour.activity.SelectCommunityActivity;
import com.hitotech.neighbour.activity.SelectHouseActivity;
import com.hitotech.neighbour.activity.WebviewActivity;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.data.Constant;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.entity.member.HouseBean;
import com.socks.library.KLog;

/**
 * Created by Lv on 2016/5/23.
 */
public class IntentUtil {

    public static void redirectLogin(Context context) {
        context.startActivity(new Intent(context, LoginActivity.class));
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectRegister(Context context) {
        context.startActivity(new Intent(context, RegisterActivity.class));
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectAgreement(Context context) {
        Intent intent = new Intent(context, WebviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("loadUrl", URLConstant.COMM_URL + URLConstant.AGREEMENT_URL);
        bundle.putString("title", "用户服务协议");
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectResetPsw(Context context) {
        context.startActivity(new Intent(context, ResetPswActivity.class));
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectMain(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectBind(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, AddressInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectBindWithFinish(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, AddressInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).finish();
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectCity(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, SelectCityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectCommunity(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, SelectCommunityActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectBuilding(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, SelectBuildingActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectHouse(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, SelectHouseActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    public static void redirectIdentify(Context context, HouseBean houseBean) {
        Intent intent = new Intent(context, IdentifyInfoActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("houseBean", houseBean);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    //webview跳转
    public static void redirectWebView(Context context, String title, String loadUrl) {
        Intent intent = new Intent(context, WebviewActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("loadUrl", spliceUrl(loadUrl));
        KLog.i("解析后的url---------" + spliceUrl(loadUrl));
        bundle.putString("title", title);
        intent.putExtras(bundle);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
    }

    private static String spliceUrl(String loadUrl) {
        StringBuilder newUrl = new StringBuilder();
        newUrl.append(loadUrl);
        if (!loadUrl.contains("?")) {
            newUrl.append("?");
        } else if (!loadUrl.endsWith("&")) {
            newUrl.append("&");
        }
        newUrl.append("Auth-Key").append("=").append(Constant.AUTH_KEY);
        newUrl.append("&");
        newUrl.append("Authorization").append("=").append(NeighbourApplication.token);
        newUrl.append("&");
        newUrl.append("User-ID").append("=").append(NeighbourApplication.user_id);
        return newUrl.toString();
    }

}
