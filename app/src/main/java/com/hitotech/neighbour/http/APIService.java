package com.hitotech.neighbour.http;

import com.hitotech.neighbour.data.Constant;
import com.hitotech.neighbour.entity.UpdateResult;
import com.hitotech.neighbour.entity.auth.AuthResult;
import com.hitotech.neighbour.entity.auth.ChangeRequest;
import com.hitotech.neighbour.entity.auth.LoginRequest;
import com.hitotech.neighbour.entity.auth.OauthRequest;
import com.hitotech.neighbour.entity.auth.RegRequest;
import com.hitotech.neighbour.entity.auth.ResetRequest;
import com.hitotech.neighbour.entity.auth.SmsRequest;
import com.hitotech.neighbour.entity.bind.BindReq;
import com.hitotech.neighbour.entity.bind.BindItemResult;
import com.hitotech.neighbour.entity.bind.BindResult;
import com.hitotech.neighbour.entity.bind.MobileResult;
import com.hitotech.neighbour.entity.home.BannerResult;
import com.hitotech.neighbour.entity.home.UrlResult;
import com.hitotech.neighbour.entity.member.HouseResult;
import com.hitotech.neighbour.entity.member.MemberResult;

import retrofit.Call;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;

/**
 * Created by Lv on 2016/5/17.
 */
public interface APIService {

    //-------------登录注册--------------//
    @POST(Constant.SEND_SMS)
    Call<AuthResult> sendSms(@Header("Auth-Key") String header, @Body SmsRequest mobile);

    @POST(Constant.REGISTER)
    Call<AuthResult> register(@Header("Auth-Key") String header, @Body RegRequest regRequest);

    @POST(Constant.LOGIN)
    Call<AuthResult> login(@Header("Auth-Key") String header, @Body LoginRequest loginRequest);

    @POST(Constant.LOGIN_OAUTH)
    Call<AuthResult> loginByOauth(@Header("Auth-Key") String header, @Body OauthRequest oauthRequest);

    @POST(Constant.RESET_PASSWORD)
    Call<AuthResult> resetPassword(@Header("Auth-Key") String header, @Body ResetRequest resetRequest);

    @POST(Constant.CHANGE_PASSWORD)
    Call<AuthResult> changePassword(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization, @Body ChangeRequest changeRequest);

    @POST(Constant.LOGOUT)
    Call<AuthResult> logout(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization);

    //-------------绑定信息--------------//
    @GET(Constant.GET_CITIES)
    Call<BindItemResult> getCities(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization);

    @GET(Constant.GET_COMMUNITIES)
    Call<BindItemResult> getCommunities(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization, @Query("city") int city_id);

    @GET(Constant.GET_BUILDINGS)
    Call<BindItemResult> getBuildings(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization, @Query("community_id") int community_id);

    @GET(Constant.GET_HOUSES)
    Call<BindItemResult> getHouses(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization, @Query("building_id") int building_id);

    @GET(Constant.GET_MOBILE)
    Call<MobileResult> getMobile(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization, @Query("house_id") int house_id);

    @POST(Constant.BIND_REQUEST)
    Call<BindResult> bindRequest(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization, @Body BindReq bindReq);

    //------------首页--------------//
    @GET(Constant.HOME)
    Call<BannerResult> homeRequest(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization);

    //------------其他--------------//
    //版本检测
    @GET(Constant.VERSION_CHECK)
    Call<UpdateResult> checkVersion(@Header("Auth-Key") String header, @Query("cur_version") String cur_version, @Query("platform_type") String platform_type, @Query("platform_version") String platform_version);

    @GET(Constant.H5_INIT)
    Call<UrlResult> getUrlData(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization);

    @GET(Constant.HOUSE_INFO)
    Call<HouseResult> getHouseInfo(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization);

    @GET(Constant.GET_MEMBER_INFO)
    Call<MemberResult> getMemberInfo(@Header("Auth-Key") String header, @Header("User-ID") String userId, @Header("Authorization") String authorization);
}
