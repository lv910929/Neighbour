package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.SwipeActivity;
import com.hitotech.neighbour.adapter.IdentifyAdapter;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.entity.IdentifyItem;
import com.hitotech.neighbour.entity.auth.AuthResult;
import com.hitotech.neighbour.entity.bind.BindBean;
import com.hitotech.neighbour.entity.bind.BindReq;
import com.hitotech.neighbour.entity.bind.BindResult;
import com.hitotech.neighbour.entity.bind.Mobile;
import com.hitotech.neighbour.entity.bind.MobileResult;
import com.hitotech.neighbour.entity.member.HouseBean;
import com.hitotech.neighbour.http.AuthApi;
import com.hitotech.neighbour.http.BindApi;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MessageUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;
import com.hitotech.neighbour.utils.SharePrefrenceUtil;
import com.hitotech.neighbour.utils.ValidateUtil;
import com.hitotech.neighbour.widget.ClearableEditText;
import com.hitotech.neighbour.widget.TimeButton;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

public class IdentifyInfoActivity extends SwipeActivity implements View.OnClickListener {

    private LinearLayout btnChangePhone;
    private TextView textFirstPart;
    private ClearableEditText editSecondPart;
    private TextView textThirdPart;
    private ClearableEditText editValidateCode;
    private TimeButton btnGetCode;
    private Button btnFinish;
    private RecyclerView recyclerIdentifySelect;
    private IdentifyAdapter identifyAdapter;

    private List<IdentifyItem> identifyItems;
    private HouseBean houseBean;
    private String phone;
    private String firstPart;
    private String secondPart;
    private String thirdPart;
    private String code;
    private int ownerFlag;
    private int identifyIndex;

    private OtherCallBack otherCallBack = new OtherCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            DialogUtil.hideHubWaitDialog();
            switch (what) {
                case 0x001://获取手机号
                    MobileResult mobileResult = (MobileResult) result;
                    Mobile mobile = mobileResult.getResult();
                    if (mobile != null) {
                        textFirstPart.setText(mobile.getFront());
                        textThirdPart.setText(mobile.getBack());
                    } else {
                        MyToast.showShortToast(mobileResult.getMsg());
                    }
                    break;
                case 0x002:
                    AuthResult authResult = (AuthResult) result;
                    MyToast.showShortToast(authResult.getMsg());
                    btnGetCode.start();
                    break;
                case 0x003:
                    BindResult bindResult = (BindResult) result;
                    BindBean bindBean = bindResult.getResult();
                    if (bindBean != null) {
                        SharePrefrenceUtil.saveBindInfo();
                        if (NeighbourApplication.isAddHouse){
                            MessageUtil.refreshHouseList();
                        }
                        IntentUtil.redirectMain(IdentifyInfoActivity.this);
                    } else {
                        MyToast.showShortToast("绑定失败,请稍后再试");
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, Object result) {
            DialogUtil.hideHubWaitDialog();
            switch (what) {
                case 0x001://获取手机号
                    MyToast.showShortToast("获取手机号失败");
                    break;
                case 0x002:
                    MyToast.showShortToast("发送验证码失败");
                    break;
                case 0x003:
                    BindResult bindResult = (BindResult) result;
                    if (bindResult != null) {
                        MyToast.showShortToast(bindResult.getMsg());
                    } else {
                        MyToast.showShortToast("绑定失败,请稍后再试");
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identify_info);
        initData();
        getData();
        initUI();
        initTimeButton(savedInstanceState);
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        houseBean = (HouseBean) bundle.getSerializable("houseBean");
        identifyItems = new ArrayList<>();
        identifyItems.add(new IdentifyItem(1, "房产在我名下"));
        identifyItems.add(new IdentifyItem(2, "我是业主家属"));
        identifyItems.add(new IdentifyItem(3, "我是租客"));
        ownerFlag = 0;
    }

    private void getData() {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(IdentifyInfoActivity.this)) {
            DialogUtil.showHubWaitDialog(IdentifyInfoActivity.this, "获取手机号中...");
            Call mobileCall = BindApi.getMobile(otherCallBack, 0x001, houseBean.getHouse_id());
            callList.add(mobileCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        btnChangePhone = (LinearLayout) findViewById(R.id.btn_change_phone);
        textFirstPart = (TextView) findViewById(R.id.text_first_part);
        editSecondPart = (ClearableEditText) findViewById(R.id.edit_second_part);
        textThirdPart = (TextView) findViewById(R.id.text_third_part);
        editValidateCode = (ClearableEditText) findViewById(R.id.edit_validate_code);
        btnGetCode = (TimeButton) findViewById(R.id.btn_get_code);
        recyclerIdentifySelect = (RecyclerView) findViewById(R.id.recycler_identify_select);
        btnFinish = (Button) findViewById(R.id.btn_finish);

        initToolBar("绑定");
        setRecyclerIdentifySelect();
        btnChangePhone.setOnClickListener(this);
        btnFinish.setOnClickListener(this);
    }

    private void setRecyclerIdentifySelect() {
        identifyAdapter = new IdentifyAdapter(IdentifyInfoActivity.this, identifyItems);
        recyclerIdentifySelect.setAdapter(identifyAdapter);
        recyclerIdentifySelect.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initTimeButton(Bundle savedInstanceState) {
        btnGetCode.onCreate(savedInstanceState);
        btnGetCode.setTextAfter("秒").setTextBefore("获取验证码").setLenght(120 * 1000);
        btnGetCode.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_change_phone:
                getData();
                break;
            case R.id.btn_get_code:
                if (validatePhone()) {
                    sendSms();
                }
                break;
            case R.id.btn_finish:
                if (validatePhone()) {
                    code = editValidateCode.getText().toString().trim();
                    if (ValidateUtil.validateCode(code)) {
                        identifyIndex = identifyAdapter.getmSelectedItem();
                        if (identifyIndex >= 0) {
                            IdentifyItem identifyItem = identifyItems.get(identifyIndex);
                            ownerFlag = identifyItem.getId();
                        }
                        BindReq bindReq = new BindReq(houseBean.getHouse_id(), phone, ownerFlag, code);
                        bindRequest(bindReq);
                    } else {
                        MyToast.showShortToast("验证码必须是6位数字");
                    }
                }
                break;
        }
    }

    //验证手机号
    private boolean validatePhone() {
        boolean result = true;
        firstPart = textFirstPart.getText().toString().trim();
        secondPart = editSecondPart.getText().toString().trim();
        thirdPart = textThirdPart.getText().toString().trim();
        phone = firstPart + secondPart + thirdPart;
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

    private void sendSms() {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(IdentifyInfoActivity.this)) {
            DialogUtil.showHubWaitDialog(IdentifyInfoActivity.this, "发送验证码中...");
            Call sendSmsCall = AuthApi.sendSms(otherCallBack, 0x002, phone);
            callList.add(sendSmsCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    private void bindRequest(BindReq bindReq) {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(IdentifyInfoActivity.this)) {
            DialogUtil.showHubWaitDialog(IdentifyInfoActivity.this, "绑定中...");
            Call bindCall = BindApi.bindRequest(otherCallBack, 0x003, bindReq);
            callList.add(bindCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }
}
