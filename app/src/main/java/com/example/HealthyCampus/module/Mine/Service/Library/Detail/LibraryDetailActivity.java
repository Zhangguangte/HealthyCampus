package com.example.HealthyCampus.module.Mine.Service.Library.Detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.BookAdapter;
import com.example.HealthyCampus.common.adapter.HostAdapter;
import com.example.HealthyCampus.common.network.vo.BookDetailVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
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


public class LibraryDetailActivity extends BaseActivity<LibraryDetailContract.View, LibraryDetailContract.Presenter> implements LibraryDetailContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvAuthor)
    TextView tvAuthor;
    @BindView(R.id.tvPrice)
    TextView tvPrice;
    @BindView(R.id.tvPublish)
    TextView tvPublish;
    @BindView(R.id.tvIntro)
    TextView tvIntro;
    @BindView(R.id.tvPlace)
    TextView tvPlace;
    @BindView(R.id.tvRest)
    TextView tvRest;
    @BindView(R.id.tvSum)
    TextView tvSum;
    @BindView(R.id.tvIndex)
    TextView tvIndex;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;


    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;

    private AnimationDrawable loadAnimation;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_library_detail);
    }

    @Override
    protected LibraryDetailContract.Presenter createPresenter() {
        return new LibraryDetailPresenter();
    }

    @Override
    protected void initView() {
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

        mPresenter.searchBookDetail(getIntent().getStringExtra("ID"));
    }


    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 999) {
                    Log.e("LibraryDetailAct" + "123456", "response.toString:" + response.toString());
                    ToastUtil.show(this, R.string.data_lose);
                } else {
                    ToastUtil.show(this, "未知错误:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showSuccess(BookDetailVo bookDetailVo) {
        tvTitle.setText(bookDetailVo.getName());
        tvAuthor.setText("作者:\t\t" + bookDetailVo.getAuthor());
        tvPrice.setText("价格:\t\t" + bookDetailVo.getPrice() + "人民币");
        tvPublish.setText(bookDetailVo.getPublish());
        tvIntro.setText("\t\t\t\t" + bookDetailVo.getIntroduced());
        tvPlace.setText("位于:" + bookDetailVo.getPlace());
        tvIndex.setText("索引号:" + bookDetailVo.getIndex());
        tvSum.setText("总量:" + bookDetailVo.getSum());
        tvRest.setText("剩余量:" + bookDetailVo.getRest());
        GlideUtils.display(ivIcon, bookDetailVo.getUrl());
        loadingData(false);
        emptyLayout.setVisibility(View.GONE);
    }

    @Override
    public Context getContext() {
        return this;
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

}
