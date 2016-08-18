package com.hitotech.neighbour.fragment;

import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bartoszlipinski.recyclerviewheader2.RecyclerViewHeader;
import com.bumptech.glide.Glide;
import com.hitotech.neighbour.R;
import com.hitotech.neighbour.adapter.MineAdapter;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.callback.UpdateFragListener;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.entity.MineItem;
import com.hitotech.neighbour.entity.MineTitle;
import com.hitotech.neighbour.entity.member.MemberBean;
import com.hitotech.neighbour.entity.member.MemberResult;
import com.hitotech.neighbour.http.MemberApi;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;
import com.hitotech.neighbour.widget.DividerItemDecoration;
import com.socks.library.KLog;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import retrofit.Call;

/**
 * Created by Lv on 2016/5/14.
 */
public class MineFragment extends BaseLoadingFragment implements View.OnClickListener,UpdateFragListener {

    private List<MineTitle> mineTitles;
    private MineItem mineAddressItem;
    private MineItem mineWalletItem;
    private MineItem mineOrderItem;
    private MineItem neighServiceItem;
    private MineItem neighOrderItem;
    private MineItem servicePhoneItem;
    private MineItem mineCarItem;
    private MineItem incomeItem;
    private MineItem feedbackItem;

    private MemberBean memberBean;

    private Toolbar toolbar;
    private CircleImageView imageMineAvatar;
    private TextView textAccountName;
    private TextView textAccountAddress;
    private Button btnChangeAddress;
    private RecyclerView recyclerMine;
    private MineAdapter mineAdapter;
    private RecyclerViewHeader recyclerHeaderMine;
    private LinearLayout itemMineSetting;
    private LinearLayout itemMineCenter;

    private CommCallBack commCallBack = new CommCallBack() {
        @Override
        public void onSuccess(Object result) {
            DialogUtil.hideHubWaitDialog();
            MemberResult memberResult = (MemberResult) result;
            if (memberResult != null && memberResult.getResult() != null) {
                memberBean = memberResult.getResult();
                KLog.i(memberBean.toString());
                loadPortrait();
                updateMineData();
            }
        }

        @Override
        public void onFail(Object result) {
            DialogUtil.hideHubWaitDialog();
            MyToast.showShortToast("加载信息失败，请稍后再试");
        }
    };


