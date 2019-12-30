package com.example.HealthyCampus.module.Find.Recipes;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.RecipesPagerAdapter;
import com.example.HealthyCampus.common.data.form.MapForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.custom_dialog.MapDialog;
import com.example.HealthyCampus.common.widgets.custom_dialog.RecipesDialog;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera.ClassifierCameraActivity;
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
    @BindView(R.id.ivBackground)
    ImageView ivBackground;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_tabs)
    SlidingTabLayout tlTabs;
    @BindView(R.id.cvClassify)
    CardView cvClassify;


    private RecipesPagerAdapter recipesPagerAdapter;
    private RecipesDialog recipesDialog;

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

    @OnClick(R.id.cvClassify)
    public void cvClassify(View view) {
        ivBackground.setVisibility(View.GONE);
        recipesDialog.show();
    }

    private void initrecipesDialog() {
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_recipe_classify, null);
        recipesDialog = new RecipesDialog(this, dialogView, R.style.DialogMap);
        recipesDialog.getWindow().findViewById(R.id.foodLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(RecipesActivity.this, ClassifierCameraActivity.class);
                cameraIntent.putExtra("CODE", ClassifierCameraActivity.DISH_CODE);
                startActivity(cameraIntent);
                recipesDialog.dismiss();
            }
        });
        recipesDialog.getWindow().findViewById(R.id.materialLayout).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent cameraIntent = new Intent(RecipesActivity.this, ClassifierCameraActivity.class);
                cameraIntent.putExtra("CODE", ClassifierCameraActivity.MATERAIL_CODE);
                startActivity(cameraIntent);
                recipesDialog.dismiss();
            }
        });
        recipesDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                ivBackground.setVisibility(View.VISIBLE);
                recipesDialog.dismiss();
            }
        });
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        initrecipesDialog();
    }

    private void initViewPager() {
        recipesPagerAdapter = new RecipesPagerAdapter(getSupportFragmentManager(),
                this);
        vpContent.setOffscreenPageLimit(2);
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
