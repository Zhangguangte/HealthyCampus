package com.example.HealthyCampus.module.Mine.Feedback;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;


public class FeedbackActivity extends BaseActivity<FeedbackContract.View, FeedbackContract.Presenter> implements FeedbackContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvNumber)
    TextView tvNumber;
    @BindView(R.id.etAdvice)
    EditText etAdvice;
    @BindView(R.id.etPhone)
    EditText etPhone;
    @BindView(R.id.tvSumbit)
    TextView tvSumbit;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_feedback);
    }

    @Override
    protected FeedbackContract.Presenter createPresenter() {
        return new FeedbackPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initEdit();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.mine_opinion);
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    private void initEdit() {
        etAdvice.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                tvSumbit.setEnabled(etAdvice.getText().toString().trim().length() > 10);
                tvNumber.setText(TextUtils.isEmpty(etAdvice.getText().toString()) ? "" : etAdvice.getText().toString().trim().length() + "/200");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @OnClick(R.id.tvSumbit)
    public void tvSumbit(View view) {
        mPresenter.sendFeed(etAdvice.getText().toString(), etPhone.getText().toString());
        ToastUtil.show(getContext(), "提交成功，感谢你的反馈");
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }
}
