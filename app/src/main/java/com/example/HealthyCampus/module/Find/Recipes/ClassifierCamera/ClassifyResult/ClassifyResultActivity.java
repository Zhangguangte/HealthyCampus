package com.example.HealthyCampus.module.Find.Recipes.ClassifierCamera.ClassifyResult;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.IngredientResultVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class ClassifyResultActivity extends BaseActivity<ClassifyResultContract.View, ClassifyResultContract.Presenter> implements ClassifyResultContract.View {

    @BindView(R.id.tvFoodName)
    TextView tvFoodName;
    @BindView(R.id.tvCalorie)
    TextView tvCalorie;
    @BindView(R.id.tvSugar)
    TextView tvSugar;
    @BindView(R.id.tvFat)
    TextView tvFat;
    @BindView(R.id.tvprotein)
    TextView tvprotein;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvSynopsis)
    TextView tvSynopsis;
    @BindView(R.id.tvNutritive)
    TextView tvNutritive;
    @BindView(R.id.tvEffect)
    TextView tvEffect;


    //加载布局


    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.cyan);
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_recipes_classify_result);
    }

    @Override
    protected ClassifyResultContract.Presenter createPresenter() {
        return new ClassifyResultPresenter();
    }

    @Override
    protected void initView() {

    }


    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showProgressDialog(getString(R.string.loading_footer_tips));
        String name = getIntent().getStringExtra("NAME");
        mPresenter.getIngredientResult(name);
    }


    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void showError(Throwable throwable) {
        Log.e("ClassifierCame:" + "123456", "throwable.getMessage()" + throwable.getMessage());
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 999) {
                    ToastUtil.show(getContext(), getString(R.string.database_empty_data));
                } else {
                    ToastUtil.show(this, "未知错误1:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                Log.e("ClassifierCame:" + "123456", "99999999999999999999999999999999999");
            }
        } else {
            Log.e("ClassifierCame:" + "123456", "http:throwable" + throwable);
            Log.e("ClassifierCame:" + "123456", "http:throwable.getCause()" + throwable.getCause());
            Log.e("ClassifierCame:" + "123456", "http:throwable.getMessage()" + throwable.getMessage());
            ToastUtil.show(this, "http:未知错误:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showIngredientSuccess(IngredientResultVo ingredientResultVo) {

        String array[] = ingredientResultVo.getComponents().split(",");

        //显示元素质量
        tvSugar.setText(array[0] + getString(R.string.find_health_recipes_classify_gram));
        tvFat.setText(array[1] + getString(R.string.find_health_recipes_classify_gram));
        tvprotein.setText(array[2] + getString(R.string.find_health_recipes_classify_gram));
        tvCalorie.setText(ingredientResultVo.getCalorie() + getString(R.string.find_health_recipes_classify_kilocalorie));

        tvEffect.setText(ingredientResultVo.getEffect().replaceAll("\n", "\n\t\t\t\t"));
        tvNutritive.setText(ingredientResultVo.getNutritive().replaceAll("\n", "\n\t\t\t\t"));
        tvSynopsis.setText(ingredientResultVo.getSynopsis().replaceAll("\n", "\n\t\t\t\t"));
        tvFoodName.setText(ingredientResultVo.getName());

        GlideUtils.display(ivIcon, ingredientResultVo.getUrl());

        dismissProgressDialog();
    }


}
