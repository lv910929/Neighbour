package com.hitotech.neighbour.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.FrameLayout;

import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.hitotech.neighbour.R;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.entity.TabEntity;
import com.hitotech.neighbour.fragment.HelpFragment;
import com.hitotech.neighbour.fragment.HomeFragment;
import com.hitotech.neighbour.fragment.MineFragment;
import com.hitotech.neighbour.utils.IHandler;
import com.hitotech.neighbour.utils.IntentUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.jaeger.library.StatusBarUtil;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnTabSelectListener, IHandler {

    public static Handler handler;
    private FragmentManager fragmentManager;
    private HomeFragment homeFragment;
    private HelpFragment helpFragment;
    private MineFragment mineFragment;

    private String[] mTitles = {"劲邻", "邻里互助", "我"};

    private int[] mIconUnselectIds = {
            R.drawable.tab_home_unselect, R.drawable.tab_help_unselect,
            R.drawable.tab_me_unselect};
    private int[] mIconSelectIds = {
            R.drawable.tab_home_select, R.drawable.tab_help_select,
            R.drawable.tab_me_select};

    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private CommonTabLayout mTabLayout;
    private FrameLayout frameMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        StatusBarUtil.setTranslucent(this, 60);
        fragmentManager = getSupportFragmentManager();
        initData();
        initUI();
        initHandler();
    }

    private void initData() {
        NeighbourApplication.isAddHouse = true;
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
    }

    private void initUI() {
        frameMain = (FrameLayout) findViewById(R.id.frame_main);
        mTabLayout = (CommonTabLayout) findViewById(R.id.layout_tab);

        mTabLayout.setTabData(mTabEntities);
        mTabLayout.setCurrentTab(0);
        setTabSelection(0);
        mTabLayout.setOnTabSelectListener(this);
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 0x001:
                        if (mineFragment != null) {
                            mineFragment.transferMsg();
                        }
                        if (homeFragment != null) {
                            homeFragment.transferMsg();
                        }
                        break;
                    case 0x002:
                        if (mineFragment != null) {
                            mineFragment.transferMsg();
                        }
                        break;
                    case 0x003:
                        finish();
                        break;
                    case 0x004:
                        if (NeighbourApplication.urlBean != null) {
                            IntentUtil.redirectWebView(MainActivity.this, "我的住址", NeighbourApplication.urlBean.getAddressUrl());
                        } else {
                            IntentUtil.redirectWebView(MainActivity.this, "我的住址", URLConstant.COMM_URL + URLConstant.ADDRESS_URL);
                        }
                        break;
                }
            }
        };
    }

    /**
     * 切换fragment
     */
    private void setTabSelection(int index) {
        // 开启一个Fragment事务
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                NeighbourApplication.isMineTab = false;
                if (homeFragment == null) {
                    homeFragment = HomeFragment.getInstance();
                    transaction.add(R.id.frame_main, homeFragment);
                } else {
                    transaction.show(homeFragment);
                }
                break;
            case 1:
                NeighbourApplication.isMineTab = false;
                if (helpFragment == null) {
                    helpFragment = HelpFragment.getInstance();
                    transaction.add(R.id.frame_main, helpFragment);
                } else {
                    transaction.show(helpFragment);
                }
                break;
            case 2:
                NeighbourApplication.isMineTab = true;
                if (mineFragment == null) {
                    mineFragment = MineFragment.getInstance();
                    transaction.add(R.id.frame_main, mineFragment);
                } else {
                    transaction.show(mineFragment);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 将所有的Fragment都置为隐藏状态。
     */
    private void hideFragments(FragmentTransaction transaction) {
        if (homeFragment != null) {
            transaction.hide(homeFragment);
        }
        if (helpFragment != null) {
            transaction.hide(helpFragment);
        }
        if (mineFragment != null) {
            transaction.hide(mineFragment);
        }
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    public void onTabSelect(int position) {
        setTabSelection(position);
    }

    @Override
    public void onTabReselect(int position) {

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

    @Override
    public Handler getHandler() {
        return handler;
    }
}
