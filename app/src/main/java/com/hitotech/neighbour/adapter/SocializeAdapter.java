package com.hitotech.neighbour.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.widget.BaseViewHolder;

/**
 * Created by Lv on 2016/4/3.
 */
public class SocializeAdapter extends BaseAdapter{

    private static final String[] SOCIALIZE_STRINGS = {"微信","朋友圈"};

    private static final int[] SOCIALIZE_IMGS ={R.drawable.socialize_wechat,R.drawable.socialize_wxcircle};

    private Context context;

    public SocializeAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return SOCIALIZE_STRINGS.length;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView==null) {
            convertView= LayoutInflater.from(context).inflate(R.layout.list_socialize_item, parent, false);
        }
        ImageView socializeImage = BaseViewHolder.get(convertView, R.id.image_socialize);
        TextView socializeText = BaseViewHolder.get(convertView,R.id.text_socialize);
        socializeImage.setImageResource(SOCIALIZE_IMGS[position]);
        socializeText.setText(SOCIALIZE_STRINGS[position]);
        return convertView;
    }
}
