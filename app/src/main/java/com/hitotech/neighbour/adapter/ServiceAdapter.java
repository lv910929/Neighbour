package com.hitotech.neighbour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lv on 2016/5/15.
 */
public class ServiceAdapter extends RecyclerView.Adapter<ServiceAdapter.ViewHolder> {

    private static final int[] IMAGE_RES = {R.drawable.home_notification, R.drawable.home_repair,
            R.drawable.home_payment, R.drawable.home_feedback};

    private static final String[] TITLE_STRING = {"小区通知", "在线报修", "物业缴费", "意见反馈"};

    private static final String[] URL_STRINGS = {
            URLConstant.COMM_URL + URLConstant.NOTICE_URL,
            URLConstant.COMM_URL + URLConstant.REPAIR_SEND_URL,
            URLConstant.COMM_URL + URLConstant.PROPERTY_FEE_URL,
            URLConstant.COMM_URL + URLConstant.FEEDBACK_URL
    };

    private List<String> urlStrings;

    private Context context;

    public ServiceAdapter(Context context) {
        this.context = context;
        urlStrings = new ArrayList<>();
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.grid_service_item, parent, false);
        return new ViewHolder(view);
    }

    public void updateData() {
        if (NeighbourApplication.urlBean != null) {
            urlStrings.add(NeighbourApplication.urlBean.getNoticeUrl());
            urlStrings.add(NeighbourApplication.urlBean.getRepairSendUrl());
            urlStrings.add(NeighbourApplication.urlBean.getPropertyFeeUrl());
            urlStrings.add(NeighbourApplication.urlBean.getSuggestUrl());
        }
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageServiceItem.setImageResource(IMAGE_RES[position]);
        holder.textServiceItem.setText(TITLE_STRING[position]);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(context, TITLE_STRING[position], urlStrings.get(position));
                } else {
                    IntentUtil.redirectWebView(context, TITLE_STRING[position], URL_STRINGS[position]);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return IMAGE_RES.length;
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imageServiceItem;
        TextView textServiceItem;

        public ViewHolder(View itemView) {
            super(itemView);
            imageServiceItem = (ImageView) itemView.findViewById(R.id.image_service_item);
            textServiceItem = (TextView) itemView.findViewById(R.id.text_service_item);
        }
    }
}
