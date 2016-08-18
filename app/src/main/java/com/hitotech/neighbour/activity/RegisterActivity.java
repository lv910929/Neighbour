package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.SwipeActivity;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.entity.auth.AuthResult;
import com.hitotech.neighbour.entity.auth.RegRequest;
import com.hitotech.neighbour.http.AuthApi;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;
import com.hitotech.neighbour.utils.SharePrefrenceUtil;
import com.hitotech.neighbour.utils.ValidateUtil;
import com.hitotech.neighbour.widget.ClearableEditText;
import com.hitotech.neighbour.widget.TimeButton;

import retrofit.Call;

public class RegisterActivity extends SwipeActivity implements View.OnClickListener {

    private String phone;
    private String code;
    private String password;

    private ClearableEditText textPhone;
    private ClearableEditText textCode;
    private ClearableEditText textPassword;
    private TimeButton btnGetCode;
    private Button btnRegister;
    private Button btnToLogin;
    private Button btnToAgreement;

    private OtherCallBack otherCallBack = new OtherCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            DialogUtil.hideHubWaitDialog();
            AuthResult authResult = (AuthResult) result;
            if (what == 0x001) {//发送验证码
                if (authResult.getCode() == 200) {
                    MyToast.showShortToast(authResult.getMsg());
                    btnGetCode.start();
                } else {
                    MyToast.showShortToast("发送验证码失败");
                }
            } else {
                if (authResult.getCode() == 200) {
                    SharePrefrenceUtil.saveRegisterToken(authResult.getResult());
                    MyToast.showShortToast("注册成功");
                    IntentUtil.redirectBindWithFinish(RegisterActivity.this, null);
                } else {
                    MyToast.showShortToast(authResult.getMsg());
                }
            }
        }

        @Override
        public void onFail(int what, Object result) {
            DialogUtil.hideHubWaitDialog();
            if (what == 0x001) {//发送验证码
                MyToast.showShortToast("发送验证码失败");
            } else {
                AuthResult authResult = (AuthResult) result;
                if (authResult != null) {
                    MyToast.showShortToast(authResult.getMsg());
                } else {
                    MyToast.showShortToast("注册失败");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        initUI();
        initTimeButton(savedInstanceState);
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        textPhone = (ClearableEditText) findViewById(R.id.text_phone);
        textCode = (ClearableEditText) findViewById(R.id.text_code);
        textPassword = (ClearableEditText) findViewById(R.id.text_password);
        btnGetCode = (TimeButton) findViewById(R.id.btn_get_code);
        btnRegister = (Button) findViewById(R.id.btn_register);
        btnToLogin = (Button) findViewById(R.id.btn_to_login);
        btnToAgreement = (Button) findViewById(R.id.btn_to_agreement);

        initToolBar("注册");
        btnRegister.setOnClickListener(this);
        btnToLogin.setOnClickListener(this);
        btnToAgreement.setOnClickListener(this);
    }

    private void initTimeButton(Bundle savedInstanceState) {
        btnGetCode.onCreate(savedInstanceState);
        btnGetCode.setTextAfter("秒").setTextBefore("获取验证码").setLenght(120 * 1000);
        btnGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_register:
                if (doValidate()) {
                    RegRequest regRequest = new RegRequest(phone, code, password);
                    register(regRequest);
                }
                break;
            case R.id.btn_get_code:
                if (validatePhone()) {
                    sendSms();
                }
                break;
            case R.id.btn_to_login:
                onBackPressed();
                break;
            case R.id.btn_to_agreement:
                IntentUtil.redirectAgreement(RegisterActivity.this);
                break;
        }
    }

    private boolean validatePhone() {
        boolean result = true;
        phone = textPhone.getText().toString().trim();
        StringBuilder buffer = new StringBuilder();
        if (phone.equals("")) {
            buffer.append("请填写手机号\n");
        } else if (!ValidateUtil.validatePhone(phone)) {
            buffer.append("请填写有效的手机号\n");
        }
        if (!TextUtils.isEmpty(buffer)) {
            result = false;
            MyToast.showShortToast(buffer.substring(0, buffer.length() - 1));
        }
        return result;
    }

    private boolean doValidate() {
        boolean result = true;
        phone = textPhone.getText().toString().trim();
        password = textPassword.getText().toString().trim();
        code = textCode.getText().toString().trim();
        StringBuilder buffer = new StringBuilder();
        if (phone.equals("")) {
            buffer.append("请填写手机号\n");
        } else if (!ValidateUtil.validatePhone(phone)) {
            buffer.append("请填写有效的手机号\n");
        } else {
            if (code.equals("")) {
                buffer.append("请填写验证码\n");
            } else if (!ValidateUtil.validateCode(code)) {
                buffer.append("验证码只能是6位数字\n");
            } else {
                if (password.equals("")) {
                    buffer.append("请填写密码\n");
                } else if (!ValidateUtil.validatePassword(password)) {
                    buffer.append("密码只能是6至20位的字母和数字\n");
                }
            }
        }
        if (!TextUtils.isEmpty(buffer)) {
            result = false;
            MyToast.showShortToast(buffer.substring(0, buffer.length() - 1));
        }
        return result;
    }

    private void sendSms() {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(RegisterActivity.this)) {
            DialogUtil.showHubWaitDialog(RegisterActivity.this, "发送验证码中...");
            Call sendSmsCall = AuthApi.sendSms(otherCallBack, 0x001, phone);
            callList.add(sendSmsCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    private void register(RegRequest regRequest) {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(RegisterActivity.this)) {
            DialogUtil.showHubWaitDialog(RegisterActivity.this, "注册中...");
            Call registerCall = AuthApi.register(otherCallBack, 0x002, regRequest);
            callList.add(registerCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }
}
