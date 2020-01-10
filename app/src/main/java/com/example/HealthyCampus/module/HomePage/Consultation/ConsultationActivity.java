package com.example.HealthyCampus.module.HomePage.Consultation;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableBoolean;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.NameUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.HomePage.Consultation.Phone.ConsultationPhoneActivity;
import com.example.HealthyCampus.module.HomePage.Consultation.Picture.ConsultPictureActivity;
import com.meiqia.core.MQManager;
import com.meiqia.core.MQMessageManager;
import com.meiqia.core.bean.MQMessage;
import com.meiqia.core.callback.OnGetMessageListCallback;
import com.meiqia.core.callback.OnInitCallback;
import com.meiqia.meiqiasdk.util.MQConfig;
import com.meiqia.meiqiasdk.util.MQIntentBuilder;
import com.orhanobut.logger.Logger;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.HashMap;
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

    private MaterialDialog dialog, nickDialog, userDialog;

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
        MQConfig.init(getContext(), ConstantValues.MEIQIA_KEY, new OnInitCallback() {
            @Override
            public void onSuccess(String clientId) {
                Logger.e("123456：美洽客服初始化成功");
            }

            @Override
            public void onFailure(int code, String message) {
                Logger.e("123456：美洽客服初始化失败，code：" + code + "；错误信息：" + message);
            }
        });
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
                switch (response.code) {
                    case 1010:
                        ToastUtil.show(this, "无过往记录");
                        SPHelper.setInt(SPHelper.MEIQIA_COOUNT, 0);
                        break;
                    case 1011:
                        ToastUtil.show(this, "你已有匿名聊天");
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
//            LogUtil.logE("ConsultationAct" + "123456", "throwable.getMessage():" + throwable.getMessage());
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void showSuccess(String room_id, String name) {
        onCustomerServiceClick(room_id, name);
    }

    @SuppressLint("InflateParams")
    @Override
    public void showtDoctorSuccess(String nickName1, String room1, String nickName2, String room2) {
        dismissProgressDialog();
        View view = LayoutInflater.from(getContext()).inflate(R.layout.health_consultation_user_dialog, null, false);
        TextView tvFirst = view.findViewById(R.id.tvFirst);
        if (null != nickName1 && null != room1) {
            tvFirst.setText(nickName1);
            tvFirst.setVisibility(View.VISIBLE);
            tvFirst.setOnClickListener(v -> {
                showSuccess(room1, nickName1);
                userDialog.dismiss();
            });
            tvFirst.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(R.array.delete, (dialog, which) -> {
                    userDialog.dismiss();
                    mPresenter.deleteRoomId(room1, 1);
                    if (null != nickName2 && null != room2)
                        showtDoctorSuccess(nickName2, room2, null, null);
                }).show();
                return true;
            });
        }
        if (null != nickName2 && null != room2) {
            TextView tvSecond = view.findViewById(R.id.tvSecond);
            tvSecond.setVisibility(View.VISIBLE);
            tvSecond.setText(nickName2);
            tvSecond.setOnClickListener(v -> {
                showSuccess(room2, nickName2);
                userDialog.dismiss();
            });
            tvSecond.setOnLongClickListener(v -> {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setItems(R.array.delete, (dialog, which) -> {
                    userDialog.dismiss();
                    mPresenter.deleteRoomId(room2, 2);
                    if (null != nickName1 && null != room1)
                        showtDoctorSuccess(nickName1, room1, null, null);
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
        dialog.show();
    }

    private void initDialog() {
        dialog = new MaterialDialog.Builder(getContext())
                .content("是否有过往记录")
                .positiveText("是")
                .onPositive((dialog, which) -> {
                    LogUtil.logE("ConsultationAct" + "123456", "tSPHelper.getString(SPHelper.MERQIA_DATE):" + SPHelper.getString(SPHelper.MERQIA_DATE));
                    if (TextUtils.isEmpty(SPHelper.getString(SPHelper.MERQIA_DATE).trim())) {
                        showProgressDialog(getString(R.string.loading_footer_tips));
                        mPresenter.getDoctorRoom();
                    } else if (1 > SPHelper.getInt(SPHelper.MEIQIA_COOUNT, 0)) {
                        ToastUtil.show(this, "无过往记录");
                    } else {
                        showtDoctorSuccess(SPHelper.getString(SPHelper.MEIQIA_NAME_1), SPHelper.getString(SPHelper.MEIQIA_ROOM_1), SPHelper.getString(SPHelper.MEIQIA_NAME_2), SPHelper.getString(SPHelper.MEIQIA_ROOM_2));
                    }
                })
                .negativeText("否")
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
                .onPositive((dialog, which) -> {
                    showProgressDialog(getString(R.string.loading_footer_tips));
                    mPresenter.createRoom(etNickname.getText().toString().trim());
                })
                .customView(view, false).build();

    }

    public void onCustomerServiceClick(String room_id, String name) {
        dismissProgressDialog();


        MQConfig.ui.titleGravity = MQConfig.ui.MQTitleGravity.LEFT;//标题位置
        MQConfig.isVoiceSwitchOpen = false;//关闭语音功能，因为录音时有不显示录音按钮的Bug

//                //自定义UI，不建议这种方式
//                MQConfig.ui.backArrowIconResId = R.drawable.ic_back_24dp;//返回按钮
//                MQConfig.ui.titleBackgroundResId = R.color.colorPrimary;//标题栏背景色
//                MQConfig.ui.titleTextColorResId = R.color.colorAccent;//标题颜色
//                MQConfig.ui.leftChatBubbleColorResId = R.color.colorAccent;//左侧聊天气泡背景
//                MQConfig.ui.rightChatBubbleColorResId = R.color.blue_light;//右侧聊天气泡背景
//                MQConfig.ui.robotEvaluateTextColorResId = android.R.color.transparent;
//                MQConfig.ui.robotMenuItemTextColorResId = android.R.color.transparent;
//                MQConfig.ui.robotMenuTipTextColorResId = android.R.color.transparent;

        HashMap<String, String> clientInfo = new HashMap<>();
        clientInfo.put("name", name);
        clientInfo.put("gender", SPHelper.getString(SPHelper.USER_SEX));
        //1、启动完全对话模式
        Intent intent = new MQIntentBuilder(getContext())
                .setCustomizedId(room_id)
//                .setScheduledAgent(ConstantValues.MEIQIA_CUSTOMER)
                .setClientInfo(clientInfo)
                .updateClientInfo(clientInfo)
                .build();

        //2、启动单一表单模式
//        Intent intent = new Intent(getContext(), MQMessageFormActivity.class);
        getContext().startActivity(intent);

        //将未读消息数清空
    }



    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
