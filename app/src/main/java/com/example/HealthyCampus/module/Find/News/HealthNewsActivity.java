package com.example.HealthyCampus.module.Find.News;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.HealthNewsPagerAdapter;
import com.example.HealthyCampus.common.adapter.SearchAdapter;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.data.Bean.SearchBean;
import com.example.HealthyCampus.common.network.NetworkManager;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import okhttp3.Request;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.chad.library.adapter.base.BaseQuickAdapter.ALPHAIN;


public class HealthNewsActivity extends BaseActivity<HealthNewsContract.View, HealthNewsContract.Presenter> implements HealthNewsContract.View, BaseQuickAdapter.RequestLoadMoreListener {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivFunction)
    ImageView ivFunction;

    //查询系列
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.etSearch)
    EditText etSearch;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;
    @BindView(R.id.vpInfo)
    ViewPager vpInfo;

    @BindView(R.id.rvSearch)
    RecyclerView rvSearch;

    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;

    private HealthNewsPagerAdapter healthNewsPagerAdapter;
    private SearchAdapter searchAdapter;

    private List<SearchBean> searchBean;

    private String keyWord;
    private int page = 1;

    private InputMethodManager mImm;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_health_news);
    }

    @Override
    protected HealthNewsContract.Presenter createPresenter() {
        return new HealthNewsPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initFragment();
        initSearchView();
        initEdit();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.find_self_help_hralth_news);
        ivBack.setVisibility(VISIBLE);
    }

    private void initFragment() {
//        healthNewsPagerAdapter = new HealthNewsPagerAdapter(getSupportFragmentManager(),getContext());
//        vpInfo.setAdapter(healthNewsPagerAdapter);
//        tabLayout.setupWithViewPager(vpInfo);
//        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);

        healthNewsPagerAdapter = new HealthNewsPagerAdapter(getSupportFragmentManager(), getContext());
        vpInfo.setOffscreenPageLimit(2);
        vpInfo.setAdapter(healthNewsPagerAdapter);
        //CubeInTransformer 内旋
        //FlipHorizontalTransformer 像翻书一样
        //AccordionTransformer  风琴 拉压
//        vpContent.setPageTransformer(true, new AccordionTransformer());
        vpInfo.setCurrentItem(0);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(vpInfo);

    }

    private void initSearchView() {
        searchAdapter = new SearchAdapter(this);

        rvSearch.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvSearch.setHasFixedSize(true);
        rvSearch.setAdapter(searchAdapter);
        rvSearch.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
//        rvSearch.addItemDecoration(new DividerItemDecoration(this, getResources().getDrawable(R.color.black), LinearLayoutManager.VERTICAL));
        searchAdapter.openLoadAnimation(ALPHAIN);
        searchAdapter.setOnLoadMoreListener(this, rvSearch);

    }

    @Override
    public void onBackPressed() {
        if (rvSearch.isShown()) {
            showSearchViewStatus(false);
        } else
            finish();
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.ivFunction)
    public void ivFunction(View view) {
        if (rvSearch.isShown()) {
            showSearchViewStatus(false);
        } else
            finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //软键盘
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initEdit() {


        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                keyWord = s.toString().trim();
                if (keyWord.length() > 0) {
                    page = 1;
                    getSearchData();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        etSearch.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_UP) {
                    showSearchViewStatus(true);
                }
                return false;
            }
        });
    }

    private void showSearchViewStatus(boolean val) {
        if (val) {
            rvSearch.setVisibility(VISIBLE);
            contentLayout.setVisibility(GONE);
            tvTitle.setVisibility(GONE);
            etSearch.setVisibility(VISIBLE);
            ivSearch.setVisibility(GONE);
            ivFunction.setVisibility(VISIBLE);
        } else {
            softInputHide();
            etSearch.setText("");
            rvSearch.scrollToPosition(0);

            searchAdapter.getData().clear();
            searchAdapter.notifyDataSetChanged();

            rvSearch.setVisibility(GONE);
            contentLayout.setVisibility(View.VISIBLE);
            tvTitle.setVisibility(View.VISIBLE);
            etSearch.setVisibility(View.GONE);
            ivSearch.setVisibility(VISIBLE);
            ivFunction.setVisibility(GONE);
        }
    }


    @Override
    public Context getContext() {
        return this;
    }


    private void softInputHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }


    @Override
    public void onLoadMoreRequested() {
        page++;
        getSearchData();
    }


    private void getSearchData() {
        String url = "http://www.cpoha.com.cn/plus/search.php";
        Map<String, String> hashMap = new HashMap<>();
        hashMap.put("kwtype", 0 + "");
        hashMap.put("channeltype", 0 + "");
        hashMap.put("orderby", "");
        hashMap.put("pagesize", "10");
        hashMap.put("typeid", "0");
        hashMap.put("TotalResult", "50");
        hashMap.put("PageNo", page + "");
        hashMap.put("keyword", keyWord);
        hashMap.put("searchtype", "titlekeyword");
        NetworkManager.postAsyncParams(url, hashMap, new NetworkManager.DataCallBack() {
            @Override
            public void requestFailure(Request request, Exception e) {
            }

            @Override
            public void requestSuccess(String result) {
                Document document = Jsoup.parse(result, ConstantValues.BASE_URL_NEWS);
                searchBean = HealthNewsMessageManager.getSearch(document);
                if (page == 1) {
                    searchAdapter.setNewData(searchBean);
                } else {
                    searchAdapter.addData(searchBean);
                    searchAdapter.loadMoreComplete();
                    if (searchBean.size() == 0) {
                        searchAdapter.loadMoreEnd(true);
                    }
                }
            }
        });
    }


    @OnClick(R.id.ivSearch)
    public void ivSearch(View view) {
        showSearchViewStatus(true);
        etSearch.requestFocus();
        etSearch.setFocusable(true);
        mImm.showSoftInput(etSearch, 0);
    }

}
