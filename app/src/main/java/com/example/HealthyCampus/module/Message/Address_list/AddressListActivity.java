package com.example.HealthyCampus.module.Message.Address_list;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.AddressListAdapter;
import com.example.HealthyCampus.common.comparator.AddressPinyinComparator;
import com.example.HealthyCampus.common.network.vo.AddressListVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.SideBar;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class AddressListActivity extends BaseActivity<AddressListContract.View, AddressListContract.Presenter> implements AddressListContract.View, AddressListAdapter.OnItemClickListener {

    @BindView(R.id.sideBar)
    SideBar sideBar;
    @BindView(R.id.dialog)
    TextView dialog;
    @BindView(R.id.sortListView)
    ListView sortListView;
    @BindView(R.id.addressListNo)
    LinearLayout addressListNo;
    @BindView(R.id.AddressFrame)
    FrameLayout AddressFrame;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    private AddressListAdapter listAdapter;
    private List<AddressListVo> addressLists;

    /**
     * 根据拼音来排列ListView里面的数据类
     */
    private AddressPinyinComparator pinyinComparator = new AddressPinyinComparator();

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.message_address_list);
    }

    @Override
    protected AddressListContract.Presenter createPresenter() {
        return new AddressListPresenter();
    }

    @Override
    protected void initView() {
        sideBar.setTextView(dialog);
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.message_address_list);
        ivBack.setVisibility(View.VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    //    返回数据：失败
    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                switch (response.code) {
                    case 1012:
                        ToastUtil.show(this, "快去添加新好友吧");
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
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误:" + throwable.getMessage());
        }
    }

    //返回数据：成功：显示好友
    @Override
    public void showFriends(ArrayList<AddressListVo> addressLists) {
        if (addressLists.size() > 0) {
            Collections.sort(addressLists, pinyinComparator);   //排序
            listAdapter = new AddressListAdapter(this, addressLists, this);
            sortListView.setAdapter(listAdapter);
        }
    }

    // 设置右侧触摸监听
    @Override
    public void listenTouchStatus() {
        sideBar.setOnTouchingLetterChangedListener(s -> {
            //该字母首次出现的位置
            int position = listAdapter.getPositionForSection(s.charAt(0));
            if (position != -1) {
                sortListView.setSelection(position);
            }
        });
    }

    @Override
    public void listenItemStatus() {
        sortListView.setOnItemClickListener((parent, view, position, id) -> {
            String nickname = addressLists.get(position).getNickname();
            Toast.makeText(getContext(), "nickname:" + nickname, Toast.LENGTH_SHORT).show();
        });
    }

    @Override
    public void sidebarShow() {
        sideBar.setVisibility(View.VISIBLE);
        DialogUtil.dismissProgressDialog();
    }


    @Override
    public void showViewByDataStatus(boolean value) {
        AddressFrame.setVisibility(value ? View.VISIBLE : View.GONE);
        addressListNo.setVisibility(value ? View.GONE : View.VISIBLE);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        DialogUtil.showProgressDialog(getContext(), "正在加载中");
        mPresenter.getFriendsInformation();
        mPresenter.listenTouch();
        mPresenter.listenItem();
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public Context getContext() {
        return this;
    }


    @Override
    public void onItemClick(String account) {
        if (getIntent().getBooleanExtra("chat", false)) {       //聊天界面跳至，用于名片使用
            Intent intent = getIntent();
            Bundle bundle = new Bundle();
            bundle.putString("ACCOUNT", account);
            intent.putExtras(bundle);
            setResult(RESULT_OK, intent);
            finish();
        } else {
            Intent intent = new Intent(this, UserInformationActivity.class);    //联系人界面，用于用户信息
            intent.putExtra("ACCOUNT", account);
            startActivity(intent);
        }
    }
}
