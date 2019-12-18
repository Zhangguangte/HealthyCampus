package com.example.HealthyCampus.module.Message.New_friend.Add_Friend.Add_Friend_Msg;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.squareup.picasso.Picasso;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class AddFriendMsgActivity extends BaseActivity<AddFriendMsgContract.View, AddFriendMsgContract.Presenter> implements AddFriendMsgContract.View {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.tvFunction)
    TextView tvFunction;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.etContent)
    EditText etContent;

    private String Content = "";

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_add_friend_msg);
    }

    @Override
    protected AddFriendMsgContract.Presenter createPresenter() {
        return new AddFriendMsgPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initEdit();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        tvName.setText(getIntent().getStringExtra("NICKNAME"));
        etContent.setText("你好！我是" + SPHelper.getString(SPHelper.NICKNAME));

//        Picasso.with(AddFriendMsgActivity.this)
//                .load(getIntent().getStringExtra("ICON"))
//                .placeholder(R.mipmap.head_default)
//                .into(ivIcon);
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.user_add_friend_add_msg_add_friend);
        ivBack.setVisibility(View.VISIBLE);
        tvFunction.setText(R.string.user_new_friend_add);
        tvFunction.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.tvFunction)
    public void tvFunction(View view) {
        RequestForm requestForm = mPresenter.encapsulationData(getIntent().getStringExtra("ID"), Content);
        mPresenter.sendRequestFriend(requestForm);
    }

    private void initEdit() {
        etContent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Content = editable.toString().trim();
            }
        });
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                Log.e("AddFriendActivity" + "123456", "response.toString:" + response.toString());
                if (response.code == 1003) {
                    ToastUtil.show(this, "Invalid Parameter");
                } else {
                    ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误3:" + throwable.getMessage());
        }
        Log.e("AddFriendActivity" + "123456", "throwable.getMessage:" + throwable.getMessage());
    }

    @Override
    public void sendSuccess() {
        ToastUtil.show(getContext(), R.string.user_add_friend_add_msg_send_success);
        Intent intent = getIntent();
        intent.putExtra("fresh", true);
        setResult(2, intent);
        finish();
    }

}
