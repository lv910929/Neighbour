package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.BaseSelectActivity;
import com.hitotech.neighbour.adapter.BindAdapter;
import com.hitotech.neighbour.adapter.listener.OnItemSelectListener;
import com.hitotech.neighbour.callback.CommCallBack;
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

public class SelectHouseActivity extends BaseSelectActivity {

    private List<BindItem> communityList;

    private RecyclerView recyclerCommunity;
    private BindAdapter communityAdapter;

    private CommCallBack commCallBack = new CommCallBack() {
        @Override
        public void onSuccess(Object result) {
            DialogUtil.hideHubWaitDialog();
            BindItemResult bindResult = (BindItemResult) result;
            if (bindResult != null) {
                if (bindResult.getResult() != null) {
                    communityList = bindResult.getResult();
                    loadCommunityList();
                }
            }
        }

        @Override
        public void onFail(Object result) {
            DialogUtil.hideHubWaitDialog();
            MyToast.showShortToast("获取房号列表失败，请稍后再试");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_community);
        initData();
        initUI();
        getCommunityList();
    }

    private void initData() {
        Bundle bundle = this.getIntent().getExtras();
        houseBean = (HouseBean) bundle.getSerializable("houseBean");
        communityList = new ArrayList<>();
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);

        recyclerCommunity = (RecyclerView) findViewById(R.id.recycler_community);
        initToolBar("选择房号");
        setRecyclerCommunity();
    }

    private void setRecyclerCommunity() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(SelectHouseActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerCommunity.setLayoutManager(linearLayoutManager);
        communityAdapter = new BindAdapter(communityList, SelectHouseActivity.this);
        recyclerCommunity.setAdapter(communityAdapter);
    }

    private void loadCommunityList() {
        if (communityAdapter == null) {
            communityAdapter = new BindAdapter(communityList, SelectHouseActivity.this);
            recyclerCommunity.setAdapter(communityAdapter);
        } else {
            communityAdapter.updateList(communityList);
        }
        communityAdapter.setOnItemSelectListener(new OnItemSelectListener() {
            @Override
            public void onItemSelect(int position) {
                BindItem bindItem = communityList.get(position);
                houseBean.setHouse_id(bindItem.getId());
                houseBean.setHouse_name(bindItem.getName());
                IntentUtil.redirectBind(SelectHouseActivity.this, houseBean);
            }
        });
    }

    private void getCommunityList() {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(SelectHouseActivity.this)) {
            DialogUtil.showHubWaitDialog(SelectHouseActivity.this, "获取房号列表中...");
            Call communityCall = BindApi.getHouseList(commCallBack, houseBean.getBuilding_id());
            callList.add(communityCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }
}
