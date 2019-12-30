package com.example.HealthyCampus.module.Find.Recipes.Functionality.RecipesList.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.CustomizationAdapter;
import com.example.HealthyCampus.common.adapter.ListTextAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.FoodMenuVo;
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


public class RecipesListActivity extends BaseActivity<RecipesListContract.View, RecipesListContract.Presenter> implements RecipesListContract.View, ListTextAdapter.onItemClick {

    //自定义标题框
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.rvList)
    RecyclerView rvList;
    @BindView(R.id.rvContent)
    RecyclerView rvContent;
    @BindView(R.id.view_divider)
    View vDivider;
    @BindView(R.id.foodTitleLayout)
    LinearLayout foodTitleLayout;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.ivEmpty)
    ImageView ivEmpty;


    private List<String> list = new LinkedList<>();
    private ListTextAdapter listAdapter;

    private List<FoodMenuVo> foodList = new LinkedList<>();
    private CustomizationAdapter customizationAdapter;

    private int row = 0, type;
    private AnimationDrawable loadAnimation;

    private StringBuffer stringBuffer = new StringBuffer();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_recipes_functionality_list);
    }

    @Override
    protected RecipesListContract.Presenter createPresenter() {
        return new RecipesListPresenter();
    }

    @Override
    protected void initView() {
        initListRecycleView();
        initContentRecycleView();
    }

    private void setCustomActionBar() {
        switch (type) {
            case 1:
                tvTitle.setText(getString(R.string.find_recipes_function_cosmetology).trim());
                break;
            case 2:
                tvTitle.setText(getString(R.string.find_recipes_function_health_preservation).trim());
                break;
            case 3:
                tvTitle.setText(getString(R.string.find_recipes_function_slimming).trim());
                break;
            case 4:
                tvTitle.setText(getString(R.string.find_recipes_function_more).trim());
                break;
        }

        ivBack.setVisibility(VISIBLE);
    }

    public static void start(Context context, int type) {
        Intent intent = new Intent(context, RecipesListActivity.class);
        intent.putExtra("TYPE", type);
        context.startActivity(intent);
        ((Activity) context).overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        type = getIntent().getIntExtra("TYPE", 0);
        setCustomActionBar();
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
        mPresenter.getRecipesList(type, row);
    }


    @Override
    public Context getContext() {
        return this;
    }

    private void initListRecycleView() {
        listAdapter = new ListTextAdapter(getContext(), list, this);
        rvList.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvList.setHasFixedSize(true);
        rvList.setAdapter(listAdapter);
    }

    private void initContentRecycleView() {
        customizationAdapter = new CustomizationAdapter(this, foodList);
        rvContent.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvContent.setHasFixedSize(true);
        rvContent.setAdapter(customizationAdapter);
        rvContent.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (customizationAdapter.isLoad()) {
                    mPresenter.getFoodList(stringBuffer.toString().trim(), ++row);
                }
            }
        });
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 999) {
                    if (row != 0)
                        ToastUtil.show(this, getString(R.string.data_lose));
                    customizationAdapter.setLoad(false);
                    customizationAdapter.notifyDataSetChanged();
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
        dismissProgressDialog();
    }

    @Override
    public void showRecipesSuccess(List<FoodMenuVo> foodMenuVos) {
        if (foodMenuVos.size() < 15)
            customizationAdapter.setLoad(false);
        foodList.addAll(foodMenuVos);
        customizationAdapter.notifyDataSetChanged();
        loadingData(false);
        emptyLayout.setVisibility(View.GONE);
        rvContent.setVisibility(VISIBLE);
        if (foodList.size() == 0 && row == 0) {
            emptyLayout.setVisibility(View.VISIBLE);
            ivEmpty.setImageResource(R.drawable.empty_data);
            tvEmpty.setText(getString(R.string.search_no_result));
            NetworkLayout.setEnabled(false);
        }

    }

    @Override
    public void showRecipesListSuccess(List<String> list) {
        stringBuffer.setLength(0);
        stringBuffer.append(list.get(0));
        this.list.clear();
        this.list.addAll(list);
        listAdapter.notifyDataSetChanged();


        rvList.setVisibility(VISIBLE);
        foodTitleLayout.setVisibility(VISIBLE);
        vDivider.setVisibility(VISIBLE);
    }


    @Override
    public void selectType(String name) {
        row = 0;
        stringBuffer.setLength(0);
        stringBuffer.append(name);
        loadingData(true);

        foodList.clear();
        customizationAdapter.notifyDataSetChanged();
        rvContent.scrollToPosition(0);
        customizationAdapter.setLoad(true);

        rvContent.setVisibility(View.GONE);
        emptyLayout.setVisibility(VISIBLE);
        mPresenter.getFoodList(name, row);
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
        mPresenter.getRecipesList(type, row);
    }
}
