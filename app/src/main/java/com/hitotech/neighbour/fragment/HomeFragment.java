package com.hitotech.neighbour.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.OpenDoorActivity;
import com.hitotech.neighbour.adapter.ServiceAdapter;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.OtherCallBack;
import com.hitotech.neighbour.callback.UpdateFragListener;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.entity.home.Banner;
import com.hitotech.neighbour.entity.home.BannerList;
import com.hitotech.neighbour.entity.home.BannerResult;
import com.hitotech.neighbour.entity.home.UrlResult;
import com.hitotech.neighbour.entity.member.HouseBean;
import com.hitotech.neighbour.entity.member.HouseResult;
import com.hitotech.neighbour.http.HomeApi;
import com.hitotech.neighbour.http.MemberApi;
import com.hitotech.neighbour.utils.DeviceManager;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.hitotech.neighbour.utils.NetUtils;
import com.hitotech.neighbour.utils.StringUtil;
import com.hitotech.neighbour.utils.UrlParseUtil;
import com.socks.library.KLog;
import com.yyydjk.library.BannerLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Call;

/**
 * Created by Lv on 2016/5/14.
 */
public class HomeFragment extends BaseLoadingFragment implements View.OnClickListener, UpdateFragListener {

    private LinearLayout layoutLocation;
    private TextView textLocation;
    private RelativeLayout layoutGuestRequest;
    private RelativeLayout layoutOpenDoor;
    private LinearLayout itemServicePhone;
    private BannerLayout bannerHome;
    private RecyclerView recyclerHouseKeep;
    private ServiceAdapter serviceAdapter;

    private HouseBean houseBean;
    private List<Banner> banners;
    private List<String> bannerImages;

