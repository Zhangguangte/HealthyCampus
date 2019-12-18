package com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.DiagnosisSortAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.RecycleOnscrollListener;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.VISIBLE;


public class DiseaseSortActivity extends BaseActivity<DiseaseSortContract.View, DiseaseSortContract.Presenter> implements DiseaseSortContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    @BindView(R.id.recycler_encyclopedia)
    RecyclerView rvEncyclopedia;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;

    private DiagnosisSortAdapter diagnosisSortAdapter;
    private List<DiseaseSortVo> mDataList = new LinkedList<>();
    private int row = 0;
    private int type = 0;
    private String title = "";
    private AnimationDrawable loadAnimation;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_self_diagnosis_sort);
    }

    @Override
    protected DiseaseSortContract.Presenter createPresenter() {
        return new DiseaseSortPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initEncyclopediaView();
    }

    private void setCustomActionBar() {
        tvTitle.setText(getIntent().getStringExtra("title"));
        ivBack.setVisibility(VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
        title = getIntent().getStringExtra("title");
        type = getIntent().getIntExtra("type", 0);
        mPresenter.getDiseaseSort(row, title, type);
    }


    private void initEncyclopediaView() {
        //下滑
        rvEncyclopedia.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (diagnosisSortAdapter.isLoad()) {
                    mPresenter.getDiseaseSort(++row, title, type);
                }
            }
        });
        diagnosisSortAdapter = new DiagnosisSortAdapter(getContext(), mDataList);
        rvEncyclopedia.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvEncyclopedia.setHasFixedSize(true);
        rvEncyclopedia.setAdapter(diagnosisSortAdapter);
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showError(Throwable throwable) {

        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1006) {
                    diagnosisSortAdapter.setLoad(false);
                    ToastUtil.show(this, "无数据");
                } else {
                    ToastUtil.show(this, "未知错误1:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        loadingData(false);
        emptyLayout.setVisibility(mDataList.size() == 0 ? View.VISIBLE : View.GONE);
        dismissProgressDialog();
    }

    @Override
    public void showDiagnosisSortListSuccess(List<DiseaseSortVo> diseasSortVos) {
        loadingData(false);
        emptyLayout.setVisibility(View.GONE);
        if(diseasSortVos.size()<20)
        {
            diagnosisSortAdapter.setLoad(false);
        }
        mDataList.addAll(diseasSortVos);
        diagnosisSortAdapter.notifyDataSetChanged();
        dismissProgressDialog();
    }

    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        row = 0;
        loadingData(true);
        mPresenter.getDiseaseSort(row, title, type);
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
}
