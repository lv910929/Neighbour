package com.hitotech.neighbour.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import com.hitotech.neighbour.activity.WebviewActivity;
import com.hitotech.neighbour.activity.base.SwipeActivity;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.utils.MyToast;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

public class WXPayEntryActivity extends SwipeActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private void handleIntent(Intent paramIntent) {
        NeighbourApplication.api.handleIntent(paramIntent, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_wxpay_entry);
        /*toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        initToolBar("支付结果");*/
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d(TAG, "onPayFinish, errCode = " + baseResp.errCode);
        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            int result = 0;
            switch (baseResp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    MyToast.showShortToast("支付成功");
                    result = 1;
                    break;
                default:
                    MyToast.showShortToast(baseResp.errStr);
                    result = 2;
                    break;
            }
            Message message = WebviewActivity.handler.obtainMessage(WebviewActivity.WX_PAY_FLAG);
            message.arg1 = result;
            onBackPressed();
        }
    }
}
