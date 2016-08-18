package com.hitotech.neighbour.activity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.CommCallBack;
import com.hitotech.neighbour.entity.UpdateBean;
import com.hitotech.neighbour.entity.UpdateResult;
import com.hitotech.neighbour.http.HomeApi;
import com.hitotech.neighbour.task.DownloadAPKTask;
import com.hitotech.neighbour.utils.DialogUtil;
import com.hitotech.neighbour.utils.MyToast;
import com.jaeger.library.StatusBarUtil;
import com.socks.library.KLog;
import com.umeng.analytics.MobclickAgent;

import cn.jpush.android.api.JPushInterface;

public class SplashActivity extends AppCompatActivity {

    public static final int DOWNLOAD_ERROR = 0;

    public static Handler handler;

    private AlphaAnimation start_anima;
    private View view;

    private UpdateBean updateBean;

    private CommCallBack commCallBack = new CommCallBack() {
        @Override
        public void onSuccess(Object result) {
            UpdateResult updateResult = (UpdateResult) result;
            if (updateResult != null && updateResult.getResult() != null) {
                updateBean = updateResult.getResult();
                if (updateBean.getVersion_compare_result() < 0) {//说明有新版本
                    if (updateBean.getIs_must_update() == 1) {//必须更新
                        new DownloadAPKTask(SplashActivity.this, updateBean.getDownload_url());
                    } else {
                        DialogUtil.showUpdateDialog(SplashActivity.this, updateBean, onPositiveListener, onNegativeListener);
                    }
                } else {
                    KLog.i("版本比对结果:" + updateBean.getVersion_compare_result());
                    startActivity();
                }
            } else {
                startActivity();
            }
        }

        @Override
        public void onFail(Object result) {
            startActivity();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setPush();
        view = View.inflate(this, R.layout.activity_splash, null);
        setContentView(view);
        StatusBarUtil.setColor(this, getResources().getColor(R.color.colorPrimary));
        StatusBarUtil.setTranslucent(this, 60);
        if (!checkNetWork()) {
            showWarnDialog();
        } else {
            initImage();
        }
        initHandler();
    }

    //设置推送
    private void setPush() {

    }

    private void initImage() {
        start_anima = new AlphaAnimation(0.6f, 1.0f);
        start_anima.setDuration(2000);
        view.startAnimation(start_anima);
        start_anima.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                if (checkNetWork()) {
                    checkUpdate();
                } else {
                    MyToast.showShortToast(getResources().getString(R.string.no_network_warn));
                    finish();
                }
            }
        });
    }

    //检查版本
    private void checkUpdate() {
        NeighbourApplication.getHandler().post(new Runnable() {
            @Override
            public void run() {
                HomeApi.checkUpdate(commCallBack, SplashActivity.this);
            }
        });
    }

    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case DOWNLOAD_ERROR:
                        KLog.i("APK下载失败！");
                        startActivity();
                        break;
                }
            }
        };
    }

    private void startActivity() {
        Intent intent = null;
        if (NeighbourApplication.hasBind)
            intent = new Intent(SplashActivity.this, MainActivity.class);
        else
            intent = new Intent(SplashActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_in, R.anim.slide_out_back);
        finish();
    }

    /**
     * 对网络连接状态进行判断
     */
    private boolean checkNetWork() {

        boolean result = false;
        ConnectivityManager mConnectivity = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        // 检查网络连接，如果无网络可用，就不需要进行连网操作等
        NetworkInfo info = mConnectivity.getActiveNetworkInfo();
        if (info == null || !mConnectivity.getBackgroundDataSetting()) {
            result = false;
        } else {
            NetworkInfo[] infos = mConnectivity.getAllNetworkInfo();
            if (info != null) {
                for (int i = 0; i < infos.length; i++) {
                    if (infos[i].getState() == NetworkInfo.State.CONNECTED) {
                        result = true;
                    }
                }
            }
        }
        return result;
    }

    //网络设置对话框
    private void showWarnDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("没有可用的网络").setMessage("是否对网络进行设置?");
        builder.setPositiveButton("是",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        redirectToNETWORK();
                    }
                })
                .setNegativeButton("否",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                dialog.cancel();
                                finish();
                            }
                        }).show();
    }

    //确定按钮点击事件
    private DialogInterface.OnClickListener onPositiveListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            new DownloadAPKTask(SplashActivity.this, updateBean.getDownload_url());
        }
    };

    //取消按钮点击事件
    private DialogInterface.OnClickListener onNegativeListener = new DialogInterface.OnClickListener() {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            startActivity();
        }
    };

    /**
     * 跳转到网络设置界面
     */
    private void redirectToNETWORK() {
        Intent intent = new Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS);
        startActivityForResult(intent, 0);
        return;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        initImage();
    }

    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        JPushInterface.onResume(this);
    }

    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        JPushInterface.onPause(this);
    }
}
