package com.example.HealthyCampus.module.Mine.User;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.DateUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Message.Address_list.AddressListActivity;
import com.example.HealthyCampus.module.Message.Chat.ChatActivity;
import com.example.HealthyCampus.module.Message.New_friend.Add_Friend.Add_Friend_Msg.AddFriendMsgActivity;
import com.example.HealthyCampus.module.Mine.Login.LoginActivity;

import org.raphets.roundimageview.RoundImageView;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

public class UserInformationActivity extends BaseActivity<UserInformationContract.View, UserInformationContract.Presenter> implements UserInformationContract.View {

    @BindView(R.id.addFriend)
    TextView addFriend;
    @BindView(R.id.tvUsername)
    TextView tvUsername;
    @BindView(R.id.tvGender)
    TextView tvGender;
    @BindView(R.id.tvBirthday)
    TextView tvBirthday;
    @BindView(R.id.tvRegion)
    TextView tvRegion;
    @BindView(R.id.sendMsg)
    TextView sendMsg;
    @BindView(R.id.tvMtime)
    TextView tvMtime;
    @BindView(R.id.tvSignature)
    TextView tvSignature;
    @BindView(R.id.tvNikename)
    TextView tvNikename;
    @BindView(R.id.RvAvatar)
    RoundImageView RvAvatar;

    private boolean change = false;
    private UserVo userVo;
    private String uid;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_userinfo);
    }

    @Override
    protected UserInformationContract.Presenter createPresenter() {
        return new UserInformationPresenter();
    }

    @Override
    protected void initView() {
        showProgressDialog("正在加载中");
        if (null != getIntent().getExtras())
            userVo = (UserVo) getIntent().getExtras().getSerializable("uservo");
        if (null != userVo) initUserInfo(userVo);
        else if (getIntent().getBooleanExtra("self", false))
            initUserInfo();
        else if (!TextUtils.isEmpty(getIntent().getStringExtra("ACCOUNT"))) {       //聊天名片
            mPresenter.getUserInformation(getIntent().getStringExtra("ACCOUNT"));
        } else if (!TextUtils.isEmpty(getIntent().getStringExtra("userid"))) {
            RequestForm requestForm = new RequestForm(getIntent().getStringExtra("userid"), "");
            mPresenter.searchUser(requestForm);
        } else {            //用户自身
            initUserInfo();
        }
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @OnClick(R.id.btnBack)
    public void btnBack(View view) {
        backResult();
        finish();
    }

    @OnClick(R.id.addFriend)
    public void addFriend(View view) {
        change = true;
        Intent intent = new Intent(this, AddFriendMsgActivity.class);
        intent.putExtra("ID", userVo.getId());
        intent.putExtra("NICKNAME", userVo.getNickname());
        if (userVo.getAvatar() != null) {
            intent.putExtra("ICON", userVo.getAvatar());
        }
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void showError(Throwable throwable) {
        Log.e("UserInformat" + "123456", "getUserInformation error");
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1001) {
                    Log.e("UserInformat" + "123456", "response.toString:" + response.toString());
                    ToastUtil.show(this, "未知错误1");
                } else {
                    ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误3:" + throwable.getMessage());
        }
        dismissProgressDialog();
        Log.e("LoginActivity" + "123456", "throwable.toString:" + throwable.toString());
        Log.e("LoginActivity" + "123456", "throwable.getMessage:" + throwable.getMessage());
    }


    @Override
    public void initUserInfo(UserVo userVo) {
        if (null != userVo) {
            if (!userVo.isfriends) {
                addFriend.setVisibility(View.VISIBLE);
                sendMsg.setVisibility(View.GONE);
            } else {
                addFriend.setVisibility(View.GONE);
                sendMsg.setVisibility(View.VISIBLE);
            }
            Log.e("UserInformat" + "123456", "userVo.toString:" + userVo.toString());
            uid = userVo.getId();
            tvUsername.setText(userVo.getNickname() + "(" + userVo.getAccount() + ")");
            tvGender.setText(userVo.getSex());
            tvBirthday.setText(userVo.getCreateTime().substring(5, 10));
            tvRegion.setText(userVo.getLocation());
            tvMtime.setText(TextUtils.isEmpty(userVo.getLastmodifyTime()) ? "上次更新:" + userVo.getLastmodifyTime().substring(0, 16) : "");
            tvSignature.setText(TextUtils.isEmpty(userVo.getDescription()) ? getResources().getString(R.string.user_information_signature) : userVo.getDescription());
            tvNikename.setText(userVo.getNickname());
            //        RvAvatar.setBackgroundResource(R.drawable.head_default);

            this.userVo = userVo;
        }
        dismissProgressDialog();
    }

    public void initUserInfo() {
        addFriend.setVisibility(View.GONE);
        tvUsername.setText(SPHelper.getString(SPHelper.NICKNAME) + "(" + SPHelper.getString(SPHelper.ACCOUNT) + ")");
        tvGender.setText(SPHelper.getString(SPHelper.USER_SEX));
        tvBirthday.setText(SPHelper.getString(SPHelper.USER_BRITHDAY).substring(5, 10));
        tvRegion.setText(SPHelper.getString(SPHelper.USER_LOCATION));
        tvMtime.setText(TextUtils.isEmpty(SPHelper.getString(SPHelper.USER_MODIFYTIME)) ? "上次更新:" + SPHelper.getString(SPHelper.USER_MODIFYTIME).substring(0, 16) : "");
        tvSignature.setText(TextUtils.isEmpty(SPHelper.getString(SPHelper.USER_DESCRIPTION)) ? getResources().getString(R.string.user_information_signature) : SPHelper.getString(SPHelper.USER_DESCRIPTION));
        tvNikename.setText(SPHelper.getString(SPHelper.NICKNAME));
        //        RvAvatar.setBackgroundResource(R.drawable.head_default);
        dismissProgressDialog();
    }

    @Override
    public void backResult() {
        Intent intent = getIntent();
        intent.putExtra("change", change);
        setResult(2, intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        backResult();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("AddressListActi" + "123456", "onKeyDown:" + 1);
        if (keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键
            backResult();
        }
        return super.onKeyDown(keyCode, event);
    }

    @OnClick(R.id.sendMsg)
    public void sendMsg(View view) {
        Intent intent = new Intent(this, ChatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("uid", uid);
        bundle.putString("anotherName", tvNikename.getText().toString().trim());
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

    }


}
