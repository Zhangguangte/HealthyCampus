package com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;

import com.example.HealthyCampus.common.widgets.pullrecycler.BaseListAdapter;

/**
 * OK
 */
public class MyLinearLayoutManager extends LinearLayoutManager implements ILayoutManager {

    public MyLinearLayoutManager(Context context) {
        super(context);
    }

    public MyLinearLayoutManager(Context context, int orientation, boolean reverseLayout) {
        super(context, orientation, reverseLayout);
    }

    public MyLinearLayoutManager(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

//    @Override
//    public void onLayoutChildren(RecyclerView.Recycler recycler, RecyclerView.State state) {
//        try {
//            super.onLayoutChildren(recycler, state);
//        } catch (Exception e) {
//            Log.e("MyLinearLayou" + "123456", "meet a IOOBE in RecyclerView");
//        }
//    }

    @Override
    public RecyclerView.LayoutManager getLayoutManager() {
        return this;
    }

    @Override
    public int findFirstVisiblePosition() {
        return findFirstVisibleItemPosition();
    }

    @Override
    public int findLastVisiblePosition() {
        return findLastVisibleItemPosition();
    }

    @Override
    public void setUpAdapter(BaseListAdapter adapter) {

    }
}
