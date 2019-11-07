package com.example.HealthyCampus.framework;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseListAdapter;
import com.example.HealthyCampus.common.widgets.pullrecycler.BaseViewHolder;
import com.example.HealthyCampus.common.widgets.pullrecycler.ItemDecoration.DividerItemDecoration;
import com.example.HealthyCampus.common.widgets.pullrecycler.PullRecycler;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.ILayoutManager;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * OK
 * @param <V>
 * @param <T>
 * @param <D>
 */
public abstract class BaseListFragment<V extends BaseView,T extends BasePresenter<V>,D> extends BaseFragment<V,T> implements PullRecycler.OnRecyclerRefreshListener {

    @BindView(R.id.pullRecycler)
    protected PullRecycler pullRecycler;

    protected BaseListAdapter adapter;
    protected ArrayList<D> mDataList = new ArrayList<>();

    @Override
    public int setContentLayout() {
        return R.layout.fragment_base_list;
    }

    protected abstract void setUpData();

    @Override
    public void setUpView(View view) {
        setUpAdapter();
//        LogUtil.logE("BaseListFragment"+"123456","this:"+this);
//        LogUtil.logE("BaseListFragment"+"123456","getContext():"+getContext());
        pullRecycler.setOnRefreshListener(this);
        pullRecycler.enablePullToRefresh(true);
        pullRecycler.enableLoadMore(true);
        pullRecycler.setLayoutManager(getLayoutManager());
        pullRecycler.addItemDecoration(getItemDecoration());
        pullRecycler.setAdapter(adapter);
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        setUpData();
    }


    protected void setUpAdapter() {
        adapter = new ListAdapter();
    }

    protected ILayoutManager getLayoutManager() {
        return new MyLinearLayoutManager(getContext());
    }

    protected RecyclerView.ItemDecoration getItemDecoration() {
        return new DividerItemDecoration(getContext(), R.drawable.list_divider);
    }

    public class ListAdapter extends BaseListAdapter {

        @Override
        protected BaseViewHolder onCreateNormalViewHolder(ViewGroup parent, int viewType) {
            return getViewHolder(parent, viewType);
        }

        @Override
        protected int getDataCount() {
            return mDataList != null ? mDataList.size() : 0;
        }

        @Override
        protected int getDataViewType(int position) {
            return getItemType(position);
        }

        @Override
        public boolean isSectionHeader(int position) {
            return BaseListFragment.this.isSectionHeader(position);
        }
    }

    protected boolean isSectionHeader(int position) {
        return false;
    }

    protected int getItemType(int position) {
        return 0;
    }

    protected abstract BaseViewHolder getViewHolder(ViewGroup parent, int viewType);
}
