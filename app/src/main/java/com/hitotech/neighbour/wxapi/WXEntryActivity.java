package com.hitotech.neighbour.wxapi;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.WindowManager;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.modelmsg.SendMessageToWX;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;

/**
 * Created by Lv on 2016/4/4.
 */
public class WXEntryActivity extends Activity implements IWXAPIEventHandler {

    private String code = "";

    private SVProgressHUD progressHUD;

    private void handleIntent(Intent paramIntent) {
        NeighbourApplication.api.handleIntent(paramIntent, this);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        handleIntent(getIntent());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        // TODO Auto-generated method stub
        super.onNewIntent(intent);
        setIntent(intent);
        handleIntent(intent);
    }

    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        SendMessageToWX res;
        if (baseResp instanceof SendMessageToWX.Resp) {
            finish();
            return;
        }
        switch (baseResp.errCode) {
            case BaseResp.ErrCode.ERR_OK://用户同意
                code = ((SendAuth.Resp) baseResp).code; //即为所需的code
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED://用户拒绝授权
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL://用户取消
                break;
        }
        finish();
    }
}
