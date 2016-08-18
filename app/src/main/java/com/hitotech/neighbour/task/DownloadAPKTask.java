package com.hitotech.neighbour.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.hitotech.neighbour.data.NeighbourConfig;
import com.hitotech.neighbour.utils.MessageUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;

public class DownloadAPKTask extends BaseTask {

    private ProgressDialog progressDialog;

    private File apkFile;

    public static String sdPath;

    static {
        sdPath = NeighbourConfig.SDCARD_ROOT_PATH + NeighbourConfig.SAVE_PATH_IN_SDCARD;
    }

    private String downloadUrl;

    public DownloadAPKTask(Context context, String downloadUrl) {
        super(context);
        this.downloadUrl = downloadUrl;
        execute();
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        showDialog();
    }

    @Override
    protected void onProgressUpdate(Integer... values) {
        super.onProgressUpdate(values);
        progressDialog.setProgress(values[0]);
    }

    @Override
    protected Integer doInBackground(String... params) {

        Integer result = 0;
        try {
            URL u = new URL(downloadUrl);
            HttpURLConnection connection = (HttpURLConnection) u.openConnection();
            connection.setConnectTimeout(5000);
            connection.setRequestMethod("GET");
            File file = new File(sdPath);
            if (!file.exists()) {
                file.mkdirs();
            }
            apkFile = new File(file.getPath(), "neighbour.apk");
            apkFile.createNewFile();
            apkFile.setWritable(true);
            if (connection.getResponseCode() == 200) {
                int max = connection.getContentLength();
                //设置进度条对话框的最大值
                progressDialog.setMax(max);
                int count = 0;
                InputStream is = connection.getInputStream();
                FileOutputStream fos = new FileOutputStream(apkFile);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    count = count + len;//设置进度条对话框进度
                    progressDialog.setProgress(count);
                }
                is.close();
                fos.close();
            }
            result = 1;
        } catch (MalformedURLException e) {
            result = -1;
        } catch (ProtocolException e) {
            result = -1;
        } catch (FileNotFoundException e) {
            result = -1;
            e.printStackTrace();
        } catch (IOException e) {
            result = -1;
            e.printStackTrace();
        }
        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        super.onPostExecute(result);
        if (result > 0) {
            if (null != progressDialog && progressDialog.isShowing()) {
                progressDialog.dismiss();
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
                context.startActivity(intent);
                ((Activity) context).finish();
            }
        } else {
            if (null != progressDialog && progressDialog.isShowing()) {
                progressDialog.dismiss();
            }
            MessageUtil.sendDownloadError();
        }
    }

    private void showDialog() {
        progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("正在下载新版本,请耐心等待...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        progressDialog.setMax(100);
        progressDialog.setCancelable(false);
        progressDialog.show();
    }
}
