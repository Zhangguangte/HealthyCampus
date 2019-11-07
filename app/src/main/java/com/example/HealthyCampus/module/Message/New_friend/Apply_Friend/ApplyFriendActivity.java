package com.example.HealthyCampus.module.Message.New_friend.Apply_Friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Message.New_friend.Add_Friend.AddFriendActivity;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;

import org.raphets.roundimageview.RoundImageView;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class ApplyFriendActivity extends BaseActivity<ApplyFriendContract.View, ApplyFriendContract.Presenter> implements ApplyFriendContract.View {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvHead)
    RoundImageView rvHead;
    @BindView(R.id.tvNickname)
    TextView tvNickname;
    @BindView(R.id.tvContent)
    TextView tvContent;
    @BindView(R.id.tvTip)
    TextView tvTip;
    @BindView(R.id.btnRefuse)
    Button btnRefuse;
    @BindView(R.id.btnAgree)
    Button btnAgree;
    @BindView(R.id.btnLayout)
    LinearLayout btnLayout;
    @BindView(R.id.rlUser)
    RelativeLayout rlUser;


    private boolean val = false;
    private String staus;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_new_friend_apply_friend);
    }

    @Override
    protected ApplyFriendContract.Presenter createPresenter() {
        return new ApplyFriendPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initApplyView();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误:" + throwable.getMessage());
        }
    }

    @Override
    public void showSuccess(DefaultResponseVo defaultResponseVo, int i) {
        Intent intent = getIntent();
        if (defaultResponseVo.code == 3000) {
            if (i == 0) {
                ToastUtil.show(getContext(), R.string.user_new_friend_apply_already_agree);
                tvTip.setText(R.string.user_new_friend_apply_already_agree);

            } else if (i == 1) {
                ToastUtil.show(getContext(), R.string.user_new_friend_apply_already_refuse);
                tvTip.setText(R.string.user_new_friend_apply_already_refuse);
            }
            val = true;
            btnLayout.setVisibility(View.GONE);
            tvTip.setVisibility(View.VISIBLE);
        }
    }


    @Override
    public void initApplyView() {
        if (null != getIntent().getExtras()) {
            tvNickname.setText(getIntent().getExtras().getString("nickname"));
            tvContent.setText(getIntent().getExtras().getString("content"));
            switch (getIntent().getExtras().getString("status")) {
                case "REQUEST":
                    break;
                case "F_REQUEST":
                    requestResponse(getIntent().getExtras().getString("userid"));
                    break;
                case "F_AGREE":
                case "AGREE":
                    tvTip.setText(R.string.user_new_friend_apply_already_agree);
                    btnLayout.setVisibility(View.GONE);
                    tvTip.setVisibility(View.VISIBLE);
                    break;
                case "REFUSE":
                    tvTip.setText(R.string.user_new_friend_apply_already_refuse);
                    tvTip.setVisibility(View.VISIBLE);
                    btnLayout.setVisibility(View.GONE);
                    break;
                case "F_REFUSE":
                    tvTip.setText(R.string.user_new_friend_apply_other_refuse);
                    tvTip.setVisibility(View.VISIBLE);
                    btnLayout.setVisibility(View.GONE);
                    break;
            }
        }

//        Picasso.with(this)
//                    .load(getIntent().getExtras().getString(""))
//                    .placeholder(R.mipmap.head_default)
//                    .into(rvHead);
    }

    @Override
    public void requestResponse(String userid) {
        RequestForm requestForm = new RequestForm(userid);
        mPresenter.searchUser(requestForm);
    }

    @Override
    public void jumpToUserInformation(UserVo userVo) {
        Intent intent = new Intent(this, UserInformationActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("uservo", userVo);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    private void setCustomActionBar() {
        tvTitle.setText(R.string.user_new_friend_apply);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.rlUser)
    public void rlUser(View view) {
        requestResponse(getIntent().getExtras().getString("userid"));
    }


    @OnClick(R.id.tvFunction)
    public void tvAdd(View view) {
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.btnAgree)
    public void btnAgree(View view) {
        RequestForm requestForm = new RequestForm(getIntent().getExtras().getString("userid"), "");
        mPresenter.saveRequestFriend(requestForm);
    }

    @OnClick(R.id.btnRefuse)
    public void btnRefuse(View view) {
        RequestForm requestForm = new RequestForm(getIntent().getExtras().getString("userid"), "");
        mPresenter.refuseRequestFriend(requestForm);
    }


}
