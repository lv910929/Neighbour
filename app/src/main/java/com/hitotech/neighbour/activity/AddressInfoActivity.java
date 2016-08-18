package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.SwipeActivity;
import com.hitotech.neighbour.entity.member.HouseBean;
import com.hitotech.neighbour.utils.IHandler;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;

public class AddressInfoActivity extends SwipeActivity implements View.OnClickListener, IHandler {

    public static Handler handler;

    private RelativeLayout itemAddressVillage;
    private TextView textAddressVillage;
    private RelativeLayout itemAddressCity;
    private TextView textAddressCity;
    private RelativeLayout itemAddressHouse;
    private TextView textAddressHouse;
    private Button btnNext;
    private Button btnGuest;

    private HouseBean houseBean;
    private String addressVillage;
    private String addressCity;
    private String addressHouse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_info);
        initData();
        initUI();
        initHandler();
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        houseBean = (HouseBean) bundle.getSerializable("houseBean");
        houseBean = null;
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        itemAddressVillage = (RelativeLayout) findViewById(R.id.item_address_village);
        textAddressVillage = (TextView) findViewById(R.id.text_address_village);
        itemAddressCity = (RelativeLayout) findViewById(R.id.item_address_city);
        textAddressCity = (TextView) findViewById(R.id.text_address_city);
        itemAddressHouse = (RelativeLayout) findViewById(R.id.item_address_house);
        textAddressHouse = (TextView) findViewById(R.id.text_address_house);
        btnNext = (Button) findViewById(R.id.btn_next);
        btnGuest = (Button) findViewById(R.id.btn_guest);

        initToolBar("房屋信息");
        itemAddressVillage.setOnClickListener(this);
        itemAddressCity.setOnClickListener(this);
        itemAddressHouse.setOnClickListener(this);
        btnNext.setOnClickListener(this);
        btnGuest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_address_city://城市
                IntentUtil.redirectCity(AddressInfoActivity.this, houseBean);
                break;
            case R.id.item_address_village://小区
                addressCity = textAddressCity.getText().toString().trim();
                if (!addressCity.equals("")) {
                    IntentUtil.redirectCommunity(AddressInfoActivity.this, houseBean);
                } else {
                    MyToast.showShortToast("请先选择城市");
                }
                break;
            case R.id.item_address_house://房号
                addressCity = textAddressCity.getText().toString().trim();
                addressVillage = textAddressVillage.getText().toString().trim();
                if (!addressCity.equals("")) {
                    if (!addressVillage.equals("")) {
                        IntentUtil.redirectBuilding(AddressInfoActivity.this, houseBean);
                    } else {
                        MyToast.showShortToast("请先选择小区");
                    }
                } else {
                    MyToast.showShortToast("请先选择城市");
                }
                break;
            case R.id.btn_next:
                if (doValidate()) {
                    IntentUtil.redirectIdentify(AddressInfoActivity.this, houseBean);
                }
                break;
            case R.id.btn_guest:

                break;
        }
    }

    private boolean doValidate() {
        boolean result = true;
        addressCity = textAddressCity.getText().toString().trim();
        addressVillage = textAddressVillage.getText().toString().trim();
        addressHouse = textAddressHouse.getText().toString().trim();
        StringBuilder buffer = new StringBuilder();
        if (addressCity.equals("")) {
            buffer.append("请选择城市\n");
        } else if (addressVillage.equals("")) {
            buffer.append("请填写小区名称\n");
        } else if (addressHouse.equals("")) {
            buffer.append("请填写房号\n");
        }
        if (!TextUtils.isEmpty(buffer)) {
            result = false;
            MyToast.showShortToast(buffer.substring(0, buffer.length() - 1));
        }
        return result;
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                if (msg.what == 0x001) {
                    houseBean = (HouseBean) msg.obj;
                    if (houseBean != null) {
                        if (houseBean.getCity_name() != null && !houseBean.getCity_name().equals("")) {
                            textAddressCity.setText(houseBean.getCity_name());
                        }
                        if (houseBean.getCommunity_name() != null && !houseBean.getCommunity_name().equals("")) {
                            textAddressVillage.setText(houseBean.getCommunity_name());
                        }
                        if (houseBean.getBuilding_name() != null && !houseBean.getBuilding_name().equals("")) {
                            if (houseBean.getHouse_name() != null && !houseBean.getHouse_name().equals("")) {
                                textAddressHouse.setText(houseBean.getCommunity_name() + " " + houseBean.getHouse_name());
                            }
                        }
                    }
                }
            }
        };
    }

    @Override
    public Handler getHandler() {
        return handler;
    }
}
