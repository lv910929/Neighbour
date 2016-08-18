package com.hitotech.neighbour.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitotech.neighbour.R;
import com.malinskiy.materialicons.IconDrawable;
import com.malinskiy.materialicons.Iconify;
import com.vlonjatg.progressactivity.ProgressActivity;

public abstract class BaseLoadingFragment extends BaseFragment {

    private ProgressActivity mProgressActivity;
    protected Drawable errorDrawable;
    protected Drawable emptyDrawable;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mProgressActivity = (ProgressActivity) inflater.inflate(R.layout.fragment_base_loading, container, false);
        mProgressActivity.addView(onCreateContentView(inflater, mProgressActivity, savedInstanceState));
        errorDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_wifi_off).colorRes(R.color.colorAccent);
        emptyDrawable = new IconDrawable(getContext(), Iconify.IconValue.zmdi_shopping_cart).colorRes(R.color.colorAccent);
        return mProgressActivity;
    }

    abstract View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState);

    protected void showLoading() {
        mProgressActivity.showLoading();
    }

    protected void showContent(){
        mProgressActivity.showContent();
    }

    protected void showError(Drawable errorImageDrawable, String errorTextTitle, String errorTextContent, String errorButtonText, View.OnClickListener onClickListener) {
        mProgressActivity.showError(errorImageDrawable, errorTextTitle, errorTextContent, errorButtonText, onClickListener);
    }

    protected void getData(){

    }

    protected View.OnClickListener mErrorRetryListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            showLoading();
            getData();
        }
    };
}