    public static HomeFragment getInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    private OtherCallBack otherCallBack = new OtherCallBack() {
        @Override
        public void onSuccess(int what, Object result) {
            DialogUtil.hideHubWaitDialog();
            switch (what) {
                case 0x000:
                    UrlResult urlResult = (UrlResult) result;
                    if (urlResult != null && urlResult.getResult() != null) {
                        NeighbourApplication.urlBean = urlResult.getResult();
                        serviceAdapter.updateData();
                    } else {
                        MyToast.showShortToast("加载失败,请稍后再试");
                    }
                    break;
                case 0x001:
                    HouseResult houseResult = (HouseResult) result;
                    if (houseResult != null && houseResult.getResult() != null) {
                        houseBean = houseResult.getResult();
                        NeighbourApplication.houseBean = houseBean;
                        textLocation.setText(houseResult.getResult().getCommunity_name());
                    }
                    break;
                case 0x002:
                    BannerResult bannerResult = (BannerResult) result;
                    if (bannerResult != null && bannerResult.getResult() != null) {
                        BannerList bannerList = bannerResult.getResult();
                        if (bannerList != null && bannerList.getBanner() != null) {
                            banners = bannerList.getBanner();
                            for (int i = 0; i < banners.size(); i++) {
                                bannerImages.add(banners.get(i).getThumb());
                            }
                            KLog.i("bannerImages.size----------" + bannerImages.size());
                            loadBanner();
                        }
                    }
                    break;
            }
        }

        @Override
        public void onFail(int what, Object result) {
            DialogUtil.hideHubWaitDialog();
            MyToast.showShortToast("加载失败,请稍后再试");
        }
    };

    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initData();
        initUI(view);
        getData();
    }

    private void initData() {
        if (bannerImages == null) {
            bannerImages = new ArrayList<>();
        }
    }

    @Override
    protected void getData() {
        super.getData();
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(getActivity())) {
            DialogUtil.showHubWaitDialog(getActivity(), "加载中...");
            Call urlCall = HomeApi.getUrlData(otherCallBack, 0x000);
            Call houseInfoCall = MemberApi.getHouseInfo(otherCallBack, 0x001);
            Call bannerCall = HomeApi.homeRequest(otherCallBack, 0x002);
            callList.add(urlCall);
            callList.add(houseInfoCall);
            callList.add(bannerCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }

    @Override
    protected void initUI(View view) {
        super.initUI(view);
        layoutLocation = (LinearLayout) view.findViewById(R.id.layout_location);
        textLocation = (TextView) view.findViewById(R.id.text_location);
        recyclerHouseKeep = (RecyclerView) view.findViewById(R.id.recycler_house_keep);
        layoutGuestRequest = (RelativeLayout) view.findViewById(R.id.layout_guest_request);
        layoutOpenDoor = (RelativeLayout) view.findViewById(R.id.layout_open_door);
        itemServicePhone = (LinearLayout) view.findViewById(R.id.item_service_phone);
        bannerHome = (BannerLayout) view.findViewById(R.id.banner_home);

        setBannerHome();
        setRecyclerHouseKeep();
        layoutLocation.setOnClickListener(this);
        layoutGuestRequest.setOnClickListener(this);
        layoutOpenDoor.setOnClickListener(this);
        itemServicePhone.setOnClickListener(this);
    }

    private void setBannerHome() {
        ViewGroup.LayoutParams linearParams = bannerHome.getLayoutParams(); //取控件textView当前的布局参数
        linearParams.height = (int) ((DeviceManager.getScreenWidth(getActivity())) * 0.6);
        bannerHome.setLayoutParams(linearParams); //使设置好的布局参数应用到控件
    }

    private void setRecyclerHouseKeep() {
        recyclerHouseKeep.setLayoutManager(new GridLayoutManager(getContext(), 4));
        serviceAdapter = new ServiceAdapter(getContext());
        recyclerHouseKeep.setAdapter(serviceAdapter);
    }

    private void loadBanner() {
        if (bannerImages.size() > 1) {
            bannerHome.startAutoPlay();
        } else {
            bannerHome.stopAutoPlay();
        }
        bannerHome.setViewUrls(bannerImages);
        bannerHome.setOnBannerItemClickListener(new BannerLayout.OnBannerItemClickListener() {
            @Override
            public void onItemClick(int i) {
                Banner banner = banners.get(i);
                if (banner.getUrl() != null && !banner.getUrl().equals("")) {
                    String parsedUrl = parseUrl(banner.getUrl());
                    KLog.i("parsedUrl------" + parsedUrl);
                    IntentUtil.redirectWebView(getActivity(), banner.getTitle(), parsedUrl);
                }
            }
        });
    }

    private String parseUrl(String url) {
        String parsedUrl = "";
        Map<String, String> urlMap = new HashMap<>();
        if (!url.equals("") && url.contains("?")) {
            String[] strings = url.split("[?]");
            String urlString = strings[1];
            if (strings.length > 2) {
                urlString = urlString + "&" + strings[2];
            }
            String[] paramStrings = urlString.split("&");
            for (int i = 0; i < paramStrings.length; i++) {
                String[] urlStrings = paramStrings[i].split("=");
                String key = StringUtil.decodeString(urlStrings[0]);
                String value = StringUtil.decodeString(urlStrings[1]);
                urlMap.put(key, value);
            }
            parsedUrl = UrlParseUtil.spliceUrl(urlMap);
        }
        return parsedUrl;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.item_service_phone:
                if (houseBean != null && houseBean.getService_phone() != null) {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + houseBean.getService_phone()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    getActivity().startActivity(intent);
                }
                break;
            case R.id.layout_location:
                if (houseBean != null) {
                    if (NeighbourApplication.urlBean != null) {
                        IntentUtil.redirectWebView(getActivity(), "我的住址", NeighbourApplication.urlBean.getAddressUrl());
                    } else {
                        IntentUtil.redirectWebView(getActivity(), "我的住址", URLConstant.COMM_URL + URLConstant.ADDRESS_URL);
                    }
                } else {
                    MyToast.showShortToast("个人住址获取失败");
                }
                break;
            case R.id.layout_guest_request:
                if (houseBean != null && NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(getActivity(), "访客邀请", NeighbourApplication.urlBean.getVisitorUrl());
                } else {
                    IntentUtil.redirectWebView(getActivity(), "访客邀请", URLConstant.COMM_URL + URLConstant.VISITOR_URL);
                }
                break;
            case R.id.layout_open_door:
                startActivity(new Intent(getActivity(), OpenDoorActivity.class));
                getActivity().overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
                break;
        }
    }

    @Override
    public void transferMsg() {
        //判断有没有网络
        if (NetUtils.hasNetWorkConection(getActivity())) {
            Call urlCall = HomeApi.getUrlData(otherCallBack, 0x000);
            Call houseInfoCall = MemberApi.getHouseInfo(otherCallBack, 0x001);
            callList.add(urlCall);
            callList.add(houseInfoCall);
        } else {
            MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
        }
    }
}
