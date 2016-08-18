package com.hitotech.neighbour.adapter;

import android.content.Context;

import com.hitotech.neighbour.entity.IdentifyItem;

import java.util.List;

/**
 * Created by Lv on 2016/5/18.
 */
public class IdentifyAdapter extends RadioAdapter<IdentifyItem>{

    public IdentifyAdapter(Context context, List<IdentifyItem> items) {
        super(context, items);
    }

    @Override
    public void onBindViewHolder(RadioAdapter.ViewHolder viewHolder, int i) {
        super.onBindViewHolder(viewHolder, i);
        viewHolder.mText.setText(mItems.get(i).getTitle());
    }
}
