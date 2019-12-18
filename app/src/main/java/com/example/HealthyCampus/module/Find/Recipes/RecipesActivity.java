package com.example.HealthyCampus.module.Find.Recipes;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.RecipesPagerAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.flyco.tablayout.SlidingTabLayout;
import com.youth.banner.transformer.AccordionTransformer;
import com.youth.banner.transformer.CubeOutTransformer;
import com.youth.banner.transformer.FlipHorizontalTransformer;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.VISIBLE;


public class RecipesActivity extends BaseActivity<RecipesContract.View, RecipesContract.Presenter> implements RecipesContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_tabs)
    SlidingTabLayout tlTabs;


    private RecipesPagerAdapter recipesPagerAdapter;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_recipes);
    }

    @Override
    protected RecipesContract.Presenter createPresenter() {
        return new RecipesPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initViewPager();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.find_health_recipes);
        ivBack.setVisibility(VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
    }

    private void initViewPager() {
        recipesPagerAdapter = new RecipesPagerAdapter(getSupportFragmentManager(),
                this);
        vpContent.setOffscreenPageLimit(1);
        vpContent.setAdapter(recipesPagerAdapter);
        //CubeInTransformer 内旋
        //FlipHorizontalTransformer 像翻书一样
        //AccordionTransformer  风琴 拉压
//        vpContent.setPageTransformer(true, new AccordionTransformer());
        vpContent.setCurrentItem(0);

        tlTabs.setViewPager(vpContent);
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
        dismissProgressDialog();
    }

    @Override
    protected void initImmersionBar() {
    }

}
