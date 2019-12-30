package com.example.HealthyCampus.module.Find.News.list;

import android.annotation.SuppressLint;
import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.HealthNewsAdapter;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.HealthMessage;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.module.Find.News.HealthNewsMessageManager;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

import static com.chad.library.adapter.base.BaseQuickAdapter.SCALEIN;

public class HealthNewsListFragment extends BaseFragment<HealthNewsListContract.View, HealthNewsListContract.Presenter> implements HealthNewsListContract.View, SwipeRefreshLayout.OnRefreshListener, BaseQuickAdapter.RequestLoadMoreListener {

    @BindView(R.id.recycler_news)
    RecyclerView rvNews;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;


    private HealthNewsAdapter healthNewsAdapter;

    private int belong, page = 1;
    private String[] urls;
    private AnimationDrawable loadAnimation;
    private List<HealthMessage> homeNewsBeans;

    @Override
    public int setContentLayout() {
        return R.layout.find_health_news_list_fragment;
    }

    @Override
    public void setUpView(View view) {
        initHealthNewsRecyclerView();
    }

    @Override
    protected void initData() {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
        if (null != getArguments()) {
            belong = getArguments().getInt("id", 0);
        }
        urls = getResources().getStringArray(R.array.health_news_urls);
        getHealthNew();

    }

    @Override
    protected HealthNewsListContract.Presenter setPresenter() {
        return new HealthNewsListPresenter();
    }

    public static BaseFragment getInstance() {
        return new HealthNewsListFragment();
    }

    @SuppressLint("ResourceType")
    private void initHealthNewsRecyclerView() {
        swipeRefreshLayout.setOnRefreshListener(this);
        swipeRefreshLayout.setColorSchemeResources(
                R.color.colorPrimary,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        homeNewsBeans = new ArrayList<>();
        healthNewsAdapter = new HealthNewsAdapter(mActivity, homeNewsBeans);
        healthNewsAdapter.openLoadAnimation(SCALEIN);
        healthNewsAdapter.setOnLoadMoreListener(this, rvNews);
        rvNews.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvNews.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvNews.setHasFixedSize(true);
        rvNews.setAdapter(healthNewsAdapter);
    }


    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        getHealthNew();
    }


    private void getHealthNew() {
        String url = ConstantValues.BASE_URL_NEWS + urls[belong] + page + ".shtml";
        NetworkManager.getAsync(url, new NetworkManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
                ToastUtil.show(getContext(), "出现异常");
                swipeRefreshLayout.setRefreshing(false);
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, ConstantValues.BASE_URL_NEWS);
                homeNewsBeans = HealthNewsMessageManager.getlist(document);
                if (page == 1) {
                    healthNewsAdapter.setNewData(homeNewsBeans);
                    swipeRefreshLayout.setRefreshing(false);
                } else {
                    healthNewsAdapter.addData(homeNewsBeans);
                    healthNewsAdapter.loadMoreComplete();
                    if (homeNewsBeans.size() == 0) {
                        healthNewsAdapter.loadMoreEnd(true);
                    }
                }
            }
        });
    }


    //以后写成一个控件
    private void loadingData(boolean val) {
        if (val) {
            loadAnimation.start();
            ivLoading.setVisibility(View.VISIBLE);
            NetworkLayout.setVisibility(View.GONE);
        } else {
            loadAnimation.stop();
            ivLoading.setVisibility(View.GONE);
            NetworkLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onRefresh() {
        page = 1;
        getHealthNew();
    }

    @Override
    public void onLoadMoreRequested() {
        page++;
        getHealthNew();
    }


}
