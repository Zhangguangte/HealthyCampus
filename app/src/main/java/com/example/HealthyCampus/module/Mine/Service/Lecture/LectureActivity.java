package com.example.HealthyCampus.module.Mine.Service.Lecture;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.CollegeAdapter;
import com.example.HealthyCampus.common.adapter.LectureAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.LectureVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
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


public class LectureActivity extends BaseActivity<LectureContract.View, LectureContract.Presenter> implements LectureContract.View, CollegeAdapter.onItemClick {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.rvLecture)
    RecyclerView rvLecture;
    @BindView(R.id.rvCollege)
    RecyclerView rvCollege;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;

    private AnimationDrawable loadAnimation;
    private LectureAdapter lectureAdapter;

    private List<LectureVo> mDataList = new LinkedList();
    private int row = 0;
    private String college;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_lecture);
    }

    @Override
    protected LectureContract.Presenter createPresenter() {
        return new LecturePresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initLectureView();
        initCollegeView();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.mine_service_lecture);
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
    }

    @Override
    public void onBackPressed() {
        if (emptyLayout.isShown() || rvLecture.isShown()) {
            emptyLayout.setVisibility(View.GONE);
            rvCollege.setVisibility(View.VISIBLE);
            rvLecture.setVisibility(View.GONE);
        } else
            finish();
    }

    @Override
    public Context getContext() {
        return this;
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

    private void initLectureView() {
        lectureAdapter = new LectureAdapter(this, mDataList);
        rvLecture.setLayoutManager(new LinearLayoutManager(getContext()));
        rvLecture.setHasFixedSize(true);
        rvLecture.setAdapter(lectureAdapter);
        rvLecture.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (lectureAdapter.isLoad()) {
                    mPresenter.getLectureList(college, ++row);
                }
            }
        });
    }

    private void initCollegeView() {
        CollegeAdapter collegeAdapter = new CollegeAdapter(this, this);
        rvCollege.setLayoutManager(new LinearLayoutManager(getContext()));
        rvCollege.setHasFixedSize(true);
        rvCollege.setAdapter(collegeAdapter);
    }


    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        lectureAdapter.setLoad(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                switch (response.code) {
                    case 999:
                        if (0 == row)
                            ToastUtil.show(getContext(), R.string.data_lose);
                        lectureAdapter.setLoad(false);
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
            ToastUtil.show(getContext(), "未知错误2:" + throwable.getMessage());
        }
        if (mDataList.size() == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void showSuccess(List<LectureVo> lectureVos) {
        rvLecture.setVisibility(View.VISIBLE);
        loadingData(false);
        emptyLayout.setVisibility(View.GONE);

        if (lectureVos.size() < 15)
            lectureAdapter.setLoad(false);
        mDataList.addAll(lectureVos);
        lectureAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getLectureList(college, row);
    }

    @Override
    public void selectCollege(String college) {
        row = 0;
        loadingData(true);
        this.college = college;
        rvCollege.setVisibility(View.GONE);
        mPresenter.getLectureList(college, row);
        emptyLayout.setVisibility(View.VISIBLE);
    }
}
