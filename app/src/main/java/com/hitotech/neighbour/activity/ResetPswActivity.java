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
import com.hitotech.neighbour.entity.auth.ResetRequest;
import com.hitotech.neighbour.http.AuthApi;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;
import com.hitotech.neighbour.utils.SharePrefrenceUtil;
import com.hitotech.neighbour.utils.ValidateUtil;
import com.hitotech.neighbour.widget.ClearableEditText;
import com.hitotech.neighbour.widget.TimeButton;

import retrofit.Call;

public class ResetPswActivity extends SwipeActivity implements View.OnClickListener {

    private String phone;
    private String code;
    private String newPassword;
    private String confirmPassword;

    private ClearableEditText textPhone;
    private ClearableEditText textCode;
    private TimeButton btnGetCode;
    private ClearableEditText textNewPassword;
    private ClearableEditText textConfirmPassword;
    private Button btnConfirm;

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
                    MyToast.showShortToast("重置密码成功！");
                    onBackPressed();
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
                    MyToast.showShortToast("重置密码失败,请稍后再试");
                }
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_psw);
        initUI();
        initTimeButton(savedInstanceState);
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        textPhone = (ClearableEditText) findViewById(R.id.text_phone);
        textCode = (ClearableEditText) findViewById(R.id.text_code);
        textNewPassword = (ClearableEditText) findViewById(R.id.text_new_password);
        textConfirmPassword = (ClearableEditText) findViewById(R.id.text_confirm_password);
        btnGetCode = (TimeButton) findViewById(R.id.btn_get_code);
        btnConfirm = (Button) findViewById(R.id.btn_confirm);

        initToolBar("重置密码");
        btnConfirm.setOnClickListener(this);
    }

    private void initTimeButton(Bundle savedInstanceState) {
        btnGetCode.onCreate(savedInstanceState);
        btnGetCode.setTextAfter("秒").setTextBefore("获取验证码").setLenght(120 * 1000);
        btnGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_code:
                if (validatePhone()) {
                    sendSms();
                }
                break;
            case R.id.btn_confirm:
                if (doValidate()) {
                    ResetRequest resetRequest = new ResetRequest(phone, code, newPassword, confirmPassword);
                    resetPswReq(resetRequest);
                }
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
        code = textCode.getText().toString().trim();
        newPassword = textNewPassword.getText().toString().trim();
        confirmPassword = textConfirmPassword.getText().toString().trim();
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
                if (newPassword.equals("")) {
                    buffer.append("请填写新密码\n");
                } else if (!ValidateUtil.validatePassword(newPassword)) {
                    buffer.append("密码只能是6至20位的字母和数字\n");
                } else if (!newPassword.equals(confirmPassword)) {
                    buffer.append("两次密码输入不一致\n");
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
        if (NetUtils.hasNetWorkConection(ResetPswActivity.this)) {
            DialogUtil.showHubWaitDialog(ResetPswActivity.this, "发送验证码中...");
            Call sendSmsCall = AuthApi.sendSms(otherCallBack, 0x001, phone);
            callList.add(sendSmsCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    private void resetPswReq(ResetRequest resetRequest) {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(ResetPswActivity.this)) {
            DialogUtil.showHubWaitDialog(ResetPswActivity.this, "重置密码中...");
            Call resetCall = AuthApi.resetPassword(otherCallBack, 0x002, resetRequest);
            callList.add(resetCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }
}
