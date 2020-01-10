package com.example.HealthyCampus.module.Find.Diagnosis.List;

import android.graphics.drawable.AnimationDrawable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.DiagnosisListAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseFragment;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class DiagnosisListFragment extends BaseFragment<DiagnosisListContract.View, DiagnosisListContract.Presenter> implements DiagnosisListContract.View {

    @BindView(R.id.recycler_diagnosis)
    RecyclerView rvDiagnosis;
    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;


    private DiagnosisListAdapter diagnosisListAdapter;
    private List<DiseaseSortListVo> mDataList = new LinkedList<>();

    private int belong;
    private AnimationDrawable loadAnimation;

    @Override
    public int setContentLayout() {
        return R.layout.find_self_diagnosis_list_fragment;
    }

    @Override
    public void setUpView(View view) {
        initDiagnosisListRecyclerView();
    }

    @Override
    protected void initData() {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();

        mDataList.clear();
        assert getArguments() != null;
        belong = getArguments().getInt("id", 0);
        diagnosisListAdapter.setType(getArguments().getInt("id", 0));
        mPresenter.getDiseaseSortList(belong);
    }

    @Override
    protected DiagnosisListContract.Presenter setPresenter() {
        return new DiagnosisListPresenter();
    }

    public static BaseFragment getInstance() {
        return new DiagnosisListFragment();
    }

    private void initDiagnosisListRecyclerView() {
        swipeRefreshLayout.setEnabled(false);
        diagnosisListAdapter = new DiagnosisListAdapter(mActivity, mDataList);
        rvDiagnosis.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        rvDiagnosis.setHasFixedSize(true);
        rvDiagnosis.setAdapter(diagnosisListAdapter);

    }

    @Override
    public void showDiagnosisSortSuccess(List<DiseaseSortListVo> diseaseSortListVos) {
        loadingData(false);
        emptyLayout.setVisibility(View.GONE);
        mDataList.addAll(diseaseSortListVos);
        diagnosisListAdapter.notifyDataSetChanged();
    }

    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                switch (response.code) {
                    case 999:
                        ToastUtil.show(getContext(), "无数据");
                        break;
                    case 1000:
                        ToastUtil.show(getContext(), "Bad Server");
                        break;
                    case 1003:
                        ToastUtil.show(getContext(), "Invalid Parameter");
                        break;
                    default:
                        ToastUtil.show(getContext(), "未知错误1:" + throwable.getMessage());
                        break;
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(mActivity, "未知错误2:" + throwable.getMessage());
        }
        if (mDataList.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getDiseaseSortList(belong);
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

}
