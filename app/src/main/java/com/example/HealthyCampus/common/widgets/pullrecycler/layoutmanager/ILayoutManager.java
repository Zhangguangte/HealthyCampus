package com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager;

import android.support.v7.widget.RecyclerView;

import com.example.HealthyCampus.common.widgets.pullrecycler.BaseListAdapter;

/**
 * OK
 */
public interface ILayoutManager {
    RecyclerView.LayoutManager getLayoutManager();
    int findFirstVisiblePosition();
    int findLastVisiblePosition();
    void setUpAdapter(BaseListAdapter adapter);
}