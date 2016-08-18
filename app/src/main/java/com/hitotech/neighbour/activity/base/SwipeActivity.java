package com.hitotech.neighbour.activity.base;

import android.os.Bundle;

import com.jude.swipbackhelper.SwipeBackHelper;

/**
 * Created by Lv on 2016/5/13.
 */
public class SwipeActivity extends BaseActivity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SwipeBackHelper.onCreate(this);
        SwipeBackHelper.getCurrentPage(this)
                .setSwipeBackEnable(true)
                .setSwipeBackEnable(true)//on-off
                .setSwipeEdge(200)//set the touch area。200 mean only the left 200px of screen can touch to begin swipe.
                .setSwipeEdgePercent(0.2f)//0.2 mean left 20% of screen can touch to begin swipe.
                .setSwipeSensitivity(0.5f)//sensitiveness of the gesture。0:slow  1:sensitive
                .setClosePercent(0.8f);//close activity when swipe over this
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        SwipeBackHelper.onPostCreate(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SwipeBackHelper.onDestroy(this);
    }
}
