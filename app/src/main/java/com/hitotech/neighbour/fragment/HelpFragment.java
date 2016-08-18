package com.hitotech.neighbour.fragment;

import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.hitotech.neighbour.R;
import com.hitotech.neighbour.adapter.HelpAdapter;
import com.hitotech.neighbour.app.NeighbourApplication;
import com.hitotech.neighbour.callback.UpdateFragListener;
import com.hitotech.neighbour.data.URLConstant;
import com.hitotech.neighbour.utils.IntentUtil;

/**
 * Created by Lv on 2016/5/14.
 */
public class HelpFragment extends BaseLoadingFragment implements Toolbar.OnMenuItemClickListener, UpdateFragListener {

    private Toolbar toolbar;
    private RecyclerView recyclerHelp;
    private HelpAdapter helpAdapter;

    public static HelpFragment getInstance() {
        HelpFragment helpFragment = new HelpFragment();
        return helpFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    View onCreateContentView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_help, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initUI(view);
    }

    @Override
    protected void initUI(View view) {
        super.initUI(view);
        toolbar = initToolBar(view, R.menu.menu_help, "邻里互助");
        recyclerHelp = (RecyclerView) view.findViewById(R.id.recycler_help);

        setRecyclerHelp();
        toolbar.setOnMenuItemClickListener(this);
    }

    private void setRecyclerHelp() {
        recyclerHelp.setLayoutManager(new GridLayoutManager(getContext(), 3));
        helpAdapter = new HelpAdapter(getContext());
        recyclerHelp.setAdapter(helpAdapter);
    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_push:
                if (NeighbourApplication.urlBean != null) {
                    IntentUtil.redirectWebView(getActivity(), "发布", NeighbourApplication.urlBean.getServiceSendUrl());
                } else {
                    IntentUtil.redirectWebView(getActivity(), "发布", URLConstant.COMM_URL + URLConstant.SERVICE_SEND_URL);
                }
                return true;
        }
        return false;
    }

    @Override
    public void transferMsg() {

    }
}
