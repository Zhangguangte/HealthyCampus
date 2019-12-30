package com.example.HealthyCampus.module.HomePage.Consultation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MessageListVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.NameUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.HomePage.Consultation.Phone.ConsultationPhoneActivity;
import com.example.HealthyCampus.module.HomePage.Consultation.Picture.ConsultPictureActivity;
import com.example.HealthyCampus.module.Message.Chat.ChatActivity;

import java.io.IOException;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class ConsultationActivity extends BaseActivity<ConsultationContract.View, ConsultationContract.Presenter> implements ConsultationContract.View {

    @BindView(R.id.pictureLayout)
    LinearLayout pictureLayout;
    @BindView(R.id.phoneLayout)
    LinearLayout phoneLayout;
    @BindView(R.id.doctorLayout)
    LinearLayout doctorLayout;

    private MaterialDialog builder, nickDialog, userDialog;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);           //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, true);         // 黑色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.health_consultation);
    }

    @Override
    protected ConsultationContract.Presenter createPresenter() {
        return new ConsultationPresenter();
    }

    @Override
    protected void initView() {
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        initDialog();
        initNickNameDialog();
    }


    @Override
    public Context getContext() {
        return this;
    }

    //返回数据：失败
    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1000) {
                    ToastUtil.show(this, "无过往记录");
//                    ToastUtil.show(getContext(), "查无数据");
                } else if (response.code == 1011) {
                    ToastUtil.show(this, "你已有匿名聊天");
//                    ToastUtil.show(getContext(), "查无数据");
                } else {
                    ToastUtil.show(this, "未知错误1:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            LogUtil.logE("ConsultationAct" + "123456", "throwable.getMessage():" + throwable.getMessage());
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void showSuccess(String room_id) {
        dismissProgressDialog();
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("roomid", String.valueOf(room_id));
        bundle.putString("anotherName", "医生");
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void showtDoctorSuccess(List<MessageListVo> listVos) {
        dismissProgressDialog();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.health_consultation_user_dialog, null, false);
        TextView tvFirst = view.findViewById(R.id.tvFirst);
        tvFirst.setText(listVos.get(0).getAnother_name());
        tvFirst.setOnClickListener(v -> {
            showSuccess(listVos.get(0).getRoom_id());
            userDialog.dismiss();
        });
        tvFirst.setOnLongClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
            builder.setItems(R.array.delete, (dialog, which) -> {
                userDialog.dismiss();
                mPresenter.deleteRoomId(listVos.get(0).getRoom_id());
                listVos.remove(0);
                if (listVos.size() > 0)
                    showtDoctorSuccess(listVos);
            }).show();


            return true;
        });
        if (listVos.size() > 1) {
            TextView tvSecond = view.findViewById(R.id.tvSecond);
            tvSecond.setVisibility(View.VISIBLE);
            tvSecond.setText(listVos.get(1).getAnother_name());
            tvSecond.setOnClickListener(v -> {
                showSuccess(listVos.get(1).getRoom_id());
                userDialog.dismiss();
            });
            tvSecond.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(R.array.delete, (dialog, which) -> {
                    userDialog.dismiss();
                    mPresenter.deleteRoomId(listVos.get(1).getRoom_id());
                    listVos.remove(1);
                    if (listVos.size() > 0)
                        showtDoctorSuccess(listVos);
                }).show();

                return true;
            });
        }
        userDialog = new MaterialDialog.Builder(getContext())
                .title("请选择你的昵称")
                .customView(view, false).build();
        userDialog.show();
    }

    @OnClick(R.id.pictureLayout)
    public void pictureLayout(View view) {
        Intent intent = new Intent(this, ConsultPictureActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @OnClick(R.id.phoneLayout)
    public void phoneLayout(View view) {
        Intent intent = new Intent(this, ConsultationPhoneActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.fade_out, R.anim.fade_in);
    }

    @OnClick(R.id.doctorLayout)
    public void doctorLayout(View view) {
        builder.show();
    }

    private void initDialog() {
        builder = new MaterialDialog.Builder(getContext())// 初始化建造者
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .titleColor(Color.BLACK)// 内容
                .content("是否已经有过记录")
                .positiveText("是")
                .positiveColor(Color.BLACK)
                .negativeText("否")
                .negativeColor(Color.BLACK)
                .onPositive((dialog, which) -> {
                    showProgressDialog(getString(R.string.loading_footer_tips));
                    mPresenter.getDoctorRoom();
                })
                .onNegative((dialog, which) -> nickDialog.show())
                .build();
    }

    @SuppressLint("InflateParams")
    private void initNickNameDialog() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.health_consultation_ask_dialog, null, false);
        ImageView ivChange = view.findViewById(R.id.ivChange);
        EditText etNickname = view.findViewById(R.id.etNickname);
        ivChange.setOnClickListener(v -> etNickname.setText(NameUtil.generateLastName()));
        nickDialog = new MaterialDialog.Builder(getContext())
                .title("请设置你的昵称")
                .positiveText("确定")
                .positiveColor(Color.BLACK)
                .onPositive((dialog, which) -> mPresenter.createRoom(etNickname.getText().toString().trim()))
                .customView(view, false).build();

    }


}
