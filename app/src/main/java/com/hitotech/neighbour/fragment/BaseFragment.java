package com.hitotech.neighbour.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import com.hitotech.neighbour.R;

import java.util.ArrayList;
import java.util.List;

import retrofit.Call;

/**
 * Created by Lv on 2016/3/21.
 */
public class BaseFragment extends Fragment {

    protected List<Call> callList;

    protected Activity mActivity;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mActivity = getActivity();
        callList = new ArrayList<>();
    }

    protected void initUI(View view) {

    }

    protected Toolbar initToolBar(View view, int resId, String title) {
        Toolbar toolbar = (Toolbar) view.findViewById(R.id.toolbar_comm);
        TextView titleText = (TextView) view.findViewById(R.id.text_title);
        toolbar.setTitle("");
        toolbar.inflateMenu(resId);
        titleText.setText(title);
        return toolbar;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (callList != null && callList.size() > 0) {
            for (Call call : callList) {
                if (call != null) {
                    call.cancel();
                }
            }
        }
    }
}
