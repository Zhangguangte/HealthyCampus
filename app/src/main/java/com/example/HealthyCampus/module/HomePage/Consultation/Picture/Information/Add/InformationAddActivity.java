package com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information.Add;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.common.helper.GreenDaoHelper;
import com.example.HealthyCampus.greendao.PatienInforBeanDao;
import com.example.HealthyCampus.greendao.model.PatienInforBean;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.OnClick;


public class InformationAddActivity extends BaseActivity<InformationAddContract.View, InformationAddContract.Presenter> implements InformationAddContract.View {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;


    @BindView(R.id.tvTitleNext)
    TextView tvTitleNext;
    @BindView(R.id.tvBrithday)
    TextView tvBrithday;
    @BindView(R.id.etWeight)
    EditText etWeight;
    @BindView(R.id.AllergyLayout)
    RelativeLayout AllergyLayout;
    @BindView(R.id.etAllergy)
    EditText etAllergy;
    @BindView(R.id.tvAllergyNumber)
    TextView tvAllergyNumber;
    @BindView(R.id.HistoryLayout)
    RelativeLayout HistoryLayout;
    @BindView(R.id.etHistory)
    EditText etHistory;
    @BindView(R.id.tvHistoryNumber)
    TextView tvHistoryNumber;
    @BindView(R.id.etName)
    EditText etName;
    @BindView(R.id.rgSex)
    RadioGroup rgSex;
    @BindView(R.id.rbAllergyHas)
    RadioButton rbAllergyHas;
    @BindView(R.id.rbAllergyHasnt)
    RadioButton rbAllergyHasnt;
    @BindView(R.id.rgAllergy)
    RadioGroup rgAllergy;
    @BindView(R.id.rbHistoryHas)
    RadioButton rbHistoryHas;
    @BindView(R.id.rbHistoryHasnt)
    RadioButton rbHistoryHasnt;
    @BindView(R.id.rgHistory)
    RadioGroup rgHistory;
    @BindView(R.id.rbLiverHas)
    RadioButton rbLiverHas;
    @BindView(R.id.rbLiverHasnt)
    RadioButton rbLiverHasnt;
    @BindView(R.id.rgLiver)
    RadioGroup rgLiver;
    @BindView(R.id.rbRenalHas)
    RadioButton rbRenalHas;
    @BindView(R.id.etID)
    EditText etID;
    @BindView(R.id.rgRenal)
    RadioGroup rgRenal;

    private int selectionStart;
    private int selectionEnd;
    private double weight;

