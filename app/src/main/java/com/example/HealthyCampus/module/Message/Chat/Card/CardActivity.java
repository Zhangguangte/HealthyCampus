package com.example.HealthyCampus.module.Message.Chat.Card;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.AddressListAdapter;
import com.example.HealthyCampus.common.comparator.AddressPinyinComparator;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.SideBar;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class CardActivity extends BaseActivity<CardContract.View, CardContract.Presenter> implements CardContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.message_address_list);
    }

    @Override
    protected CardContract.Presenter createPresenter() {
        return new CardPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.chatting_select_people);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1001) {
                    Log.e("AddressListActi" + "123456", "response.toString:" + response.toString());
                    ToastUtil.show(this, "用户信息错误");
                } else {
                    ToastUtil.show(this, "未知错误:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }catch(IllegalStateException e)
            {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误:" + throwable.getMessage());
        }
    }



    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public Context getContext() {
        return this;
    }
}
