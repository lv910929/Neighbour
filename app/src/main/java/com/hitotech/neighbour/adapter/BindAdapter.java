package com.hitotech.neighbour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.adapter.listener.OnItemSelectListener;
import com.hitotech.neighbour.entity.bind.BindItem;

import java.util.List;

/**
 * Created by Lv on 2016/5/23.
 */
public class BindAdapter extends RecyclerView.Adapter<BindAdapter.ViewHolder> {

    private List<BindItem> bindItemList;

    private Context context;

    private OnItemSelectListener onItemSelectListener;

    public BindAdapter(List<BindItem> bindItemList, Context context) {
        this.bindItemList = bindItemList;
        this.context = context;
    }

    public void updateList(List<BindItem> bindItemList){
        this.bindItemList = bindItemList;
        notifyDataSetChanged();
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    public void setOnItemSelectListener(OnItemSelectListener listener) {
        this.onItemSelectListener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_city_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final BindItem bindItem = bindItemList.get(position);
        holder.textCityName.setText(bindItem.getName());
        holder.textCityName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemSelectListener != null) {
                    onItemSelectListener.onItemSelect(position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return bindItemList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        TextView textCityName;

        public ViewHolder(View itemView) {
            super(itemView);
            textCityName = (TextView) itemView.findViewById(R.id.text_city_name);
        }
    }
}
