package com.hitotech.neighbour.adapter.viewholder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.hitotech.neighbour.R;

/**
 * Created by Lv on 2016/5/14.
 */
public class MineItemHolder extends RecyclerView.ViewHolder {

    public ImageView imageMineItem;
    public TextView textItemTitle;
    public TextView textItemContent;
    public TextView textNotifyNum;
    public ImageView imageForward;

    public MineItemHolder(View itemView) {
        super(itemView);
        imageMineItem = (ImageView) itemView.findViewById(R.id.image_mine_item);
        textItemTitle = (TextView) itemView.findViewById(R.id.text_item_title);
        textItemContent = (TextView) itemView.findViewById(R.id.text_item_content);
        textNotifyNum = (TextView) itemView.findViewById(R.id.text_notify_num);
        imageForward = (ImageView) itemView.findViewById(R.id.image_forward);
    }
}
