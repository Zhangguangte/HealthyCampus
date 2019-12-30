package com.example.HealthyCampus.module.Mine.Service.Library;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.BookAdapter;
import com.example.HealthyCampus.common.adapter.HostAdapter;
import com.example.HealthyCampus.common.network.vo.BookVo;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.common.helper.GreenDaoHelper;
import com.example.HealthyCampus.greendao.SearchBookDao;
import com.example.HealthyCampus.greendao.model.SearchBook;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;


public class LibraryActivity extends BaseActivity<LibraryContract.View, LibraryContract.Presenter> implements LibraryContract.View, HostAdapter.onItemClick, BookAdapter.onItemClick {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.btnSearch)
    Button btnSearch;
    @BindView(R.id.btnCanel)
    Button btnCanel;
    @BindView(R.id.searchLayout)
    LinearLayout searchLayout;
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.rvHost)
    RecyclerView rvHost;
    @BindView(R.id.tvHistory)
    TextView tvHistory;
    @BindView(R.id.rvHistory)
    RecyclerView rvHistory;
    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;

    private HostAdapter historyAdapter;
    private BookAdapter bookAdapter;

    private List<String> list = new LinkedList<>();
    private List<BookVo> bookVos = new LinkedList<>();
    private SearchBookDao dao;

    private AnimationDrawable loadAnimation;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_service_library);
    }

    @Override
    protected LibraryContract.Presenter createPresenter() {
        return new LibraryPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initHistoryRecyclerView();
        initBookRecyclerView();
        initEdit();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.mine_service_library);
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);
        StatusBarUtil.setStatusBarDarkTheme(this, true);
    }

    //热门图书
    private void initHostRecyclerView() {
        HostAdapter hostAdapter = new HostAdapter(getContext(), this);
//        rvHost.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvHost.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rvHost.setHasFixedSize(true);
        rvHost.setAdapter(hostAdapter);
    }

    //历史搜索
    private void initHistoryRecyclerView() {
        historyAdapter = new HostAdapter(getContext(), list, this);
//        rvHistory.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvHistory.setLayoutManager(new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL));
        rvHistory.setHasFixedSize(true);
        rvHistory.setAdapter(historyAdapter);
    }

    //查询图书
    private void initBookRecyclerView() {
        bookAdapter = new BookAdapter(getContext(), bookVos);
//        rvHistory.setLayoutManager(new GridLayoutManager(getContext(),3));
        rvSearch.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(bookAdapter);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        initHostRecyclerView();
        dao = new GreenDaoHelper(this).initDao().getSearchBookDao();
        for (SearchBook searchBook : dao.queryBuilder().orderDesc().list()) {
            list.add(searchBook.getBookName());
        }
        historyAdapter.notifyDataSetChanged();
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initEdit() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                ivClear.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
                btnSearch.setVisibility(s.toString().length() > 0 ? View.VISIBLE : View.GONE);
                btnCanel.setVisibility(s.toString().length() > 0 ? View.GONE : View.VISIBLE);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            /*判断是否是“搜索”键*/
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                searchBook();
                return true;
            }
            return false;
        });

        etSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showOrExitList(true);
            }
            return false;
        });

    }

    @OnClick(R.id.ivClear)
    public void ivClear() {
        etSearch.setText("");
    }

    @OnClick(R.id.btnSearch)
    public void btnSearch() {
        searchBook();
    }

    @OnClick(R.id.tvHistory)
    public void tvHistory() {
        list.clear();
        historyAdapter.notifyDataSetChanged();
        dao.deleteAll();
    }

    @OnClick(R.id.btnCanel)
    public void btnCanel() {
        showOrExitList(false);
    }

    @Override
    public void onBackPressed() {
        if (emptyLayout.isShown()) {
            loadingData(false);
        }
        if (rvSearch.isShown())
            showOrExitList(false);
        else
            finish();
    }

    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 999) {
                    Log.e("LibraryDetailAc" + "123456", "response.toString:" + response.toString());
                    ToastUtil.show(this, "查无结果");
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
        loadingData(false);
    }

    @Override
    public void showSuccess(List<BookVo> bookVos) {
        if (null == bookVos || bookVos.size() == 0)
            ToastUtil.show(this, "查无结果");
        else {
            if (emptyLayout.isShown()) {
                this.bookVos.addAll(bookVos);
                bookAdapter.notifyDataSetChanged();
                //添加信息与搜索历史
                if (!TextUtils.isEmpty(etSearch.getText().toString()) && -1 == list.indexOf(etSearch.getText().toString())) {
                    dao.save(new SearchBook(null, etSearch.getText().toString()));
                    list.add(0, etSearch.getText().toString());
                    historyAdapter.notifyDataSetChanged();
                }
            }
        }
        loadingData(false);
    }

    //显隐查询
    private void showOrExitList(boolean val) {
        if (val) {
            bookVos.clear();
            bookAdapter.notifyDataSetChanged();
            rvSearch.setVisibility(View.VISIBLE);
            contentLayout.setVisibility(View.GONE);
        } else {
            etSearch.setText("");
            ivClear.setVisibility(View.GONE);
            btnSearch.setVisibility(View.GONE);
            btnCanel.setVisibility(View.GONE);
            rvSearch.setVisibility(View.GONE);
            contentLayout.setVisibility(View.VISIBLE);
            softInputHide();
        }
    }

    private void softInputHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    private void searchBook() {
        showOrExitList(true);
        if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
            ToastUtil.show(getContext(), R.string.input_no_empty);
        } else {
            loadingData(true);
            bookVos.clear();
            bookAdapter.notifyDataSetChanged();
            mPresenter.searchBook(etSearch.getText().toString().trim());
        }
        softInputHide();
    }

    @Override
    public Context getContext() {
        return this;
    }

    private void loadingData(boolean val) {
        if (val) {
            emptyLayout.setVisibility(View.VISIBLE);
            loadAnimation.start();
            ivLoading.setVisibility(View.VISIBLE);
            NetworkLayout.setVisibility(View.GONE);
            btnSearch.setEnabled(false);
            etSearch.setEnabled(false);
        } else {
            emptyLayout.setVisibility(View.GONE);
            loadAnimation.stop();
            ivLoading.setVisibility(View.GONE);
            NetworkLayout.setVisibility(View.VISIBLE);
            btnSearch.setEnabled(true);
            etSearch.setEnabled(true);
        }
    }


    @Override
    public void selectedName(String bookName) {
        etSearch.setText(bookName);
    }
}
