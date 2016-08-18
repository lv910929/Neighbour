package com.hitotech.neighbour.http;

import android.content.Context;
import android.os.Build;

import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.data.Constant;
import com.hitotech.neighbour.data.NeighbourConfig;
import com.hitotech.neighbour.entity.UpdateResult;
import com.hitotech.neighbour.entity.home.BannerResult;
import com.hitotech.neighbour.entity.home.UrlResult;
import com.hitotech.neighbour.utils.AppUtil;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Lv on 2016/5/24.
 */
public class HomeApi {

    //检测版本
    public static Call<UpdateResult> checkUpdate(final CommCallBack commCallBack, Context context) {
        String versionName = AppUtil.getVersionName(context);
        String version_release = Build.VERSION.RELEASE; // 设备的系统版本
        Call<UpdateResult> updateResultCall = BuildApi.getAPIService().checkVersion(Constant.AUTH_KEY, versionName, NeighbourConfig.PLATFORM_TYPE, version_release);
        updateResultCall.enqueue(new Callback<UpdateResult>() {
            @Override
            public void onResponse(Response<UpdateResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    commCallBack.onSuccess(response.body());
                } else {
                    commCallBack.onFail(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                commCallBack.onFail(null);
            }
        });
        return updateResultCall;
    }

    //获取url配置数据
    public static Call<UrlResult> getUrlData(final OtherCallBack otherCallBack, final int what) {
        Call<UrlResult> urlResultCall = BuildApi.getAPIService().getUrlData(Constant.AUTH_KEY, NeighbourApplication.user_id, NeighbourApplication.token);
        urlResultCall.enqueue(new Callback<UrlResult>() {
            @Override
            public void onResponse(Response<UrlResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    otherCallBack.onSuccess(what, response.body());
                } else {
                    otherCallBack.onFail(what, response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                otherCallBack.onFail(what, null);
            }
        });
        return urlResultCall;
    }

    //查询广告
    public static Call<BannerResult> homeRequest(final OtherCallBack otherCallBack, final int what) {
        Call<BannerResult> bannerResultCall = BuildApi.getAPIService().homeRequest(Constant.AUTH_KEY, NeighbourApplication.user_id, NeighbourApplication.token);
        bannerResultCall.enqueue(new Callback<BannerResult>() {
            @Override
            public void onResponse(Response<BannerResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    otherCallBack.onSuccess(what, response.body());
                } else {
                    otherCallBack.onFail(what, response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                otherCallBack.onFail(what, null);
            }
        });
        return bannerResultCall;
    }
}
