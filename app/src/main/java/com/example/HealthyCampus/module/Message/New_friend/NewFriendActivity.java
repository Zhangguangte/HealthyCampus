package com.example.HealthyCampus.module.Message.New_friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.MessageRecyclerAdapter;
import com.example.HealthyCampus.common.data.form.RequestForm;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.RequestFriendVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Message.New_friend.Add_Friend.AddFriendActivity;
import com.example.HealthyCampus.module.Message.New_friend.Apply_Friend.ApplyFriendActivity;
import com.example.HealthyCampus.module.Message.New_friend.List.NewFriendListActivity;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class NewFriendActivity extends BaseActivity<NewFriendContract.View, NewFriendContract.Presenter> implements NewFriendContract.View, MessageRecyclerAdapter.onItemClick {

    @BindView(R.id.rvNotice)
    RecyclerView rvNotice;
    @BindView(R.id.rvRecommend)
    RecyclerView rvRecommend;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvFunction)
    TextView tvAdd;
    @BindView(R.id.noticeMore)
    TextView noticeMore;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    private MessageRecyclerAdapter noticeAdapter;
    private Bundle bundle = new Bundle();
    private boolean val = false;
    private List<RequestFriendVo> requestFriendVos = new ArrayList<RequestFriendVo>();
    private int position = -1;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_new_friend);
    }

    @Override
    protected NewFriendContract.Presenter createPresenter() {
        return new NewFriendPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initRvNotice();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mPresenter.requestFriends();
    }


    @Override
    public Context getContext() {
        return this;
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
    public void showSuccess(DefaultResponseVo defaultResponseVo, int position) {
        if (defaultResponseVo.code == 3000) {
            ToastUtil.show(getContext(), "添加成功");
            noticeAdapter.agree(position);
            mPresenter.requestFriends();
        }
    }

    @Override
    public void initRvNotice() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvNotice.setLayoutManager(layoutManager);
        //分割线
        rvNotice.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        noticeAdapter = new MessageRecyclerAdapter(requestFriendVos, getContext(), true, this);
        rvNotice.setAdapter(noticeAdapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        mPresenter.requestFriends();
    }

    @Override
    public void showRequestFriends(ArrayList<RequestFriendVo> requestFriendVos) {
        noticeAdapter.clear();
        if (requestFriendVos == null || requestFriendVos.size() == 0) {
            rvNotice.setVisibility(View.GONE);
        } else {
            rvNotice.setVisibility(View.VISIBLE);
        }
        bundle.putSerializable("list", requestFriendVos);
        if (requestFriendVos != null && requestFriendVos.size() > 3)
            val = true;
        noticeAdapter.addList(requestFriendVos);
    }


    private void setCustomActionBar() {
        tvTitle.setText(R.string.user_new_friend);
        tvAdd.setText(R.string.user_new_friend_add);
        tvAdd.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @OnClick(R.id.tvFunction)
    public void tvAdd(View view) {
        Intent intent = new Intent(this, AddFriendActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @OnClick(R.id.noticeMore)
    public void noticeMore(View view) {
        Intent intent = new Intent(this, NewFriendListActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    public void onItemButtonClick(String userid, int position) {
        RequestForm requestForm = new RequestForm(userid, "");
        mPresenter.saveRequestFriend(requestForm, position);
    }

    @Override
    public void onItemViewClick(String userid, String nickname, String content, String status, int pos) {
        if (status.equals("F_REQUEST")) {
            Intent intent = new Intent(this, UserInformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userid", userid);
            intent.putExtras(bundle);
            position = pos;
            startActivityForResult(intent, 3);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            Intent intent = new Intent(this, ApplyFriendActivity.class);
            Bundle bundle = new Bundle();
            bundle.putString("userid", userid);
            bundle.putString("nickname", nickname);
            bundle.putString("content", content);
            bundle.putString("status", status);
            intent.putExtras(bundle);
            position = pos;
            startActivityForResult(intent, 3);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
    }

}
