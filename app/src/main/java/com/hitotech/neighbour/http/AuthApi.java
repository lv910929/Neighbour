package com.hitotech.neighbour.http;

import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.data.Constant;
import com.hitotech.neighbour.entity.auth.AuthResult;
import com.hitotech.neighbour.entity.auth.LoginRequest;
import com.hitotech.neighbour.entity.auth.RegRequest;
import com.hitotech.neighbour.entity.auth.ResetRequest;
import com.hitotech.neighbour.entity.auth.SmsRequest;

import retrofit.Call;
import retrofit.Callback;
import retrofit.Response;
import retrofit.Retrofit;

/**
 * Created by Lv on 2016/5/22.
 */
public class AuthApi {

    //获取验证码
    public static Call<AuthResult> sendSms(final OtherCallBack otherCallBack, final int what, String mobile) {
        SmsRequest smsRequest = new SmsRequest(mobile);
        Call<AuthResult> authResultCall = BuildApi.getAPIService().sendSms(Constant.AUTH_KEY, smsRequest);
        authResultCall.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Response<AuthResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    otherCallBack.onSuccess(what, response.body());
                } else {
                    otherCallBack.onFail(what, response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                otherCallBack.onFail(what, t.getMessage());
            }
        });
        return authResultCall;
    }

    //注册
    public static Call<AuthResult> register(final OtherCallBack otherCallBack, final int what, RegRequest regRequest) {
        Call<AuthResult> authResultCall = BuildApi.getAPIService().register(Constant.AUTH_KEY, regRequest);
        authResultCall.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Response<AuthResult> response, Retrofit retrofit) {
                if (response.isSuccess()) {
                    otherCallBack.onSuccess(what, response.body());
                } else {
                    otherCallBack.onFail(what, response.body());
                }
            }

            @Override
            public void onFailure(Throwable t) {
                AuthResult authResult = null;
                otherCallBack.onFail(what, authResult);
            }
        });
        return authResultCall;
    }

    //登录
    public static Call<AuthResult> login(final CommCallBack commCallBack, LoginRequest loginRequest) {
        Call<AuthResult> authResultCall = BuildApi.getAPIService().login(Constant.AUTH_KEY, loginRequest);
        authResultCall.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Response<AuthResult> response, Retrofit retrofit) {
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
        return authResultCall;
    }

    //重置密码
    public static Call<AuthResult> resetPassword(final OtherCallBack otherCallBack, final int what,ResetRequest resetRequest){
        Call<AuthResult> authResultCall = BuildApi.getAPIService().resetPassword(Constant.AUTH_KEY,resetRequest);
        authResultCall.enqueue(new Callback<AuthResult>() {
            @Override
            public void onResponse(Response<AuthResult> response, Retrofit retrofit) {
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
        return authResultCall;
    }
}
