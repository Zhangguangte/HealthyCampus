package com.example.HealthyCampus.module.Find.Recipes.Customization.activity.Detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.DetailAdapter;
import com.example.HealthyCampus.common.adapter.MaterialAdapter;
import com.example.HealthyCampus.common.data.Bean.CookDetailBean;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.github.lzyzsd.circleprogress.ArcProgress;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.VISIBLE;


public class RecipesDetailActivity extends BaseActivity<RecipesDetailContract.View, RecipesDetailContract.Presenter> implements RecipesDetailContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;


    @BindView(R.id.ivIcon)
    ImageView ivIcon;           //菜肴图片

    @BindView(R.id.material_recycler_view)
    RecyclerView rvMaterial;
    @BindView(R.id.detail_way_recycler_view)
    RecyclerView rvDetail;

    //主要元素
    //蛋白质
    @BindView(R.id.protein_circle)
    ArcProgress proteinCircle;
    @BindView(R.id.protein_text)
    TextView proteinText;
    //脂肪
    @BindView(R.id.fat_circle)
    ArcProgress fatCircle;
    @BindView(R.id.fat_text)
    TextView fatText;
    //碳水化合物
    @BindView(R.id.carbohydrate_circle)
    ArcProgress carbohydrateCircle;
    @BindView(R.id.carbohydrate_text)
    TextView carbohydrateText;

    @BindView(R.id.tvFlavor)
    TextView tvFlavor;
    @BindView(R.id.tvProductionTime)
    TextView tvProductionTime;
    @BindView(R.id.tvMainProcess)
    TextView tvMainProcess;


    private List<CookDetailBean> materialList = new LinkedList<>();
    private List<String> detailList = new LinkedList<>();
    private MaterialAdapter materialAdapter;
    private DetailAdapter detailAdapter;


    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.bright_green);   //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, false);     //白色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_recipes_customization_activity_detail);
    }

    @Override
    protected RecipesDetailContract.Presenter createPresenter() {
        return new RecipesDetailPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        iniMaterialRecycleView();
        iniDetailRecycleView();
    }


    private void iniMaterialRecycleView() {
        materialAdapter = new MaterialAdapter(this, materialList);
        rvMaterial.setLayoutManager(new MyLinearLayoutManager(this));
        rvMaterial.setNestedScrollingEnabled(false);
        rvMaterial.setHasFixedSize(true);
        rvMaterial.setAdapter(materialAdapter);

    }

    private void iniDetailRecycleView() {
        detailAdapter = new DetailAdapter(this, detailList);
        rvDetail.setLayoutManager(new MyLinearLayoutManager(this));
        rvDetail.setNestedScrollingEnabled(false);
        rvDetail.setHasFixedSize(true);
        rvDetail.setAdapter(detailAdapter);
    }


    private void setCustomActionBar() {
        ivBack.setVisibility(VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        showProgressDialog(getResources().getString(R.string.loading_footer_tips));
        /**
         * 需要通过Message机制更新数据，或者初始化数据时直接赋值，不然Item部分不显示
         */
        Intent intent = getIntent();
        if (null == intent) {
            ToastUtil.show(getContext(), getString(R.string.data_lose));
            finish();
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("ID"))) {
            mPresenter.getRecipeDetail(0, getIntent().getStringExtra("ID"));
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("NAME"))) {
            mPresenter.getRecipeDetail(1, "%" + getIntent().getStringExtra("NAME") + "%");
        }

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
                switch (response.code) {
                    case 1014:
                        ToastUtil.show(getContext(), getString(R.string.database_empty_data));
                        finish();
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
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void showMaterialSuccess(List<CookDetailBean> cookDetailBeans) {
        materialList.clear();
        materialList.addAll(cookDetailBeans);
        Message message = mHandler.obtainMessage();
        message.what = 2;
        mHandler.sendMessage(message);
    }

    @Override
    public void showDetailSuccess(List<String> detaillist) {
        detailList.clear();
        detailList.addAll(detaillist);
        Message message = mHandler.obtainMessage();
        message.what = 3;
        mHandler.sendMessage(message);
    }

    @Override
    public void showIconSuccess(String url) {
        GlideUtils.display(ivIcon, url, false);
    }

    @Override
    public void showElementSuccess(String components) {
        Message message = mHandler.obtainMessage();
        message.what = 1;
        message.obj = components.split(",");
        mHandler.sendMessage(message);
    }

    @Override
    public void showGeneralSuccess(String flavor, String productionTime, String mainProcess) {
        tvFlavor.setText(flavor);
        tvProductionTime.setText(productionTime);
        tvMainProcess.setText(mainProcess);
    }

    @Override
    public void showTitle(String title) {
        tvTitle.setText(title);
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressLint("SetTextI18n")
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:                                 //更新元素数据
                    String[] array = (String[]) msg.obj;
                    //显示元素质量
                    proteinText.setText(array[2] + "克");
                    fatText.setText(array[1] + "克");
                    carbohydrateText.setText(array[0] + "克");
                    //显示元素比例
                    proteinCircle.setProgress((int) Float.parseFloat(array[2]));
                    fatCircle.setProgress((int) Float.parseFloat(array[1]));
                    carbohydrateCircle.setProgress((int) Float.parseFloat(array[0]));
                    dismissProgressDialog();
                    break;
                case 2:                                 //更新材料数据
                    materialAdapter.notifyDataSetChanged();
                    break;
                case 3:                                 //更新步骤数据
                    detailAdapter.notifyDataSetChanged();
                    break;
            }
        }
    };


}
