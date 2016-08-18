package com.hitotech.neighbour.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Vibrator;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.activity.base.SwipeActivity;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.utils.BluetoothManager;
import com.hitotech.neighbour.utils.MyToast;

public class OpenDoorActivity extends SwipeActivity implements View.OnClickListener {

    private static final int REQUEST_CODE_BLUETOOTH_ON = 1313;
    private static final int BLUETOOTH_DISCOVERABLE_DURATION = 120;

    private static final Integer REQUEST_OPEN_BLUETOOTH = 11;
    private static final Integer REQUEST_SENSOR_SHAKE = 10;
    private static Handler handler;

    private SensorManager sensorManager;
    private Vibrator vibrator;

    private Button btnToBluetooth;
    private boolean hasOpenBluetooth;
    private boolean remindOpenBlueTooth;
    private boolean openBlueTooth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_door);
        iniData();
        initUI();
        initHandler();
    }

    private void iniData() {
        sensorManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        remindOpenBlueTooth = NeighbourApplication.sharedPreferences.getBoolean("remindOpenBlueTooth", true);
        openBlueTooth = NeighbourApplication.sharedPreferences.getBoolean("openBlueTooth", false);
    }

    private void initUI() {
        toolbar = (Toolbar) findViewById(R.id.toolbar_comm);
        textTitle = (TextView) findViewById(R.id.text_title);
        btnToBluetooth = (Button) findViewById(R.id.btn_to_bluetooth);

        initToolBar("手机开门");
        initBlueTooth();
        btnToBluetooth.setOnClickListener(this);
    }

    private void initBlueTooth() {
        if (BluetoothManager.isBluetoothEnabled()) {
            btnToBluetooth.setVisibility(View.GONE);
            MyToast.showShortToast("蓝牙已开启");
            hasOpenBluetooth = true;
        } else {
            btnToBluetooth.setVisibility(View.VISIBLE);
            hasOpenBluetooth = false;
            if ((BluetoothManager.isBluetoothSupported()) && (!BluetoothManager.isBluetoothEnabled())) {
                this.turnOnBluetooth();
            }
        }
    }

    /**
     * 弹出系统弹框提示用户打开 Bluetooth
     */
    private void turnOnBluetooth() {
        if (remindOpenBlueTooth) {
            showRemindDialog();
        } else {
            if (openBlueTooth) {//说明记住每次自动开启蓝牙
                BluetoothManager.turnOnBluetooth();
            }
        }
        checkBlueTooth();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_to_bluetooth:
                Intent intent = new Intent(Settings.ACTION_BLUETOOTH_SETTINGS);
                startActivityForResult(intent, REQUEST_OPEN_BLUETOOTH);
                break;
        }
    }

    private void checkBlueTooth() {
        if (BluetoothManager.isBluetoothEnabled()) {
            btnToBluetooth.setVisibility(View.GONE);
            MyToast.showShortToast("蓝牙已开启");
            hasOpenBluetooth = true;
        } else {
            btnToBluetooth.setVisibility(View.VISIBLE);
            MyToast.showShortToast("蓝牙不可用，去设置");
            hasOpenBluetooth = false;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_OPEN_BLUETOOTH) {
            checkBlueTooth();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sensorManager != null) {// 注册监听器
            // 第一个参数是Listener，第二个参数是所得传感器类型，第三个参数值获取传感器信息的频率
            sensorManager.registerListener(sensorEventListener, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER), SensorManager.SENSOR_DELAY_NORMAL);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (sensorManager != null) {// 取消监听器
            sensorManager.unregisterListener(sensorEventListener);
        }
    }

    /**
     * 重力感应监听
     */
    private SensorEventListener sensorEventListener = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent event) {
            // 传感器信息改变时执行该方法
            float[] values = event.values;
            float x = values[0]; // x轴方向的重力加速度，向右为正
            float y = values[1]; // y轴方向的重力加速度，向前为正
            float z = values[2]; // z轴方向的重力加速度，向上为正
            // 一般在这三个方向的重力加速度达到40就达到了摇晃手机的状态。
            int medumValue = 19;// 如果不敏感请自行调低该数值,低于10的话就不行了,因为z轴上的加速度本身就已经达到10了
            if (Math.abs(x) > medumValue || Math.abs(y) > medumValue || Math.abs(z) > medumValue) {
                vibrator.vibrate(200);
                Message msg = new Message();
                msg.what = REQUEST_SENSOR_SHAKE;
                handler.sendMessage(msg);
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {

        }
    };

    /**
     * 动作执行
     */
    private void initHandler() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                switch (msg.what) {
                    case 10:
                        if (hasOpenBluetooth) {
                            MyToast.showShortToast("检测到摇晃，执行操作！");
                        } else {
                            MyToast.showShortToast("请先开启蓝牙！");
                        }
                        break;
                }
            }
        };
    }

    private CheckBox checkboxRemember;

    private View getBlueToothView() {
        View contentView = LayoutInflater.from(OpenDoorActivity.this).inflate(R.layout.layout_bluetooth_view, null);
        checkboxRemember = (CheckBox) contentView.findViewById(R.id.checkbox_remember);
        return contentView;
    }

    private void showRemindDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("提示");
        builder.setView(getBlueToothView());
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                BluetoothManager.turnOnBluetooth();
                saveRemindStatus();
                saveOpenBlueTooth(true);
                checkBlueTooth();
            }
        });
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                saveRemindStatus();
                saveOpenBlueTooth(false);
                checkBlueTooth();
            }
        });
        builder.show();
    }

    //记住提醒
    private void saveRemindStatus() {

        if (checkboxRemember.isChecked()) {
            remindOpenBlueTooth = false;
        } else {
            remindOpenBlueTooth = true;
        }
        SharedPreferences.Editor editor = NeighbourApplication.sharedPreferences.edit();
        editor.putBoolean("remindOpenBlueTooth", remindOpenBlueTooth);
        editor.commit();
    }

    //记住开启
    private void saveOpenBlueTooth(boolean openBlueTooth) {
        SharedPreferences.Editor editor = NeighbourApplication.sharedPreferences.edit();
        editor.putBoolean("openBlueTooth", openBlueTooth);
        editor.commit();
    }

}