    private PatienInforBean patienInforBean;
    private PatienInforBeanDao dao;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);           //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, true);         // 黑色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.health_consultation_information_add);
    }

    @Override
    protected InformationAddContract.Presenter createPresenter() {
        return new InformationAddPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        intEditView();
        intRadioGroup();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        patienInforBean = new PatienInforBean();
        dao = new GreenDaoHelper(this).initDao().getPatienInforBeanDao();
        if (null != getIntent() && -1 != getIntent().getLongExtra("ID", -1)) {
            patienInforBean = dao.load(getIntent().getLongExtra("ID", 0));
            initInfor();
        }
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.health_consult_picture_information_add_patient);
        tvTitleNext.setText(R.string.user_new_friend_add);
        tvTitleNext.setVisibility(View.VISIBLE);
    }

    public static boolean isOnlyPointNumber(String number) {//保留1位小数正则
        Pattern pattern = Pattern.compile("^\\d+\\.?\\d{0,1}$");
        Matcher matcher = pattern.matcher(number);
        return matcher.matches();
    }

    @SuppressLint("SetTextI18n")
    private void initInfor() {
        etName.setText(patienInforBean.getName());
        etID.setText(patienInforBean.getCard_id());
        rgSex.check("女".equals(patienInforBean.getSex()) ? R.id.rbWoman : R.id.rbMan);
        tvBrithday.setText(patienInforBean.getBirthday());
        etWeight.setText(patienInforBean.getWeight());
        //过敏史
        rgAllergy.check(TextUtils.isEmpty(patienInforBean.getAllergy()) ? R.id.rbAllergyHasnt : R.id.rbAllergyHas);
        AllergyLayout.setVisibility(TextUtils.isEmpty(patienInforBean.getAllergy()) ? View.GONE : View.VISIBLE);
        etAllergy.setText(patienInforBean.getAllergy());
        tvAllergyNumber.setText(patienInforBean.getAllergy().length() + "/30");
        //过往病例
        rgHistory.check(TextUtils.isEmpty(patienInforBean.getHistory()) ? R.id.rbHistoryHasnt : R.id.rbHistoryHas);
        HistoryLayout.setVisibility(TextUtils.isEmpty(patienInforBean.getHistory()) ? View.GONE : View.VISIBLE);
        etHistory.setText(patienInforBean.getHistory());
        tvHistoryNumber.setText(patienInforBean.getHistory().length() + "/50");

        rgLiver.check("正常".equals(patienInforBean.getLiver()) ? R.id.rbLiverHasnt : R.id.rbLiverHas);
        rgRenal.check("正常".equals(patienInforBean.getKidney()) ? R.id.rbRenalHasnt : R.id.rbRenalHas);
    }


    private void intEditView() {

        //体重编辑
        etWeight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                weight = 0;
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void afterTextChanged(Editable s) {
                selectionStart = etWeight.getSelectionStart();
                selectionEnd = etWeight.getSelectionEnd();
                if (!TextUtils.isEmpty(etWeight.getText().toString())) {
                    if (!isOnlyPointNumber(etWeight.getText().toString())) {
                        //删除多余输入的字（不会显示出来）
                        s.delete(selectionStart - 1, selectionEnd);
                        etWeight.setText(s);
                    }
                    weight = Double.parseDouble(etWeight.getText().toString().trim());
                }
                if (weight > 250)
                    etWeight.setText("250");
            }
        });

        //过敏史
        etAllergy.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    tvAllergyNumber.setText(String.valueOf(s.toString().trim().length()));
                    tvAllergyNumber.append("/30");
                } else {
                    tvAllergyNumber.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //过往病例
        etHistory.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.toString().trim().length() > 0) {
                    tvHistoryNumber.setText(String.valueOf(s.toString().trim().length()));
                    tvHistoryNumber.append("/50");
                } else {
                    tvHistoryNumber.setText("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    private void intRadioGroup() {
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
        rgAllergy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                AllergyLayout.setVisibility(checkedId == R.id.rbAllergyHas ? View.VISIBLE : View.GONE);
            }
        });
        rgHistory.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                HistoryLayout.setVisibility(checkedId == R.id.rbHistoryHas ? View.VISIBLE : View.GONE);
            }
        });
    }


    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.tvTitleNext)
    public void tvTitleNext(View view) {
        if (TextUtils.isEmpty(etName.getText().toString().trim()))
            ToastUtil.show(getContext(), R.string.health_consult_picture_information_tip_name);
        else if (TextUtils.isEmpty(tvBrithday.getText().toString().trim()))
            ToastUtil.show(getContext(), R.string.health_consult_picture_information_tip_birthday);
        else if (TextUtils.isEmpty(etWeight.getText().toString().trim()))
            ToastUtil.show(getContext(), R.string.health_consult_picture_information_tip_weight);
        else {
            patienInforBean.setName(etName.getText().toString().trim());
            patienInforBean.setCard_id(etID.getText().toString().trim());
            patienInforBean.setSex(rgSex.getCheckedRadioButtonId() == R.id.rbWoman ? "女" : "男");
            patienInforBean.setBirthday(tvBrithday.getText().toString().trim());
            patienInforBean.setWeight(etWeight.getText().toString().trim());

            patienInforBean.setAllergy(rgAllergy.getCheckedRadioButtonId() == R.id.rbAllergyHas ? etAllergy.getText().toString().trim() : "");
            patienInforBean.setHistory(rgHistory.getCheckedRadioButtonId() == R.id.rbHistoryHas ? etHistory.getText().toString().trim() : "");

            patienInforBean.setLiver(rgLiver.getCheckedRadioButtonId() == R.id.rbLiverHasnt ? "正常" : "异常");
            patienInforBean.setKidney(rgRenal.getCheckedRadioButtonId() == R.id.rbRenalHasnt ? "正常" : "异常");
            if (null == getIntent() || -1 == getIntent().getLongExtra("ID", -1)) {
                patienInforBean.setId(null);
                dao.insert(patienInforBean);
            } else {
                dao.update(patienInforBean);
            }
            finish();
        }
    }

    @OnClick(R.id.tvBrithday)
    public void tvBrithday(View view) {
        new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                tvBrithday.setText(String.valueOf(year + "-" + (monthOfYear + 1) + "-" + dayOfMonth));
            }
        }, 2000, 1, 1).show();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {

            default:
                Log.e("ConsultPicture" + "123456", "requestCode = " + requestCode);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


}
