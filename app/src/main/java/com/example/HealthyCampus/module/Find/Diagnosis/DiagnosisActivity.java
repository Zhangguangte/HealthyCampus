package com.example.HealthyCampus.module.Find.Diagnosis;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.DiagnosisPagerAdapter;
import com.example.HealthyCampus.common.adapter.DiseaseAdapter;
import com.example.HealthyCampus.common.adapter.MedicineAdapter;
import com.example.HealthyCampus.common.adapter.RecipesPagerAdapter;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortListVo;
import com.example.HealthyCampus.common.network.vo.DiseaseSortVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.RecycleOnscrollListener;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.Detail.DiseaseDetailActivity;
import com.example.HealthyCampus.module.Find.Nearby.NearbyActivity;
import com.flyco.tablayout.SlidingTabLayout;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;


public class DiagnosisActivity extends BaseActivity<DiagnosisContract.View, DiagnosisContract.Presenter> implements DiagnosisContract.View, DiseaseAdapter.onItemClick {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivSearch)
    ImageView ivSearch;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivFunction)
    ImageView ivFunction;


    @BindView(R.id.rvDisease)
    RecyclerView rvDisease;
    @BindView(R.id.vp_content)
    ViewPager vpContent;
    @BindView(R.id.tl_tabs)
    SlidingTabLayout tlTabs;

    private DiseaseAdapter diseaseAdapter;
    private List<DiseaseSortVo> diseaseSortVos = new LinkedList<>();

    private InputMethodManager mImm;

    private int row = 0;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_self_diagnosis);
    }

    @Override
    protected DiagnosisContract.Presenter createPresenter() {
        return new DiagnosisPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initViewPager();
        initDiseaseView();
        initEdit();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.find_self_help_diagnosis);
        ivBack.setVisibility(VISIBLE);
    }

    private void initEdit() {

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            /*判断是否是“搜索”键*/
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                    ToastUtil.show(getContext(), getString(R.string.input_no_empty));
                    return true;
                }
                showProgressDialog(getString(R.string.loading_footer_tips));
                diseaseSortVos.clear();
                diseaseAdapter.notifyDataSetChanged();

                diseaseAdapter.setIsLoad(true);
                mPresenter.getDiseaseInfo(etSearch.getText().toString().trim(), row);
                softInputHide();
                return true;
            }
            return false;
        });

    }

    private void softInputHide() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    public void onBackPressed() {
        if (rvDisease.isShown()) {
            showOrExitEdit(false);
        } else
            finish();
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        if (rvDisease.isShown()) {
            showOrExitEdit(false);
        } else
            finish();
    }

    @OnClick(R.id.ivSearch)
    public void ivSearch(View view) {
        showOrExitEdit(true);

        etSearch.requestFocus();
        etSearch.setFocusable(true);
        mImm.showSoftInput(etSearch, 0);
    }

    @OnClick(R.id.ivFunction)
    public void ivFunction(View view) {
        showOrExitEdit(false);

    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        mImm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE); //软键盘
    }

    private void initDiseaseView() {
        diseaseAdapter = new DiseaseAdapter(this, diseaseSortVos, this);
        rvDisease.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvDisease.setHasFixedSize(true);
        rvDisease.setAdapter(diseaseAdapter);
        rvDisease.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (diseaseAdapter.isLoad()) {
                    mPresenter.getDiseaseInfo(etSearch.getText().toString().trim(), ++row);
                }
            }
        });
    }


    private void initViewPager() {
        DiagnosisPagerAdapter diagnosisPagerAdapter = new DiagnosisPagerAdapter(getSupportFragmentManager(),
                this);
        vpContent.setOffscreenPageLimit(2);
        vpContent.setAdapter(diagnosisPagerAdapter);
        //CubeInTransformer 内旋
        //FlipHorizontalTransformer 像翻书一样
        //AccordionTransformer  风琴 拉压
//        vpContent.setPageTransformer(true, new AccordionTransformer());
        vpContent.setCurrentItem(0);

        tlTabs.setViewPager(vpContent);
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
                switch (response.code) {
                    case 1008:
                        diseaseAdapter.setIsLoad(false);
                        if (row == 0)
                            ToastUtil.show(this, "查无数据");
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            ToastUtil.show(this, "未知错误2:" + throwable.getMessage());
        }
        dismissProgressDialog();
    }

    @Override
    public void showDiseaseSuccess(List<DiseaseSortVo> diseaseSortVoList) {
        diseaseSortVos.addAll(diseaseSortVoList);
        if (row == 0)
            dismissProgressDialog();
        if (diseaseSortVoList.size() < 15)
            diseaseAdapter.setIsLoad(false);
        diseaseAdapter.notifyDataSetChanged();
    }


    private void showOrExitEdit(boolean val) {
        if (val) {
            ivSearch.setVisibility(GONE);
            tvTitle.setVisibility(GONE);
            etSearch.setVisibility(VISIBLE);
            rvDisease.setVisibility(VISIBLE);

            ivFunction.setVisibility(View.VISIBLE);
        } else {
            softInputHide();

            ivSearch.setVisibility(VISIBLE);
            tvTitle.setVisibility(VISIBLE);
            etSearch.setText("");
            etSearch.setVisibility(GONE);
            ivFunction.setVisibility(GONE);
            rvDisease.setVisibility(GONE);

            diseaseSortVos.clear();
            diseaseAdapter.notifyDataSetChanged();

            row = 0;
        }
    }


    @Override
    public void detailDisease(String id) {
        Intent intent = new Intent(this, DiseaseDetailActivity.class);
        intent.putExtra("ID", id);
        startActivity(intent);
        overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }
}
