package com.example.HealthyCampus.module.HomePage.Consultation.Picture;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.ConsultPictureAdapter;
import com.example.HealthyCampus.common.adapter.ConsultPictureInfAdapter;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.FileUtils;
import com.example.HealthyCampus.common.utils.FunctionUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.greendao.model.PatienInforBean;
import com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information.InformationActivity;
import com.example.HealthyCampus.module.Message.Chat.imageselect.ImageSelectorActivity;
import com.luck.picture.lib.PictureSelector;
import com.luck.picture.lib.entity.LocalMedia;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_PHOTO;
import static com.example.HealthyCampus.common.constants.ConstantValues.PICK_VEDIO;


public class ConsultPictureActivity extends BaseActivity<ConsultPictureContract.View, ConsultPictureContract.Presenter> implements ConsultPictureContract.View, ConsultPictureAdapter.onItemClick, ConsultPictureInfAdapter.onItemClick {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;

    @BindView(R.id.etWord)
    EditText etWord;
    @BindView(R.id.cbPrescription)
    CheckBox cbPrescription;
    @BindView(R.id.cbHistory)
    CheckBox cbHistory;
    @BindView(R.id.btnSubmit)
    Button btnSubmit;
    @BindView(R.id.tvHint)
    TextView tvHint;
    @BindView(R.id.tvWordNumber)
    TextView tvWordNumber;
    @BindView(R.id.tvPictureHint)
    TextView tvPictureHint;
    @BindView(R.id.rvPicture)
    RecyclerView rvPicture;
    @BindView(R.id.rvPatient)
    RecyclerView rvPatient;
    @BindView(R.id.addLayout)
    LinearLayout addLayout;

    private int pictureNumber = 0;
    private ConsultPictureAdapter pictureAdapter;
    private List<String> list;

    private ConsultPictureInfAdapter patienAdapter;
    private List<PatienInforBean> patienInforBeans;

