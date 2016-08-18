package com.hitotech.neighbour.activity.base;

import android.os.Message;

import com.hitotech.neighbour.activity.AddressInfoActivity;
import com.hitotech.neighbour.entity.member.HouseBean;

/**
 * Created by Lv on 2016/5/28.
 */
public class BaseSelectActivity extends SwipeActivity {

    protected HouseBean houseBean;

    private void sendResult() {
        Message message = AddressInfoActivity.handler.obtainMessage(0x001);
        message.obj = houseBean;
        message.sendToTarget();
    }

    @Override
    protected void onDestroy() {
        sendResult();
        super.onDestroy();
    }
}
