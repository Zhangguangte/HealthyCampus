package com.example.HealthyCampus.module.Mine.Service.timeTable;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.CourseVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.CourseTableView;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class TimeTableActivity extends BaseActivity<TimeTableContract.View, TimeTableContract.Presenter> implements TimeTableContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tableView)
    CourseTableView tableView;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;

    private AnimationDrawable loadAnimation;
    private String major;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_timetable);
    }

    @Override
    protected TimeTableContract.Presenter createPresenter() {
        return new TimeTablePresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.mine_service_timetable);
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();


        major = "16软工1";
//        major = SPHelper.getString(SPHelper.MAJOR);
//        if (TextUtils.isEmpty(major)) {
//            ToastUtil.show(getContext(), "请填写学生信息");
//            finish();
//        } else
        mPresenter.getTimeTable(major);
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

    @Override
    public void showSuccess(List<CourseVo> courses) {

        loadingData(false);
        emptyLayout.setVisibility(View.GONE);
        tableView.updateCourseViews(courses);


    }

    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 999) {
                    ToastUtil.show(getContext(),R.string.data_lose );
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

    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getTimeTable(major);
    }

}