    private MaterialDialog builder;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);           //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, true);         // 黑色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.health_consultation_picture);
    }

    @Override
    protected ConsultPictureContract.Presenter createPresenter() {
        return new ConsultPicturePresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initEdit();
        initPictureRecycle();
        initPatientRecycle();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        initDialog();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.health_consult_picture_interrogation);
    }

    private void initEdit() {
        etWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                nextClick(s.length() > 0);
                if (s.toString().trim().length() > 0) {
                    tvWordNumber.setText(String.valueOf(s.toString().trim().length()));
                    tvWordNumber.append("/500");
                } else {
                    tvWordNumber.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void initPictureRecycle() {
        list = new LinkedList<>();
        pictureAdapter = new ConsultPictureAdapter(getContext(), list, this);
        rvPicture.setLayoutManager(new GridLayoutManager(getContext(), 4));
        rvPicture.setHasFixedSize(true);
        rvPicture.setAdapter(pictureAdapter);
    }

    private void initPatientRecycle() {
        patienInforBeans = new LinkedList<>();
        patienAdapter = new ConsultPictureInfAdapter(getContext(), patienInforBeans, this);
        rvPatient.setLayoutManager(new MyLinearLayoutManager(this));
        rvPatient.setHasFixedSize(true);
        rvPatient.setAdapter(patienAdapter);
    }


    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.cbPrescription)
    public void cbPrescription(View view) {
        tvHint.setVisibility(!tvHint.isShown() ? View.VISIBLE : View.INVISIBLE);
    }

    private void nextClick(boolean val) {
        btnSubmit.setEnabled(val);
        btnSubmit.setBackgroundColor(getResources().getColor(val ? R.color.clickable_color : R.color.not_clickable_color));
    }

    @OnClick(R.id.btnSubmit)
    public void btnSubmit(View view) {
        if (etWord.getText().toString().trim().length() < 11) {
            ToastUtil.show(getContext(), R.string.health_consult_picture_describe);
        } else {
            builder.show();
        }
    }

    private void initDialog() {
        builder = new MaterialDialog.Builder(getContext())// 初始化建造者
                .backgroundColor(Color.WHITE)
                .contentColor(Color.BLACK)
                .titleColor(Color.BLACK)// 内容
                .content("是否确认提交")
                .positiveText("确定")
                .positiveColor(Color.BLACK)
                .negativeText("取消")
                .negativeColor(Color.BLACK)
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        showProgressDialog(getString(R.string.loading_sumbit));
                        btnSubmit.setEnabled(false);
                        mPresenter.saveConsultPicture(etWord.getText().toString().trim(), list, patienInforBeans, cbPrescription.isChecked(), cbHistory.isChecked());
                    }
                }).build();
    }

    @OnClick(R.id.addLayout)
    public void addLayout(View view) {
        Intent intent = new Intent(ConsultPictureActivity.this, InformationActivity.class);
        intent.putExtra("MAX", null != patienInforBeans ? 4 - patienInforBeans.size() : 4);
        startActivityForResult(intent, ConstantValues.HEALTH_INFORMATION);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    //返回数据：失败
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
        btnSubmit.setEnabled(true);
        dismissProgressDialog();
    }

    @Override
    public void showSuccess() {
        ToastUtil.show(getContext(), "提交成功");
        new Handler().postDelayed(this::finish, 2000);
        dismissProgressDialog();
    }

    @Override
    public void pictureButton() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.picture_vedio, (dialog, which) -> {
            if (0 == which) {
                Intent intent = new Intent(ConsultPictureActivity.this, ImageSelectorActivity.class);
                intent.putExtra("maxNum", 9 - pictureNumber);
                startActivityForResult(intent, PICK_PHOTO);
                overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            } else {
                FunctionUtils.openGalleryAudio(ConsultPictureActivity.this, PICK_VEDIO);
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case PICK_PHOTO:       //相册回调
                if (RESULT_OK == resultCode) {
                    List<String> images = data.getStringArrayListExtra("images");
                    pictureNumber += images.size();
                    list.addAll(images);
                    pictureAdapter.notifyDataSetChanged();
                    if (pictureNumber > 0)
                        tvPictureHint.setVisibility(View.GONE);
                }
                break;
            case PICK_VEDIO:   //视频
                // 视频选择结果回调
                List<LocalMedia> selectListVideo = PictureSelector.obtainMultipleResult(data);
                StringBuilder urlpath = new StringBuilder();
                StringBuilder vedioName = new StringBuilder();
                for (LocalMedia media : selectListVideo) {
//                    LogUtil.logE("ChatActivity" + "123456", "获取视频路径成功:" + media.getPath());
                    //数据重置
                    urlpath.setLength(0);
                    vedioName.setLength(0);
                    //文件名、缩略图名称
                    urlpath.append(FileUtils.getFileName());
                    vedioName.append(FileUtils.getFileName(media.getPath()));
                    //保存操作
                    FunctionUtils.createThumbnail(media.getPath(), urlpath.toString());   //添加缩略图
                }
                break;
            case ConstantValues.HEALTH_INFORMATION:
                if (RESULT_OK == resultCode) {
                    List<PatienInforBean> list = (List<PatienInforBean>) data.getSerializableExtra("USER");
                    patienInforBeans.addAll(list);
                    patienAdapter.notifyDataSetChanged();
                    if (patienInforBeans.size() > 0) {
                        addLayout.setVisibility(View.GONE);
                        rvPatient.setVisibility(View.VISIBLE);
                    }
                }
                break;
            default:
                Log.e("ConsultPicture" + "123456", "requestCode = " + requestCode);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void remove(int position) {
        pictureNumber--;
        list.remove(position);
        pictureAdapter.notifyDataSetChanged();
        if (pictureNumber == 0)
            tvPictureHint.setVisibility(View.VISIBLE);
    }

    @Override
    public void removeInf(int position) {
        patienInforBeans.remove(position);
        patienAdapter.notifyDataSetChanged();
        if (patienInforBeans.size() == 0) {
            rvPatient.setVisibility(View.GONE);
            addLayout.setVisibility(View.VISIBLE);
        }
    }


}
