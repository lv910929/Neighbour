package com.hitotech.neighbour.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.utils.IntentUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Lv on 2016/5/17.
 */
public class HelpAdapter extends RecyclerView.Adapter<HelpAdapter.ViewHolder> {

    private static final int[] IMAGE_RES = {R.drawable.help_clean_bg, R.drawable.help_repair_bg,
            R.drawable.help_massage_bg, R.drawable.help_fitness_bg,
            R.drawable.help_manicure_bg, R.drawable.help_dog_bg};

    private static final String[] TITLE_STRING = {"家庭保洁", "电脑维修", "推拿按摩", "健身私教", "美甲化妆", "宠物看管"};

    private static final String[] URL_STRINGS = {
            URLConstant.COMM_URL + URLConstant.HOUSE_CLEAN_URL,
            URLConstant.COMM_URL + URLConstant.COMPUTER_REPAIR_URL,
            URLConstant.COMM_URL + URLConstant.MASSAGE_URL,
            URLConstant.COMM_URL + URLConstant.FITNESS_URL,
            URLConstant.COMM_URL + URLConstant.MANICURE_URL,
            URLConstant.COMM_URL + URLConstant.PET_CARE_URL
    };

    private List<String> urlStringList;

    private Context context;

    public HelpAdapter(Context context) {
        this.context = context;
        urlStringList = new ArrayList<>();
        if (NeighbourApplication.urlBean != null) {
            initData();
        }
    }

    private void initData() {
        urlStringList.add(NeighbourApplication.urlBean.getHouseCleanUrl());
        urlStringList.add(NeighbourApplication.urlBean.getComputerRepairUrl());
        urlStringList.add(NeighbourApplication.urlBean.getMassageUrl());
        urlStringList.add(NeighbourApplication.urlBean.getFitnessUrl());
        urlStringList.add(NeighbourApplication.urlBean.getManicureUrl());
        urlStringList.add(NeighbourApplication.urlBean.getPetCareUrl());
    }

    protected LayoutInflater getLayoutInflater() {
        return LayoutInflater.from(context);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = getLayoutInflater().inflate(R.layout.grid_help_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        holder.imageHelpItem.setImageResource(IMAGE_RES[position]);
        holder.textHelpItem.setText(TITLE_STRING[position]);
        holder.layoutItemHelp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(context, TITLE_STRING[position], urlStringList.get(position));
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
        LinearLayout layoutItemHelp;
        ImageView imageHelpItem;
        TextView textHelpItem;

        public ViewHolder(View itemView) {
            super(itemView);
            layoutItemHelp = (LinearLayout) itemView.findViewById(R.id.layout_item_help);
            imageHelpItem = (ImageView) itemView.findViewById(R.id.image_help_item);
            textHelpItem = (TextView) itemView.findViewById(R.id.text_help_item);
        }
    }

}
