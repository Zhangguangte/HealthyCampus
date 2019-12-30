package com.example.HealthyCampus.module.Find.Recipes.Customization.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.CustomizationAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
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

    @BindView(R.id.btnPreday)
    Button btnPreday;
    @BindView(R.id.btnNext)
    Button btnNext;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;

    //三餐选择
    @BindView(R.id.btnBreakfast)
    Button btnBreakfast;
    @BindView(R.id.btnLunch)
    Button btnLunch;
    @BindView(R.id.btnDinner)
    Button btnDinner;

    private Button btn;
    private List<FoodMenuVo> foodList = new LinkedList<>();
    private CustomizationAdapter customizationAdapter;

    private AnimationDrawable loadAnimation;

    private String[] weeks;

    private int weekId;
    private StringBuffer type = new StringBuffer("breakfast");               //0:早;1:午;2:晚;
    private int tooday;
    private int dayTitle = 0;

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
        customizationAdapter.setLoad(false);
        rvContent.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvContent.setHasFixedSize(true);
        rvContent.setAdapter(customizationAdapter);

    }


    @SuppressLint("SetTextI18n")
    private void setCustomActionBar() {
        ivBack.setVisibility(VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    public static void actionShow(Context context, String title) {
        Intent intent = new Intent(context, CustomizationActivity.class);
        intent.putExtra("Title", title);
        context.startActivity(intent);
    }



    @SuppressLint("SetTextI18n")
    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();


        weeks = getResources().getStringArray(R.array.recipes_week);
        List list = Arrays.asList(weeks);
        String title = getIntent().getStringExtra("Title");
        weekId = list.indexOf(title);


        tooday = DateUtils.getWeek();

        if (tooday == weekId)
            tvTitle.setText(weeks[weekId] + "（今天）");
        else
            tvTitle.setText(title);

        btn = btnBreakfast;
        dayClick();
        mPresenter.getRecipesByThreeMeals(dayTitle, type.toString(), weekId);
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
                if (response.code == 1006) {
                    ToastUtil.show(this, "无数据");
                } else if (response.code == 1003) {
                    ToastUtil.show(this, "无效参数");
                } else
                    ToastUtil.show(this, "未知错误1:" + throwable.getMessage());
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


        foodList.clear();
        foodList.addAll(foodMenuVos);
        customizationAdapter.notifyDataSetChanged();
    }

    private void dayClick() {
        if (weekId == 0) {
            btnNext.setTextColor(getResources().getColor(R.color.text_level_3));
            btnNext.setEnabled(false);
        } else if (weekId == 1) {
            btnPreday.setTextColor(getResources().getColor(R.color.text_level_3));
            btnPreday.setEnabled(false);
        } else {
            btnPreday.setTextColor(getResources().getColor(R.color.black));
            btnPreday.setEnabled(true);
            btnNext.setTextColor(getResources().getColor(R.color.black));
            btnNext.setEnabled(true);
        }
    }


    @OnClick(R.id.NetworkLayout)      //重试:数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getRecipesByThreeMeals(dayTitle, type.toString(), weekId);
    }


    @OnClick({R.id.btnBreakfast, R.id.btnLunch, R.id.btnDinner})
    public void btnThreeMeals(View view) {
        loadingData(true);
        //初始化数据
        rvContent.scrollToPosition(0);
        foodList.clear();
        customizationAdapter.notifyDataSetChanged();

        switch (view.getId()) {
            case R.id.btnBreakfast:
                btnBreakfast.setEnabled(false);
                btnBreakfast.setTextSize(18.0f);
                btnBreakfast.setTextColor(getResources().getColor(R.color.white));
                btnClick();
                type.setLength(0);
                type.append("breakfast");
                dayTitle = 0;
                btn = btnBreakfast;
                break;
            case R.id.btnLunch:
                btnLunch.setEnabled(false);
                btnLunch.setTextSize(18.0f);
                btnLunch.setTextColor(getResources().getColor(R.color.white));
                btnClick();
                type.setLength(0);
                type.append("lunch");
                dayTitle = 1;
                btn = btnLunch;
                break;
            case R.id.btnDinner:
                btnDinner.setEnabled(false);
                btnDinner.setTextSize(18.0f);
                btnDinner.setTextColor(getResources().getColor(R.color.white));
                btnClick();
                type.setLength(0);
                type.append("dinner");
                dayTitle = 2;
                btn = btnDinner;
                break;
            default:
                break;
        }

        mPresenter.getRecipesByThreeMeals(dayTitle, type.toString(), weekId);
    }

    private void btnClick() {
        btn.setTextSize(16.0f);
        btn.setEnabled(true);
        btn.setTextColor(getResources().getColor(R.color.black));
    }

    @SuppressLint("SetTextI18n")
    @OnClick({R.id.btnPreday, R.id.btnNext})
    public void btnDay(View view) {
        loadingData(true);
        emptyLayout.setVisibility(VISIBLE);
        //初始化数据
        rvContent.scrollToPosition(0);
        foodList.clear();
        customizationAdapter.notifyDataSetChanged();

        btnClick();
        //初始化为早餐
        btnBreakfast.setEnabled(false);
        btnBreakfast.setTextSize(18.0f);
        btnBreakfast.setTextColor(getResources().getColor(R.color.white));
        btn = btnBreakfast;
        switch (view.getId()) {
            case R.id.btnPreday:
                weekId = weekId == 0 ? 6 : --weekId;
                dayClick();
                break;
            case R.id.btnNext:
                weekId = weekId == 6 ? 0 : ++weekId;
                dayClick();
                break;
            default:
                break;
        }
        if (tooday == weekId)
            tvTitle.setText(weeks[weekId] + "（今天）");
        else
            tvTitle.setText(weeks[weekId]);
        dayTitle = 0;
        type.setLength(0);
        type.append("breakfast");
        mPresenter.getRecipesByThreeMeals(dayTitle, type.toString(), weekId);
    }

}
