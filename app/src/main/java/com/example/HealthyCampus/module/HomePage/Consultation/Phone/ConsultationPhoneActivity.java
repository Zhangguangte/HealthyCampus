package com.example.HealthyCampus.module.HomePage.Consultation.Phone;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.HomePage.Consultation.Picture.ConsultPictureActivity;

import butterknife.BindView;
import butterknife.OnClick;


public class ConsultationPhoneActivity extends BaseActivity<ConsultationPhoneContract.View, ConsultationPhoneContract.Presenter> implements ConsultationPhoneContract.View {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.tvCall)
    TextView tvCall;

    private MaterialDialog builder;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);           //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, true);         // 黑色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.health_consultation_phone);
    }

    @Override
    protected ConsultationPhoneContract.Presenter createPresenter() {
        return new ConsultationPhonePresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.health_consult_emergency_call);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        initDialog();
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.tvCall)
    public void tvCall(View view) {
        builder.show();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void initDialog() {
        builder = new MaterialDialog.Builder(getContext())// 初始化建造者
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .titleColor(Color.BLACK)// 内容
                .content("是否确认拨打电话")
                .positiveText("确定")
                .positiveColor(Color.BLACK)
                .negativeText("取消")
                .negativeColor(Color.BLACK)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
//                        Intent intent = new Intent(Intent.ACTION_DIAL);     //拨打电话界面
//                        Intent intent = new Intent(Intent.ACTION_CALL);     //打电话
//                        Uri data = Uri.parse("tel:132XXXXXXXX");
//                        intent.setData(data);
//                        startActivity(intent);
                        ToastUtil.show(getContext(), "拨打成功");
                    }
                }).build();
    }

}
