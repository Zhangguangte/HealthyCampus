package com.example.HealthyCampus.module.Message.New_friend.Add_Friend;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.AddSearchAdapter;
import com.example.HealthyCampus.common.helper.GreenDaoHelper;
import com.example.HealthyCampus.common.helper.SPHelper;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.UserVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.greendao.SearchAddDao;
import com.example.HealthyCampus.greendao.model.SearchAdd;
import com.example.HealthyCampus.module.Message.New_friend.Add_Friend.Add_Friend_Msg.AddFriendMsgActivity;
import com.example.HealthyCampus.module.Mine.User.UserInformationActivity;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class AddFriendActivity extends BaseActivity<AddFriendContract.View, AddFriendContract.Presenter> implements AddFriendContract.View, AddSearchAdapter.onItemOnClick {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvFunction)
    TextView tvAdd;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.clearHistory)
    Button clearHistory;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.btnSearch)
    Button btnSearch;

    private SearchAddDao dao;
    private AddSearchAdapter adapter;
    private List<SearchAdd> list;
    private String searchWord = "";

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.user_add_friend);
    }

    @Override
    protected AddFriendContract.Presenter createPresenter() {
        return new AddFriendPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initSearchDB();
        initEditKey();
        initQuery();
        initRecyclerView();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rvHistory.setLayoutManager(layoutManager);
        //分割线
        rvHistory.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
        adapter = new AddSearchAdapter(getContext(), list, this);
        rvHistory.setAdapter(adapter);
    }


    @Override
    public Context getContext() {
        return this;
    }

    /*数据库*/
    @Override
    public void initSearchDB() {
        //初始化
        GreenDaoHelper daoHelper = new GreenDaoHelper(this);
        dao = daoHelper.initDao().getSearchAddDao();

    }

    @Override
    protected void onResume() {
        initQuery();
        adapter.notifyDataSetChanged();
        super.onResume();
    }

    @Override
    public void initQuery() {
        //查询所有
        list = dao.queryBuilder().list();
        //这里用于判断是否有数据
        if (list.size() == 0) {
            rvHistory.setVisibility(View.GONE);
            clearHistory.setVisibility(View.GONE);
        } else {
            rvHistory.setVisibility(View.VISIBLE);
            clearHistory.setVisibility(View.VISIBLE);
        }
        //list倒序排列
        Collections.reverse(list);
    }


    private void setCustomActionBar() {
        tvTitle.setText(R.string.user_add_friend_add_friend);
        tvAdd.setText(R.string.user_new_friend_add);
        tvAdd.setVisibility(View.VISIBLE);
        ivBack.setVisibility(View.VISIBLE);
    }

    /*搜索文本监听*/
    @Override
    public void initEditKey() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                searchWord = editable.toString().trim();
            }
        });

    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.clearHistory)
    public void clearHistory(View view) {
        dao.deleteAll();
        clearHistory.setVisibility(View.GONE);
        list.clear();
        initQuery();
        adapter.notifyDataSetChanged();
    }

    @OnClick({R.id.btnSearch, R.id.tvFunction})
    public void btnSearch(View view) {
        if (TextUtils.isEmpty(searchWord))
            ToastUtil.show(getContext(), "输入不可以为空");
        else if (searchWord.equals(SPHelper.getString(SPHelper.ACCOUNT)) || searchWord.equals(SPHelper.getString(SPHelper.USER_PHONE))) {
            Intent intent = new Intent(this, UserInformationActivity.class);
            intent.putExtra("self", true);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            showProgressDialog(getString(R.string.loading_footer_tips));
            mPresenter.searchUser(searchWord);
        }
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                Log.e("AddFriendActivity" + "123456", "response.toString:" + response.toString());
                switch (response.code) {
                    case 1005:
                        ToastUtil.show(this, R.string.user_add_friend_no_result);
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
            ToastUtil.show(this, "未知错误3:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    //查询成功：跳至申请消息界面
    @Override
    public void jumpToMsg(UserVo userVo) {
        if (null != userVo && userVo.isfriends) {
            Intent intent = new Intent(this, UserInformationActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("uservo", userVo);
            intent.putExtras(bundle);
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        } else {
            Intent intent = new Intent(this, AddFriendMsgActivity.class);
            assert userVo != null;
            intent.putExtra("ID", userVo.getId());
            intent.putExtra("NICKNAME", userVo.getNickname());
            if (userVo.getAvatar() != null) {
                intent.putExtra("ICON", userVo.getAvatar());
            }
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        }
        dismissProgressDialog();
    }


    @Override
    public void initInsert(String name) {
        try {
            if (list.size() < 8) {
                //删除已经存在重复的搜索历史
                List<SearchAdd> list2 = dao.queryBuilder()
                        .where(SearchAddDao.Properties.Content.eq(name)).build().list();
                dao.deleteInTx(list2);
                //添加
                if (!name.equals(""))
                    dao.insert(new SearchAdd(null, name));
                list.add(new SearchAdd(null, name));
            } else {
                //删除第一条数据，用于替换最后一条搜索
                dao.delete(dao.queryBuilder().list().get(0));
                //删除已经存在重复的搜索历史
                List<SearchAdd> list3 = dao.queryBuilder()
                        .where(SearchAddDao.Properties.Content.eq(name)).build().list();
                dao.deleteInTx(list3);
                //添加
                if (!name.equals(""))
                    dao.insert(new SearchAdd(null, name));
            }
            //添加后更新列表
//            initQuery();
            adapter.notifyDataSetChanged();
        } catch (Exception e) {
            ToastUtil.show(this, "插入失败");
        }

    }

    @Override
    public void onitemonclick(String content) {
        etSearch.setText(content);
    }

}
