package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.BaseSelectActivity;
import com.hitotech.neighbour.adapter.BindAdapter;
import com.hitotech.neighbour.adapter.listener.OnItemSelectListener;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.entity.LocateState;
import com.hitotech.neighbour.entity.bind.BindItem;
import com.hitotech.neighbour.entity.bind.BindItemResult;
import com.hitotech.neighbour.entity.member.HouseBean;
import com.hitotech.neighbour.http.BindApi;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

public class SelectCityActivity extends BaseSelectActivity implements BDLocationListener,View.OnClickListener {

    private RelativeLayout itemLocationCity;
    private TextView textLocationCity;
    private Button btnGetLocation;
    private RecyclerView recyclerCity;
    private BindAdapter cityAdapter;

    private List<BindItem> cityList;
    private int locateState;
    private String locatedCity;

    public LocationClient locationClient;

    private CommCallBack commCallBack = new CommCallBack() {
        @Override
        public void onSuccess(Object result) {
            DialogUtil.hideHubWaitDialog();
            BindItemResult cityResult = (BindItemResult) result;
            if (cityResult.getResult() != null) {
                cityList = cityResult.getResult();
                loadCityList();
            }
        }

        @Override
        public void onFail(Object result) {
            DialogUtil.hideHubWaitDialog();
            MyToast.showShortToast("获取城市列表失败，请稍后再试");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_city);
        initData();
        setLocationClientConfig();
        initUI();
        getCityList();
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        houseBean = (HouseBean) bundle.getSerializable("houseBean");
        if (houseBean == null) {
            houseBean = new HouseBean();
        }
        cityList = new ArrayList<>();
    }

    protected void setLocationClientConfig() {

        locationClient = new LocationClient(getApplicationContext());
        locationClient.registerLocationListener(this);
        LocationClientOption option = new LocationClientOption();
        option.setOpenGps(true); // 打开gps
        option.setCoorType("bd09ll"); // 设置坐标类型
        option.setAddrType("all"); // 设置地址信息，仅设置为“all”时有地址信息，默认无地址信息
        option.setScanSpan(0); // 设置定位模式，小于1秒则一次定位;大于等于1秒则定时定位
        locationClient.setLocOption(option);
        locationClient.start();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        itemLocationCity = (RelativeLayout) findViewById(R.id.item_location_city);
        textLocationCity = (TextView) findViewById(R.id.text_location_city);
        btnGetLocation = (Button) findViewById(R.id.btn_get_location);
        recyclerCity = (RecyclerView) findViewById(R.id.recycler_city);
        initToolBar("选择城市");
        setRecyclerCity();
        itemLocationCity.setOnClickListener(this);
        btnGetLocation.setOnClickListener(this);
    }

    private void setRecyclerCity() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectCityActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerCity.setLayoutManager(linearLayoutManager);
        cityAdapter = new BindAdapter(cityList, SelectCityActivity.this);
        recyclerCity.setAdapter(cityAdapter);
    }

    private void getCityList() {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(SelectCityActivity.this)) {
            DialogUtil.showHubWaitDialog(SelectCityActivity.this, "获取城市列表中...");
            Call cityCall = BindApi.getCityList(commCallBack);
            callList.add(cityCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    //加载城市列表
    private void loadCityList() {
        if (cityAdapter == null) {
            cityAdapter = new BindAdapter(cityList, SelectCityActivity.this);
            recyclerCity.setAdapter(cityAdapter);
        } else {
            cityAdapter.updateList(cityList);
        }
        cityAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(int position) {
                BindItem bindItem = cityList.get(position);
                houseBean.setCity_id(bindItem.getId());
                houseBean.setCity_name(bindItem.getName());
                IntentUtil.redirectCommunity(SelectCityActivity.this, houseBean);
            }
        });
    }

    private void updateLocateState(int state) {
        locateState = state;
        switch (locateState) {
            case LocateState.LOCATING:
                textLocationCity.setText("定位中...");
                break;
            case LocateState.FAILED:
                textLocationCity.setText("定位失败");
                break;
            case LocateState.SUCCESS:
                textLocationCity.setText(locatedCity);
                break;
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_get_location:
                if (locateState == LocateState.FAILED) {
                    Log.e("onLocateClick", "重新定位...");
                    updateLocateState(LocateState.LOCATING);
                }
                break;
            case R.id.item_location_city:
                checkLocation();
                break;
        }
    }

    private void checkLocation() {
        switch (locateState) {
            case LocateState.LOCATING:
                MyToast.showShortToast("正在定位中");
                break;
            case LocateState.FAILED:
                MyToast.showShortToast("定位失败");
                break;
            case LocateState.SUCCESS:
                if (compareLocation()) {
                    IntentUtil.redirectCommunity(SelectCityActivity.this, houseBean);
                } else {
                    MyToast.showShortToast("您定位的城市不在城市列表中");
                }
                break;
        }
    }

    private boolean compareLocation() {
        boolean result = false;
        if (cityList != null && cityList.size() > 0) {
            for (int i = 0; i < cityList.size(); i++) {
                if (locatedCity.contains(cityList.get(i).getName())) {
                    houseBean.setCity_id(cityList.get(i).getId());
                    houseBean.setCity_name(cityList.get(i).getName());
                    result = true;
                    break;
                }
            }
        }
        return result;
    }

    @Override
    public void onReceiveLocation(BDLocation bdLocation) {
        if (null != bdLocation) {
            locatedCity = bdLocation.getCity();
            updateLocateState(LocateState.SUCCESS);
            locationClient.stop();
            btnGetLocation.setVisibility(View.GONE);
        } else {//定位失败
            updateLocateState(LocateState.FAILED);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
