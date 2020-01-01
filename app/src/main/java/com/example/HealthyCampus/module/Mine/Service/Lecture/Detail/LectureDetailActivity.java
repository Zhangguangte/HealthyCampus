package com.example.HealthyCampus.module.Mine.Service.Lecture.Detail;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.LectureVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class LectureDetailActivity extends BaseActivity<LectureDetailContract.View, LectureDetailContract.Presenter> implements LectureDetailContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;


    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;

    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvCollege)
    TextView tvCollege;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.tvDate)
    TextView tvDate;

    private AnimationDrawable loadAnimation;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_lecture_detail);
    }

    @Override
    protected LectureDetailContract.Presenter createPresenter() {
        return new LectureDetailPresenter();
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    public static void actionShow(Context context, String id) {
        Intent intent = new Intent(context, LectureDetailActivity.class);
        intent.putExtra("ID", id);
        context.startActivity(intent);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
        mPresenter.getLectureDetail(getIntent().getStringExtra("ID"));
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 999) {
                    ToastUtil.show(getContext(), R.string.data_lose);
                } else {
                    ToastUtil.show(getContext(), "未知错误1:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(getContext(), "未知错误2:" + throwable.getMessage());
        }
    }

    @Override
    public void showSuccess(LectureVo lectureVo) {
        loadingData(true);
        emptyLayout.setVisibility(View.GONE);
        if (null == lectureVo)
            ToastUtil.show(getContext(), R.string.data_lose);
        else {
            tvContent.setText(lectureVo.getContent());
            tvAuthor.setText(lectureVo.getAuthor());
            tvCollege.setText(lectureVo.getCollege().replaceAll("[)(]", " "));
            tvDate.setText(lectureVo.getDate());
            tvTitle.setText(lectureVo.getTitle());
        }
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

    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getLectureDetail(getIntent().getStringExtra("ID"));
    }


}
