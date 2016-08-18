package com.hitotech.neighbour.task;


import android.content.Context;
import android.os.AsyncTask;

import com.hitotech.neighbour.utils.DialogUtil;

public abstract class BaseTask extends AsyncTask<String, Integer, Integer> {

    protected Context context;

    public BaseTask(Context context) {
        this.context = context;
    }

    @Override
    protected void onPostExecute(Integer result) {
        DialogUtil.hideWaitDialog();

    }
}
