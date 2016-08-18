package com.hitotech.neighbour.utils;

import android.widget.Toast;

import com.hitotech.neighbour.app.NeighbourApplication;

public class MyToast {

    private static Toast mToast = null;

    public static void showShortToast(String text) {
        if (mToast == null) {
            mToast = Toast.makeText(NeighbourApplication.getInstance(), text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    public static void showLongToast(String text){
        if (mToast == null) {
            mToast = Toast.makeText(NeighbourApplication.getInstance(), text, Toast.LENGTH_LONG);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_LONG);
        }
        mToast.show();
    }

}
