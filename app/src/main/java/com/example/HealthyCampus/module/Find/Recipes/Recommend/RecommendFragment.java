package com.example.HealthyCampus.module.Find.Recipes.Recommend;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.RecommendAdapter;
import com.example.HealthyCampus.common.network.vo.FoodRecommendVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.RecycleOnscrollListener;
import com.example.HealthyCampus.framework.BaseFragment;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class RecommendFragment extends BaseFragment<RecommendContract.View, RecommendContract.Presenter> implements RecommendContract.View {

    @BindView(R.id.recycler_recommend)
    RecyclerView rvRecommend;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;

    private RecommendAdapter recommendAdapter;
    private List<FoodRecommendVo> mDataList = new LinkedList<>();

    private AnimationDrawable loadAnimation;


    @Override
    public int setContentLayout() {
        return R.layout.find_recipes_recommend_fragment;
    }

    @Override
    public void setUpView(View view) {
        initRecommendRecyclerView();
    }

    @Override
    protected void initData() {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
        mDataList.clear();
        mPresenter.getRecommendRecipes();
    }

    @Override
    protected RecommendContract.Presenter setPresenter() {
        return new RecommendPresenter();
    }

    public static BaseFragment getInstance() {
        return new RecommendFragment();
    }

    private void initRecommendRecyclerView() {
        recommendAdapter = new RecommendAdapter(mActivity, mDataList);

        GridLayoutManager layoutManager = new GridLayoutManager(getActivity(), 2);
        layoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                //返回的是Item的跨度
                return position % 3 == 2 || position == mDataList.size() ? 2 : 1;
//                Log.e("RecommendFra" + "123456", "position:" + position);
            }
        });
        //刷新
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                mDataList.clear();
                mPresenter.getRecommendRecipes();
            }
        });
        //下滑
        rvRecommend.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (recommendAdapter.isLoad()) {
                    mPresenter.getRecommendRecipes();
                }
            }
        });
        rvRecommend.setLayoutManager(layoutManager);
        rvRecommend.setHasFixedSize(true);
        rvRecommend.setAdapter(recommendAdapter);

    }

    @Override
    public void showRecommendSuccess(List<FoodRecommendVo> recommendFoodBeans) {
        loadingData(false);
        emptyLayout.setVisibility(View.GONE);

        mDataList.addAll(recommendFoodBeans);
        recommendAdapter.notifyDataSetChanged();
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1006) {
                    ToastUtil.show(mActivity, "无数据");
                } else {
                    ToastUtil.show(mActivity, "未知错误1:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(mActivity, "未知错误2:" + throwable.getMessage());
        }
        emptyLayout.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
        recommendAdapter.setLoad(false);
        swipeRefreshLayout.setRefreshing(false);
    }

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


    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getRecommendRecipes();
    }
}
