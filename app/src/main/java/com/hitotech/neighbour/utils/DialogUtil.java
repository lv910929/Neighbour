package com.hitotech.neighbour.utils;


import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.bigkoo.svprogresshud.SVProgressHUD;
import com.hitotech.neighbour.R;
import com.hitotech.neighbour.adapter.SocializeAdapter;
import com.hitotech.neighbour.entity.UpdateBean;
import com.hitotech.neighbour.wxapi.WXHelper;

import me.drakeet.materialdialog.MaterialDialog;

public class DialogUtil {

    private static ProgressDialog progressDialog;

    private static SVProgressHUD progressHUD;

    public static void showInfoDialog(Context context, String title, String message) {
        new AlertDialog.Builder(context)
                .setMessage(message)
                .setTitle(title)
                .setCancelable(false)
                .setPositiveButton("确定", null)
                .show();
    }

    public static void showActionDialog(Context context, String title, String message, DialogInterface.OnClickListener onClickListener) {
        new AlertDialog.Builder(context)
                .setTitle(title)
                .setMessage(message)
                .setCancelable(true)
                .setPositiveButton("确定", onClickListener)
                .setNegativeButton("取消", null)
                .show();
    }

    public static void showWaitDialog(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage(message);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }
    }

    public static void hideWaitDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }

    public static void releaseWaitDialog() {
        progressDialog = null;
    }

    public static void showHubWaitDialog(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            progressHUD = new SVProgressHUD(context);
            progressHUD.showWithStatus(message, SVProgressHUD.SVProgressHUDMaskType.Clear);
        }
    }

    public static void hideHubWaitDialog() {
        if (progressHUD.isShowing()) {
            progressHUD.dismiss();
        }
    }

    public static void showSuccessDialog(Context context, String message) {
        if (!((Activity) context).isFinishing()) {
            progressHUD = new SVProgressHUD(context);
            progressHUD.showSuccessWithStatus(message);
        }
    }

    public static void showUpdateDialog(final Context context, UpdateBean updateBean, DialogInterface.OnClickListener onPositiveClickListener, DialogInterface.OnClickListener onNegativeClickListener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("新版本更新提示");
        builder.setView(getUpdateView(context, updateBean));
        builder.setCancelable(false);
        builder.setPositiveButton("确定", onPositiveClickListener);
        builder.setNegativeButton("取消", onNegativeClickListener);
        builder.show();
    }


    private static View getUpdateView(Context context, UpdateBean updateBean) {

        View contentView = LayoutInflater.from(context).inflate(R.layout.layout_update_view, null);
        TextView textVersionCode = (TextView) contentView.findViewById(R.id.text_version_code);
        TextView textUpdateInfo = (TextView) contentView.findViewById(R.id.text_update_info);
        textVersionCode.setText(updateBean.getLatest_version());
        textUpdateInfo.setText(updateBean.getLatest_version_message());
        return contentView;
    }

    public static void showShareDialog(final Context context, final String title, final String description, final String url) {
        SocializeAdapter socializeAdapter = new SocializeAdapter(context);
        ListView listView = new ListView(context);
        listView.setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT));
        float scale = context.getResources().getDisplayMetrics().density;
        int dpAsPixels = (int) (8 * scale + 0.5f);
        listView.setPadding(0, 0, 0, 0);
        listView.setDividerHeight(0);
        listView.setAdapter(socializeAdapter);

        final MaterialDialog alert = new MaterialDialog(context)
                .setTitle("分享到")
                .setCanceledOnTouchOutside(true)
                .setContentView(listView);
        /*alert.setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               alert.dismiss();
            }
        });*/
        alert.show();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0) {
                    WXHelper.sendWebPageToWX(context, title, description, url, true);
                } else {
                    WXHelper.sendWebPageToWX(context, title, description, url, false);
                }
                alert.dismiss();
            }
        });
    }


}
