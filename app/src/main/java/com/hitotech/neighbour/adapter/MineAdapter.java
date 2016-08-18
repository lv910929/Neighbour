package com.hitotech.neighbour.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.adapter.viewholder.MineHeadHolder;
import com.hitotech.neighbour.adapter.viewholder.MineItemHolder;
import com.hitotech.neighbour.entity.MineItem;
import com.hitotech.neighbour.entity.MineTitle;
import com.hitotech.neighbour.utils.IntentUtil;
import com.truizlop.sectionedrecyclerview.SectionedRecyclerViewAdapter;

import java.util.List;

/**
 * Created by Lv on 2016/5/14.
 */
public class MineAdapter extends SectionedRecyclerViewAdapter<MineHeadHolder, MineItemHolder, MineHeadHolder> {

    private Context mContext;

    private List<MineTitle> mineTitleList;

    private List<MineItem> mineItemList;

    public MineAdapter(Context mContext, List<MineTitle> mineTitleList) {
        this.mContext = mContext;
        this.mineTitleList = mineTitleList;
    }

    public void updateList(List<MineTitle> mineTitleList) {
        this.mineTitleList = mineTitleList;
        notifyDataSetChanged();
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(mContext);
    }

    @Override
    protected int getSectionCount() {
        return mineTitleList.size();
    }

    @Override
    protected int getItemCountForSection(int section) {
        return mineTitleList.get(section).getMineItems().size();
    }

    @Override
    protected boolean hasFooterInSection(int section) {
        return false;
    }

    @Override
    protected MineHeadHolder onCreateSectionHeaderViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_mine_header, parent, false);
        return new MineHeadHolder(view);
    }

    @Override
    protected MineHeadHolder onCreateSectionFooterViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    protected MineItemHolder onCreateItemViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.list_mine_item, parent, false);
        return new MineItemHolder(view);
    }

    @Override
    protected void onBindSectionHeaderViewHolder(MineHeadHolder holder, int section) {

    }

    @Override
    protected void onBindSectionFooterViewHolder(MineHeadHolder holder, int section) {

    }

    @Override
    protected void onBindItemViewHolder(MineItemHolder holder, int section, int position) {
        mineItemList = mineTitleList.get(section).getMineItems();
        final MineItem mineItem = mineItemList.get(position);
        holder.imageMineItem.setImageResource(mineItem.getResId());
        holder.textItemTitle.setText(mineItem.getTitle());
        holder.textItemContent.setText(mineItem.getContent());
        if (mineItem.getNotifyNum() > 0) {
            holder.textNotifyNum.setVisibility(View.VISIBLE);
            holder.textNotifyNum.setText(mineItem.getNotifyNum() + "");
        } else {
            holder.textNotifyNum.setVisibility(View.GONE);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mineItem.getId() == 8) {//拨打电话
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + mineItem.getContent()));
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    mContext.startActivity(intent);
                } else {
                    IntentUtil.redirectWebView(mContext, mineItem.getTitle(), mineItem.getUrl());
                }
            }
        });
    }
}
