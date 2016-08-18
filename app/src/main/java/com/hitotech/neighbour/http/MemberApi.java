package com.hitotech.neighbour.http;

import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.data.Constant;
import com.hitotech.neighbour.entity.member.HouseResult;
import com.hitotech.neighbour.entity.member.MemberResult;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Lv on 2016/5/24.
 */
public class MemberApi {

    public static Call<HouseResult> getHouseInfo(final OtherCallBack otherCallBack, final int what) {
        Call<HouseResult> houseResultCall = BuildApi.getAPIService().getHouseInfo(Constant.AUTH_KEY, NeighbourApplication.user_id, NeighbourApplication.token);
        houseResultCall.enqueue(new Callback<HouseResult>() {
            @Override
            public void onResponse(Response<HouseResult> response, Retrofit retrofit) {
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
        return houseResultCall;
    }

    public static Call<MemberResult> getMemberInfo(final CommCallBack commCallBack) {
        Call<MemberResult> memberResultCall = BuildApi.getAPIService().getMemberInfo(Constant.AUTH_KEY, NeighbourApplication.user_id, NeighbourApplication.token);
        memberResultCall.enqueue(new Callback<MemberResult>() {
            @Override
            public void onResponse(Response<MemberResult> response, Retrofit retrofit) {
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
        return memberResultCall;
    }
}
