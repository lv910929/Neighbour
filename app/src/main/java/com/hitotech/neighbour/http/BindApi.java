package com.hitotech.neighbour.http;

import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.data.Constant;
import com.hitotech.neighbour.entity.bind.BindItemResult;
import com.hitotech.neighbour.entity.bind.BindReq;
import com.hitotech.neighbour.entity.bind.BindResult;
import com.hitotech.neighbour.entity.bind.MobileResult;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Lv on 2016/5/23.
 */
public class BindApi {

    public static Call<BindItemResult> getCityList(final CommCallBack commCallBack) {
        Call<BindItemResult> cityResultCall = BuildApi.getAPIService().getCities(Constant.AUTH_KEY, NeighbourApplication.user_id, NeighbourApplication.token);
        cityResultCall.enqueue(new Callback<BindItemResult>() {
            @Override
            public void onResponse(Response<BindItemResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    commCallBack.onSuccess(response.body());
                } else {
                    commCallBack.onFail(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                BindItemResult cityResult = null;
                commCallBack.onFail(cityResult);
            }
        });
        return cityResultCall;
    }

    public static Call<BindItemResult> getCommunityList(final CommCallBack commCallBack, int cityId) {
        Call<BindItemResult> bindResultCall = BuildApi.getAPIService().getCommunities(Constant.AUTH_KEY,
                NeighbourApplication.user_id, NeighbourApplication.token, cityId);
        bindResultCall.enqueue(new Callback<BindItemResult>() {
            @Override
            public void onResponse(Response<BindItemResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    commCallBack.onSuccess(response.body());
                } else {
                    commCallBack.onFail(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                BindItemResult bindResult = null;
                commCallBack.onFail(bindResult);
            }
        });
        return bindResultCall;
    }

    public static Call<BindItemResult> getBuildingList(final CommCallBack commCallBack, int communityId) {
        Call<BindItemResult> bindResultCall = BuildApi.getAPIService().getBuildings(Constant.AUTH_KEY,
                NeighbourApplication.user_id, NeighbourApplication.token, communityId);
        bindResultCall.enqueue(new Callback<BindItemResult>() {
            @Override
            public void onResponse(Response<BindItemResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    commCallBack.onSuccess(response.body());
                } else {
                    commCallBack.onFail(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                BindItemResult bindResult = null;
                commCallBack.onFail(bindResult);
            }
        });
        return bindResultCall;
    }

    public static Call<BindItemResult> getHouseList(final CommCallBack commCallBack, int buildingId) {
        Call<BindItemResult> bindResultCall = BuildApi.getAPIService().getHouses(Constant.AUTH_KEY,
                NeighbourApplication.user_id, NeighbourApplication.token, buildingId);
        bindResultCall.enqueue(new Callback<BindItemResult>() {
            @Override
            public void onResponse(Response<BindItemResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    commCallBack.onSuccess(response.body());
                } else {
                    commCallBack.onFail(response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                BindItemResult bindResult = null;
                commCallBack.onFail(bindResult);
            }
        });
        return bindResultCall;
    }

    public static Call<MobileResult> getMobile(final OtherCallBack otherCallBack, final int what, int houseId) {
        Call<MobileResult> mobileResultCall = BuildApi.getAPIService().getMobile(Constant.AUTH_KEY,
                NeighbourApplication.user_id, NeighbourApplication.token, houseId);
        mobileResultCall.enqueue(new Callback<MobileResult>() {
            @Override
            public void onResponse(Response<MobileResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    otherCallBack.onSuccess(what, response.body());
                } else {
                    otherCallBack.onFail(what, response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                MobileResult mobileResult = null;
                otherCallBack.onFail(what, mobileResult);
            }
        });
        return mobileResultCall;
    }

    public static Call<BindResult> bindRequest(final OtherCallBack otherCallBack, final int what, BindReq bindReq) {
        Call<BindResult> bindResultCall = BuildApi.getAPIService().bindRequest(Constant.AUTH_KEY,
                NeighbourApplication.user_id, NeighbourApplication.token, bindReq);
        bindResultCall.enqueue(new Callback<BindResult>() {
            @Override
            public void onResponse(Response<BindResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    otherCallBack.onSuccess(what, response.body());
                } else {
                    otherCallBack.onFail(what, response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                BindResult bindResult = null;
                otherCallBack.onFail(what, bindResult);
            }
        });
        return bindResultCall;
    }
}
