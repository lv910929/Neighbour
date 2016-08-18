package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.BaseActivity;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.entity.auth.AuthResult;
import com.hitotech.neighbour.entity.auth.LoginRequest;
import com.hitotech.neighbour.http.AuthApi;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;
import com.hitotech.neighbour.utils.PushManager;
import com.hitotech.neighbour.utils.SharePrefrenceUtil;
import com.hitotech.neighbour.utils.ValidateUtil;
import com.hitotech.neighbour.widget.ClearableEditText;

import retrofit.Call;

public class LoginActivity extends BaseActivity implements View.OnClickListener {

    private String phone;
    private String password;

    private ClearableEditText textPhone;
    private ClearableEditText textPassword;
    private Button btnForgetPsw;
    private Button btnToRegister;
    private Button btnLogin;

    private CommCallBack commCallBack = new CommCallBack() {
        @Override
        public void onSuccess(Object result) {
            DialogUtil.hideHubWaitDialog();
            AuthResult authResult = (AuthResult) result;
            if (authResult.getCode() == 200) {
                SharePrefrenceUtil.saveLoginToken(authResult.getResult());
                MyToast.showShortToast("登录成功");
                PushManager.setAlias(NeighbourApplication.user_id);
                if (NeighbourApplication.hasBind) {
                    IntentUtil.redirectMain(LoginActivity.this);
                } else {
                    IntentUtil.redirectBind(LoginActivity.this, null);
                }
            } else {
                MyToast.showShortToast(authResult.getMsg());
            }
        }

        @Override
        public void onFail(Object result) {
            DialogUtil.hideHubWaitDialog();
            AuthResult authResult = (AuthResult) result;
            if (authResult != null) {
                MyToast.showShortToast(authResult.getMsg());
            } else {
                MyToast.showShortToast("登录失败");
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initUI();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        textPhone = (ClearableEditText) findViewById(R.id.text_phone);
        textPassword = (ClearableEditText) findViewById(R.id.text_password);
        btnToRegister = (Button) findViewById(R.id.btn_to_register);
        btnLogin = (Button) findViewById(R.id.btn_login);
        btnForgetPsw = (Button) findViewById(R.id.btn_forget_psw);

        setToolBar("登录");
        btnLogin.setOnClickListener(this);
        btnToRegister.setOnClickListener(this);
        btnForgetPsw.setOnClickListener(this);
    }

    private void setToolBar(String title) {
        setTitle("");
        setSupportActionBar(toolbar);
        textTitle.setText(title);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_login:
                if (doValidate()) {
                    LoginRequest loginRequest = new LoginRequest(phone, password);
                    loginReq(loginRequest);
                }
                break;
            case R.id.btn_to_register:
                IntentUtil.redirectRegister(LoginActivity.this);
                break;
            case R.id.btn_forget_psw:
                IntentUtil.redirectResetPsw(LoginActivity.this);
                break;
        }
    }

    private boolean doValidate() {
        boolean result = true;
        phone = textPhone.getText().toString().trim();
        password = textPassword.getText().toString().trim();
        StringBuilder buffer = new StringBuilder();
        if (phone.equals("")) {
            buffer.append("请填写手机号\n");
        } else if (!ValidateUtil.validatePhone(phone)) {
            buffer.append("请填写有效的手机号\n");
        } else {
            if (password.equals("")) {
                buffer.append("请填写密码\n");
            } else if (!ValidateUtil.validatePassword(password)) {
                buffer.append("密码只能是6至20位的字母和数字\n");
            }
        }
        if (!TextUtils.isEmpty(buffer)) {
            result = false;
            MyToast.showShortToast(buffer.substring(0, buffer.length() - 1));
        }
        return result;
    }

    private void loginReq(LoginRequest loginRequest) {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(LoginActivity.this)) {
            DialogUtil.showHubWaitDialog(LoginActivity.this, "登录中...");
            Call loginCall = AuthApi.login(commCallBack, loginRequest);
            callList.add(loginCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    /**
     * 重写返回键点击事件
     */
    private long mExitTime;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                MyToast.showShortToast("再按一次退出");
                mExitTime = System.currentTimeMillis();
            } else {
                finish();
            }
            return true;
        }
        if (keyCode == KeyEvent.KEYCODE_MENU) {
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
