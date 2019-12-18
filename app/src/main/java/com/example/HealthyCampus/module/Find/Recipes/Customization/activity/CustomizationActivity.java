package com.example.HealthyCampus.module.Find.Recipes.Customization.activity;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.CustomizationAdapter;
import com.example.HealthyCampus.common.adapter.MedicineAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class CustomizationActivity extends BaseActivity<CustomizationContract.View, CustomizationContract.Presenter> implements CustomizationContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    @BindView(R.id.tvNext)
    TextView tvNext;
    @BindView(R.id.ivNext)
    ImageView ivNext;
    @BindView(R.id.change_layout)
    RelativeLayout changeLayout;

    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;


    private List<FoodMenuVo> foodList = new LinkedList<>();
    private CustomizationAdapter customizationAdapter;

    private AnimationDrawable loadAnimation;
    private Animation nextanimation;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.bright_green);
        StatusBarUtil.setStatusBarDarkTheme(this, false);     //白色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_recipes_customization_activity);
    }

    @Override
    protected CustomizationContract.Presenter createPresenter() {
        return new CustomizationPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initRecycleView();
    }

    private void initRecycleView() {
        customizationAdapter = new CustomizationAdapter(this, foodList);
        rvContent.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvContent.setHasFixedSize(true);
        rvContent.setAdapter(customizationAdapter);
    }


    private void setCustomActionBar() {
        tvTitle.setText(getIntent().getStringExtra("week_title"));
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
        nextanimation = AnimationUtils.loadAnimation(getContext(), R.anim.customization_process);
        mPresenter.getRecipesByThreeMeals();
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showError(Throwable throwable) {

        loadingData(false);

        processNext(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1006) {
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


    @Override
    public void showRecipesSuccess(List<FoodMenuVo> foodMenuVos) {

        loadingData(false);
        emptyLayout.setVisibility(GONE);


        processNext(false);
        foodList.clear();
        foodList.addAll(foodMenuVos);
        customizationAdapter.notifyDataSetChanged();
    }

    @OnClick(R.id.change_layout)
    public void changeLayout(View view) {
        processNext(true);
        mPresenter.getRecipesByThreeMeals();
    }

    private void processNext(boolean val) {
        if (val) {
            changeLayout.setEnabled(false);
            tvNext.setVisibility(View.GONE);
            ivNext.setAnimation(nextanimation);
        } else {
            changeLayout.setEnabled(true);
            tvNext.setVisibility(View.VISIBLE);
            ivNext.setAnimation(null);
        }
    }

    @OnClick(R.id.NetworkLayout)      //重试:数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getRecipesByThreeMeals();
    }

}
