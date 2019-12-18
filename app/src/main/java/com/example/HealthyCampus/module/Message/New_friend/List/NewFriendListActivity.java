package com.example.HealthyCampus.module.Message.New_friend.List;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.MessageRecyclerAdapter;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Message.New_friend.Apply_Friend.ApplyFriendActivity;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class NewFriendListActivity extends BaseActivity<NewFriendListContract.View, NewFriendListContract.Presenter> implements NewFriendListContract.View, MessageRecyclerAdapter.onItemClick {


    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvFunction)
    TextView tvClear;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.rvMore)
    RecyclerView rvMore;

    private MessageRecyclerAdapter moreAdapter;
    private ArrayList<RequestFriendVo> requestFriendVos;
    private boolean clear = false;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_new_friend_list);
    }

    @Override
    protected NewFriendListContract.Presenter createPresenter() {
        return new NewFriendListPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initRecycler();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void clearList() {
        clear = true;
        ToastUtil.show(getContext(), "清空成功");
        moreAdapter.clear();
        rvMore.setVisibility(View.GONE);
        tvClear.setClickable(false);
    }

    @Override
    public void showRequestFriends(ArrayList<RequestFriendVo> requestFriendVos) {
        moreAdapter.clear();
        if (requestFriendVos == null || requestFriendVos.size() == 0) {
            rvMore.setVisibility(View.GONE);
        } else {
            rvMore.setVisibility(View.VISIBLE);
        }
        moreAdapter.addList(requestFriendVos);
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

    private void initRecycler() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvMore.setLayoutManager(layoutManager);
        //分割线
        rvMore.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        requestFriendVos = (ArrayList<RequestFriendVo>) getIntent().getExtras().getSerializable("list");
        if (null == requestFriendVos || requestFriendVos.size() == 0) {
            rvMore.setVisibility(View.GONE);
            tvClear.setClickable(false);
        } else {
            rvMore.setVisibility(View.VISIBLE);
            tvClear.setClickable(true);
        }
        moreAdapter = new MessageRecyclerAdapter(requestFriendVos, getContext(), false, this);
        rvMore.setAdapter(moreAdapter);
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.user_new_friend_notice);
        tvClear.setText(R.string.clear);
        tvClear.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
    }


    @OnClick(R.id.tvFunction)
    public void tvClear(View view) {
        MaterialDialog.Builder materialDialog = new MaterialDialog.Builder(this);
        materialDialog.title("点击确定删除所有好友通知")
                .negativeText("取消")
                .onNegative(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {

                    }
                })
                .positiveText("确定")
                .onPositive(new MaterialDialog.SingleButtonCallback() {
                    @Override
                    public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                        mPresenter.clearRequestFriends();
                    }
                });
        MaterialDialog materialDialog1 = materialDialog.build();
        materialDialog1.show();
    }


    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @Override
    public void showSuccess(DefaultResponseVo defaultResponseVo, int position) {
        if (defaultResponseVo.code == 3000) {
            moreAdapter.agree(position);
            ToastUtil.show(getContext(), "添加成功");
        }
    }


    @Override
    public void onItemButtonClick(String userid, int position, String fNickname) {
        Map<String, String> map = new HashMap();
        map.put("s_nickname", SPHelper.getString(SPHelper.NICKNAME));
        map.put("f_nickname", fNickname);
        RequestForm requestForm = new RequestForm(userid, "", map);
        mPresenter.saveRequestFriend(requestForm, position);
    }

    @Override
    public void onItemViewClick(String userid, String nickname, String content, String status, int position) {
        if (status.equals("F_REQUEST")) {
            Intent intent = new Intent(this, UserInformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userid", userid);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            Intent intent = new Intent(this, ApplyFriendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userid", userid);
            bundle.putString("nickname", nickname);
            bundle.putString("content", content);
            bundle.putString("status", status);
            bundle.putInt("position", position);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestFriends();
    }
}
