

package com.hitotech.neighbour.receiver;

import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import com.hitotech.neighbour.activity.MainActivity;

public class BlueToothReceiver extends BroadcastReceiver {

    private String btMessage = "";

    //监听蓝牙状态
    @Override
    public void onReceive(Context context, Intent intent) {

        Log.i("TAG---BlueTooth", "接收到蓝牙状态改变广播！！");
        String action = intent.getAction();
        BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
        if (BluetoothDevice.ACTION_ACL_CONNECTED.equals(action)) {
            Toast.makeText(context, "蓝牙已连接！", Toast.LENGTH_LONG).show();
        } else if (BluetoothDevice.ACTION_ACL_DISCONNECTED.equals(action)) {
            Toast.makeText(context, device.getName() + "蓝牙连接已断开！", Toast.LENGTH_LONG).show();
        }
        intent.putExtra("Bluetooth", btMessage);
        intent.setClass(context, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(intent);
    }
}