    public static MineFragment getInstance() {
        MineFragment mineFragment = new MineFragment();
        return mineFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(false);
    }

    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_mine, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initUI(view);
        getData();
    }

    //初始化数据
    private void initData() {

        mineTitles = new ArrayList<>();
        //列表1
        List<MineItem> mineItemList = new ArrayList<>();
        mineAddressItem = new MineItem(1, R.drawable.icon_mine_location, "我的住址", "", URLConstant.COMM_URL + URLConstant.ADDRESS_URL);
        mineWalletItem = new MineItem(2, R.drawable.icon_mine_wallet, "我的钱包", "", URLConstant.COMM_URL + URLConstant.BALANCE_URL);
        mineItemList.add(mineAddressItem);
        mineItemList.add(mineWalletItem);
        MineTitle mineTitle1 = new MineTitle(1, "", mineItemList);
        mineTitles.add(mineTitle1);
        //列表2
        List<MineItem> mineCarList = new ArrayList<>();
        mineCarItem = new MineItem(3, R.drawable.icon_mine_car, "我的车辆", "", URLConstant.COMM_URL + URLConstant.MY_CAR_URL);
        mineCarList.add(mineCarItem);
        MineTitle mineTitle2 = new MineTitle(2, "", mineCarList);
        mineTitles.add(mineTitle2);
        //列表3
        List<MineItem> mineOrderList = new ArrayList<>();
        mineOrderItem = new MineItem(4, R.drawable.icon_mine_service, "我的服务订单", "", URLConstant.COMM_URL + URLConstant.MY_ALL_ORDER);
        mineOrderList.add(mineOrderItem);
        MineTitle mineTitle3 = new MineTitle(3, "", mineOrderList);
        mineTitles.add(mineTitle3);
        //列表4
        List<MineItem> neighOrderList = new ArrayList<>();
        neighServiceItem = new MineItem(5, R.drawable.icon_neigh_service, "我提供的邻里服务", "", URLConstant.COMM_URL + URLConstant.MY_SERVICE_URL);
        neighOrderItem = new MineItem(6, R.drawable.icon_neigh_order, "我的邻里服务订单", "", URLConstant.COMM_URL + URLConstant.MY_SERVICE_ORDER);
        incomeItem = new MineItem(7, R.drawable.icon_mine_income, "收入明细", "", URLConstant.COMM_URL + URLConstant.INCOME_URL);
        neighOrderList.add(neighServiceItem);
        neighOrderList.add(neighOrderItem);
        neighOrderList.add(incomeItem);
        MineTitle mineTitle4 = new MineTitle(4, "", neighOrderList);
        mineTitles.add(mineTitle4);
        //列表5
        List<MineItem> otherItemList = new ArrayList<>();
        servicePhoneItem = new MineItem(8, R.drawable.icon_service_phone, "客服电话", "", "");
        feedbackItem = new MineItem(9, R.drawable.icon_mine_feedback, "意见及反馈", "", URLConstant.COMM_URL + URLConstant.FEEDBACK_URL);
        otherItemList.add(servicePhoneItem);
        otherItemList.add(feedbackItem);
        MineTitle mineTitle5 = new MineTitle(5, "", otherItemList);
        mineTitles.add(mineTitle5);
        //列表6
        List<MineItem> emptyItemList = new ArrayList<>();
        MineTitle mineTitle6 = new MineTitle(6, "", emptyItemList);
        mineTitles.add(mineTitle6);

        if (NeighbourApplication.urlBean != null) {
            mineAddressItem.setUrl(NeighbourApplication.urlBean.getAddressUrl());
            mineWalletItem.setUrl(NeighbourApplication.urlBean.getBalanceUrl());
            mineCarItem.setUrl(NeighbourApplication.urlBean.getMyCarUrl());
            mineOrderItem.setUrl(NeighbourApplication.urlBean.getMyOrderUrl());
            neighServiceItem.setUrl(NeighbourApplication.urlBean.getMyServiceUrl());
            neighOrderItem.setUrl(NeighbourApplication.urlBean.getMyServiceOrderUrl());
            incomeItem.setUrl(NeighbourApplication.urlBean.getIncomeUrl());
            feedbackItem.setUrl(NeighbourApplication.urlBean.getSuggestUrl());
        }
    }

    @Override
    protected void getData() {
        super.getData();
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(getActivity())) {
            Call memberResultCall = MemberApi.getMemberInfo(commCallBack);
            callList.add(memberResultCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    @Override
    protected void initUI(View view) {
        super.initUI(view);
        toolbar = initToolBar(view, R.menu.menu_mine, "我");
        itemMineCenter = (LinearLayout) view.findViewById(R.id.item_mine_center);
        imageMineAvatar = (CircleImageView) view.findViewById(R.id.image_mine_avatar);
        textAccountName = (TextView) view.findViewById(R.id.text_account_name);
        textAccountAddress = (TextView) view.findViewById(R.id.text_account_address);
        btnChangeAddress = (Button) view.findViewById(R.id.btn_change_address);
        recyclerMine = (RecyclerView) view.findViewById(R.id.recycler_mine);
        recyclerHeaderMine = (RecyclerViewHeader) view.findViewById(R.id.recyclerHeader_mine);
        itemMineSetting = (LinearLayout) view.findViewById(R.id.item_mine_setting);

        setRecyclerView();
        itemMineCenter.setOnClickListener(this);
        btnChangeAddress.setOnClickListener(this);
        itemMineSetting.setOnClickListener(this);
    }

    private void setRecyclerView() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerMine.setLayoutManager(linearLayoutManager);
        recyclerMine.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayoutManager.VERTICAL));
        recyclerMine.setHasFixedSize(true);
        mineAdapter = new MineAdapter(getContext(), mineTitles);
        recyclerMine.setAdapter(mineAdapter);
        recyclerHeaderMine.attachTo(recyclerMine);
    }

    //加载头像图片
    private void loadPortrait() {
        Glide.with(getContext().getApplicationContext())
                .load(memberBean.getMember_avatar())
                .error(R.drawable.ic_default_avatar)
                .centerCrop()
                .into(imageMineAvatar);
    }

    //更新用户数据
    private void updateMineData() {

        textAccountName.setText(memberBean.getMember_nickname());
        textAccountAddress.setText(memberBean.getMember_house());

        mineCarItem.setNotifyNum(memberBean.getCars_count());
        mineOrderItem.setNotifyNum(memberBean.getOrder_service_count());
        neighOrderItem.setNotifyNum(memberBean.getService_count());
        servicePhoneItem.setContent(memberBean.getService_phone());
        if (memberBean.getCredit_total() != null && !memberBean.getCredit_total().equals("")) {
            mineWalletItem.setContent(memberBean.getCredit_total());
        } else {
            mineWalletItem.setContent("");
        }
        mineAdapter.updateList(mineTitles);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_mine_center:
                if (memberBean != null && NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(getActivity(), "个人中心", NeighbourApplication.urlBean.getProfileUrl());
                } else {
                    IntentUtil.redirectWebView(getActivity(), "个人中心", URLConstant.COMM_URL + URLConstant.PROFILE_URL);
                }
                break;
            case R.id.btn_change_address:
                if (memberBean != null && NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(getActivity(), "切换地址", NeighbourApplication.urlBean.getAddressUrl());
                } else {
                    IntentUtil.redirectWebView(getActivity(), "切换地址", URLConstant.COMM_URL + URLConstant.ADDRESS_URL);
                }
                break;
            case R.id.item_mine_setting:
                if (memberBean != null && NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(getActivity(), "设置", NeighbourApplication.urlBean.getSettingUrl());
                } else {
                    IntentUtil.redirectWebView(getActivity(), "切换地址", URLConstant.COMM_URL + URLConstant.SETTING_URL);
                }
                break;
        }
    }

    @Override
    public void transferMsg() {
        getData();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (NeighbourApplication.isMineTab){
            if (NetUtils.hasNetWorkConection(getActivity())) {//判断有没有网络
                DialogUtil.showHubWaitDialog(getActivity(), "加载信息中...");
                Call memberResultCall = MemberApi.getMemberInfo(commCallBack);
                callList.add(memberResultCall);
            } else {
                MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
            }
        }
    }
}
