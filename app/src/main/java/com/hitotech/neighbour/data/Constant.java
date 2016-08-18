package com.hitotech.neighbour.data;

/**
 * Created by Lv on 2016/5/17.
 */
public class Constant {

    public static final int SUCCESS_CODE = 200;

    public static final String AUTH_KEY = "W6N1PXcHpLNjI6WasoVlekw3MeMl5qFl";

    //主接口
    public static final String BASE_URL = "http://apijl.hondapark.cn/";

    //发送短信
    public static final String SEND_SMS = "Auth/send_sms";

    //用户注册
    public static final String REGISTER = "Auth/reg";

    //手机动态登录
    public static final String LOGIN_MOBILE = "Auth/login_by_mobile";

    //账号密码登录
    public static final String LOGIN = "Auth/login";

    //第三方平台登录
    public static final String LOGIN_OAUTH = "Auth/login_by_oauth";

    //重置密码
    public static final String RESET_PASSWORD = "Auth/reset_password";

    //修改密码
    public static final String CHANGE_PASSWORD = "Auth/change_password";

    //登出
    public static final String LOGOUT = "Auth/logout";

    //城市列表
    public static final String GET_CITIES = "Binding/cities";

    //小区列表
    public static final String GET_COMMUNITIES = "Binding/communities";

    //楼栋列表
    public static final String GET_BUILDINGS = "Binding/buildings";

    //房号列表
    public static final String GET_HOUSES = "Binding/houses";

    //房产下业主手机号
    public static final String GET_MOBILE = "Binding/mobile";

    //绑定提交处理
    public static final String BIND_REQUEST = "Binding/do";

    //用户当前住址信息
    public static final String HOUSE_INFO = "Util/house_info";

    //个人信息频道页
    public static final String GET_MEMBER_INFO = "Member/index";

    //首页
    public static final String HOME = "Home/index";

    //url配置数据
    public static final String H5_INIT = "Util/h5init";

    //版本检测
    public static final String VERSION_CHECK = "Util/version_checking";

}
