package com.example.HealthyCampus.module.Find.Drug_Bank;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
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
import com.example.HealthyCampus.common.adapter.ClassifyAdapter;
import com.example.HealthyCampus.common.adapter.MedicineAdapter;
import com.example.HealthyCampus.common.adapter.TypeAdapter;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MedicineListVo;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.common.widgets.RecycleOnscrollListener;
import com.example.HealthyCampus.common.widgets.pullrecycler.layoutmanager.MyLinearLayoutManager;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.Drug_Bank.Medicine_Detail.MedicineDetailActivity;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.VISIBLE;


public class DrugBankActivity extends BaseActivity<DrugBankContract.View, DrugBankContract.Presenter> implements DrugBankContract.View, TypeAdapter.onItemClick, ClassifyAdapter.onItemClick, MedicineAdapter.onItemClick {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvFunction)
    TextView tvCancel;
    @BindView(R.id.ivEmpty)
    ImageView ivEmpty;
    @BindView(R.id.ivFunction)
    ImageView ivFunction;


    @BindView(R.id.rv_classify_list)
    RecyclerView rvClassify;    //RecycleView视图
    @BindView(R.id.rv_type_list)
    RecyclerView rvType;
    @BindView(R.id.rv_medicine_list)
    RecyclerView rvMedicine;
    @BindView(R.id.rv_search_medicine_list)
    RecyclerView rvSearchMedicine;
    @BindView(R.id.refreshLayout)
    SwipeRefreshLayout srlSearchMedicine;
    @BindView(R.id.refreshSearchLayout)
    SwipeRefreshLayout refreshSearchLayout;

    //标题
    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;
    @BindView(R.id.type_title)
    TextView tvType;
    @BindView(R.id.classify_title)
    TextView tvClassify;

    //数据布局系列
    @BindView(R.id.contentLayout)
    LinearLayout contentLayout;
    @BindView(R.id.tvEmpty)
    TextView tvEmpty;
    @BindView(R.id.tvMedicineEmpty)
    TextView tvMedicineEmpty;
    @BindView(R.id.ivMedicineLoading)
    ImageView ivMedicineLoading;
    @BindView(R.id.ivMedicineEmpty)
    ImageView ivMedicineEmpty;
    @BindView(R.id.mEmptyLayout)
    LinearLayout mEmptyLayout;


    @BindView(R.id.medicineEmptyLayout)
    RelativeLayout medicineEmptyLayout;

    //查询系列
    @BindView(R.id.tvSearch)
    TextView tvSearch;
    @BindView(R.id.etSearch)
    EditText etSearch;
    @BindView(R.id.ivClear)
    ImageView ivClear;
    @BindView(R.id.btnSearch)
    Button btnSearch;

    @BindView(R.id.intervalView)
    View intervalView;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;


    //适配器
    private TypeAdapter typeAdapter;                //类型
    private ClassifyAdapter classifyAdapter;        //分类
    private MedicineAdapter medicineAdapter;        //药品
    private MedicineAdapter searchMedicineAdapter;        //查询药品
    //数据
    private List<String> typeList = new ArrayList<>();          //类型数据
    private List<String> typeMasterList = new ArrayList<>();          //分类作主数据备份
    private List<String> classifyList = new LinkedList<>();     //分类数据
    private List<MedicineListVo> medicineLists = new ArrayList<>();         //药品数据
    private List<MedicineListVo> searchMedicineLists = new ArrayList<>();         //查询药品数据
    private Map<String, List<String>> mData;                    //类型、分类数据
    private StringBuffer type = new StringBuffer("");                    //类型名
    private StringBuffer classifyName = new StringBuffer("");           //分类名

    private AnimationDrawable loadAnimation, loadMedicineAnimation;

    private StringBuffer keyWord = new StringBuffer();
    private int row = 0;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_drug_bank);
    }

    public void hideSoftInput() {
        InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
        View v = getWindow().peekDecorView();
        if (null != v) {
            imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
        }
    }

    @Override
    protected DrugBankContract.Presenter createPresenter() {
        return new DrugBankPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initTypeView();
        initClassifyView();
        initMedicineView();
        initSearchMedicineView();
        initSearchEdit();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.find_drug_bank);
        ivBack.setVisibility(VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.ivClear)
    public void ivClear(View view) {
        etSearch.setText("");
        showBtnSearch(false);
    }

    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        mPresenter.getAllClassify();
    }

    @OnClick(R.id.mEmptyLayout)      //重试:药品数据为空
    public void mEmptyLayout(View view) {
        if (!"".equals(classifyName.toString())) {
            loadingMedicineData(true);
            mPresenter.getAllMedicine(classifyName.toString(), row);
        } else {
            ToastUtil.show(getContext(), getResources().getString(R.string.empty_data));
        }
    }

    @OnClick(R.id.tvSearch)      //选择查询范围
    public void tvSearch(View view) {
        CharSequence[] scope = getResources().getTextArray(R.array.medicine);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setItems(R.array.medicine, (dialog, which) -> tvSearch.setText(scope[which]));
        builder.show();
    }

    @OnClick(R.id.btnSearch)      //查询关键字
    public void btnSearch(View view) {
        searchMedicine();
    }

    @OnClick(R.id.tvFunction)      //清除查询结果
    public void tvFunction(View view) {
        showSearchMedicineViewStatus(true);
    }


    @Override
    protected void initData(Bundle savedInstanceState) {

        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadMedicineAnimation = (AnimationDrawable) ivMedicineLoading.getDrawable();
        loadAnimation.start();

        mPresenter.getAllClassify();
    }

    @Override
    public void onBackPressed() {
        if (rvSearchMedicine.isShown()) {
            showSearchMedicineViewStatus(true);
        } else finish();
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showTypeError() {       //类型：空数据显示
        ivEmpty.setImageResource(R.drawable.drug_no_data);
        tvEmpty.setText(getString(R.string.network_try_again));
        loadingData(false);
    }

    @Override
    public void showMedicineError() {       //类型：根据关键字，获得药品数据空显示
        btnSearch.setEnabled(true);
        etSearch.setEnabled(true);
    }

    @Override
    public void showAllMedicineError() {       //类型：根据分类，获得药品数据空显示
        loadingMedicineData(false);
        tvMedicineEmpty.setText(R.string.network_load_try_again);
    }


    //查询药品数据
    private void searchMedicine() {
        if (!keyWord.toString().equals(etSearch.getText().toString().trim())) {
            //加载数据
            emptyLayout.setVisibility(VISIBLE);
            srlSearchMedicine.setVisibility(View.GONE);
            loadingData(true);

            row = 0;
            searchMedicineAdapter.setLoad(true);

            btnSearch.setEnabled(false);
            etSearch.setEnabled(false);

            searchMedicineLists.clear();
            searchMedicineAdapter.notifyDataSetChanged();

            hideSoftInput();
            mPresenter.getAllMedicineByKey(tvSearch.getText().toString().trim(), etSearch.getText().toString().trim(), row);
            keyWord.setLength(0);
            keyWord.append(etSearch.getText().toString().trim());
        }

    }


    @Override
    public void showError(Throwable throwable) {
        loadingData(false);
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                switch (response.code) {
                    case 999:   //分类药品数据
                        if (ivMedicineEmpty.isShown()) {
                            mEmptyLayout.setEnabled(false);
                            ivMedicineEmpty.setImageResource(R.drawable.empty_data);
                            tvMedicineEmpty.setText(R.string.empty_data);
                        } else {
                            searchMedicineAdapter.setLoad(false);
                            ToastUtil.show(this, getResources().getString(R.string.data_lose));
                        }
                        break;
                    case 1007:  //查询药品数据
                        searchMedicineAdapter.setLoad(false);
                        if (0 == row) {
                            emptyLayout.setVisibility(View.VISIBLE);
                            ivEmpty.setImageResource(R.drawable.empty_data);
                            tvEmpty.setText(getString(R.string.search_no_result));
                            NetworkLayout.setEnabled(false);
                            ToastUtil.show(this, getResources().getString(R.string.search_no_result));
                        } else
                            ToastUtil.show(this, getResources().getString(R.string.data_finish));
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


    }

    @Override
    public void showClassify(Map<String, List<String>> classifyMap, List<String> typelist) {

        loadingData(false);

        //设置所有的类型
        typeList.addAll(typelist);
        typeAdapter.notifyDataSetChanged();

        typeMasterList.addAll(typelist);

        classifyList.addAll(Objects.requireNonNull(classifyMap.get(typeList.get(0))));       //分类数据
        classifyAdapter.notifyDataSetChanged();


        mData = classifyMap;                                         //总数据

        type.setLength(0);
        type.append(typelist.get(0));                                 //默认第一个类


        //空数据隐藏
        emptyLayout.setVisibility(View.GONE);
        contentLayout.setVisibility(View.VISIBLE);
    }

    private void initClassifyView() {
        classifyAdapter = new ClassifyAdapter(this, classifyList, this);
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 3);
        rvClassify.setLayoutManager(layoutManager);
        rvClassify.setItemAnimator(new DefaultItemAnimator());
        rvClassify.setAdapter(classifyAdapter);
    }

    private void initTypeView() {
        typeAdapter = new TypeAdapter(this, typeList, this);
        rvType.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvType.setItemAnimator(new DefaultItemAnimator());
        rvType.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvType.setAdapter(typeAdapter);
    }

    private void initMedicineView() {
        medicineAdapter = new MedicineAdapter(this, medicineLists, this);
        rvMedicine.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvMedicine.setHasFixedSize(true);
        rvMedicine.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvMedicine.setAdapter(medicineAdapter);
        rvMedicine.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (medicineAdapter.isLoad()) {
                    mPresenter.getAllMedicine(classifyName.toString(), ++row);
                }
            }
        });
        refreshSearchLayout.setEnabled(false);
    }

    private void initSearchMedicineView() {
        searchMedicineAdapter = new MedicineAdapter(this, searchMedicineLists, this);
        rvSearchMedicine.setLayoutManager(new MyLinearLayoutManager(getContext()));
        rvSearchMedicine.setHasFixedSize(true);
        rvSearchMedicine.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
        rvSearchMedicine.setAdapter(searchMedicineAdapter);
        rvSearchMedicine.addOnScrollListener(new RecycleOnscrollListener() {
            @Override
            public void onLoadMore() {
                if (searchMedicineAdapter.isLoad()) {
                    mPresenter.getAllMedicineByKey(tvSearch.getText().toString().trim(), etSearch.getText().toString().trim(), ++row);
                }
            }
        });
        srlSearchMedicine.setEnabled(false);
    }

    private void showSearchMedicineViewStatus(boolean val) {
        hideSoftInput();
        if (val) {
            etSearch.setText("");
            tvCancel.setVisibility(View.GONE);
            ivFunction.setVisibility(View.VISIBLE);
            srlSearchMedicine.setVisibility(View.GONE);
            if (typeList.size() > 0) {
                emptyLayout.setVisibility(View.GONE);
                contentLayout.setVisibility(View.VISIBLE);
            } else {
                emptyLayout.setVisibility(View.VISIBLE);
            }
            //清空数据
            row = 0;
            searchMedicineLists.clear();
            searchMedicineAdapter.notifyDataSetChanged();
        } else {
            emptyLayout.setVisibility(View.GONE);
            tvCancel.setVisibility(VISIBLE);
            contentLayout.setVisibility(View.GONE);
            srlSearchMedicine.setVisibility(View.VISIBLE);
            ivFunction.setVisibility(View.GONE);
        }
    }


    @SuppressLint("ClickableViewAccessibility")
    private void initSearchEdit() {
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                showBtnSearch(etSearch.getText().toString().trim().length() > 0);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        etSearch.setOnEditorActionListener((v, actionId, event) -> {
            /*判断是否是“搜索”键*/
            if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                if (TextUtils.isEmpty(etSearch.getText().toString().trim())) {
                    ToastUtil.show(getContext(), getString(R.string.input_no_empty));
                    return true;
                }
                searchMedicine();
                return true;
            }
            return false;
        });

        etSearch.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_UP) {
                showSearchMedicineViewStatus(false);
            }
            return false;
        });


    }

    private void showBtnSearch(boolean val) {
        if (val) {
            btnSearch.setVisibility(View.VISIBLE);
            ivClear.setVisibility(View.VISIBLE);
        } else {
            btnSearch.setVisibility(View.GONE);
            ivClear.setVisibility(View.GONE);
        }
    }


    @Override
    public void selectType(String typ) {       //选择类型,切换相应的分类数据
        classifyList.clear();
        classifyList.addAll(Objects.requireNonNull(mData.get(typ)));
        typeAdapter.setPosi(typeList.indexOf(type));
        classifyAdapter.notifyDataSetChanged();
        medicineAdapter.setLoad(true);
        type.setLength(0);
        type.append(typ);
    }

    @Override
    public void selectClassify(String classify) {       //选择分类,查询相关药品数据,切换相应的药品数据
        rvClassify.setVisibility(View.GONE);
        loadingMedicineData(true);
        row = 0;
        classifyName.setLength(0);
        classifyName.append(classify);
        typeAdapter.setPosi(Objects.requireNonNull(mData.get(type.toString())).indexOf(classifyName.toString()) + 1);

        rvMedicine.scrollToPosition(0);
        medicineLists.clear();
        medicineAdapter.setLoad(true);
        medicineAdapter.notifyDataSetChanged();

        mPresenter.getAllMedicine(classify, row);
    }

    @Override
    public void showAllMedicineSuccess(List<MedicineListVo> medicineListVos, String classifyName) {    //显示药品信息
        if (this.classifyName.toString().equals(classifyName)) {
            loadingMedicineData(false);
            medicineEmptyLayout.setVisibility(View.GONE);
            if (medicineListVos.size() < 15)
                medicineAdapter.setLoad(false);
            medicineLists.addAll(medicineListVos);
            medicineAdapter.notifyDataSetChanged();
            showMedicineStatus(false);
        }
    }

    private void showMedicineStatus(boolean val) {
        if (val) {
            medicineEmptyLayout.setVisibility(VISIBLE);
            refreshSearchLayout.setVisibility(View.GONE);
        } else {
            medicineEmptyLayout.setVisibility(View.GONE);
            refreshSearchLayout.setVisibility(VISIBLE);
        }
    }


    @OnClick(R.id.ivFunction)
    public void ivFunction() {
        Intent intent = new Intent(getContext(), CaptureActivity.class);
        startActivityForResult(intent, ConstantValues.REQUEST_CODE);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == ConstantValues.REQUEST_CODE) {
            //处理扫描结果（在界面上显示）
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (bundle == null) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
//                    ToastUtil.show(getContext(), "解析结果:" + result);
//
//                    Log.e("DrugBankActivity" + "123456", "result:" + result);
//                    tvZZZ.setText(tvZZZ.getText().toString().trim() + "\n" + result);
                    Intent intent = new Intent(this, MedicineDetailActivity.class);
                    intent.putExtra("CODE", result);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoomout, R.anim.zoomin);

                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtil.show(getContext(), "解析二维码失败");
                }
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public void showMedicineByKey(List<MedicineListVo> medicineListVos) {    //显示药品信息
        if (row == 0) {
            loadingData(false);
            srlSearchMedicine.setVisibility(VISIBLE);
            emptyLayout.setVisibility(View.GONE);
        }
        if (medicineListVos.size() < 15) {
            searchMedicineAdapter.setLoad(false);
        }
        searchMedicineLists.addAll(medicineListVos);
        searchMedicineAdapter.notifyDataSetChanged();
        btnSearch.setEnabled(true);
        etSearch.setEnabled(true);
    }

    @Override
    public boolean isClassify(String classifyName) {
        return this.classifyName.toString().equals(classifyName);
    }

    @Override
    public boolean isKeyword(String keyword) {
        return etSearch.getText().toString().equals(keyword);
    }


    @Override
    public void seletClassify(String classifyname) //选择分类
    {
        rvClassify.setVisibility(View.GONE);
        loadingMedicineData(true);
        //切换标题
        tvType.setText(R.string.mine_drug_bank_classify_list);
        tvClassify.setText(R.string.mine_drug_bank_medicine);
        //切换类型数据为分类数据
        typeList.clear();
        typeList.addAll(Objects.requireNonNull(mData.get(type.toString())));
        typeList.add(0, "返回");
        typeAdapter.setPosi(Objects.requireNonNull(mData.get(type.toString())).indexOf(classifyname) + 1);
        typeAdapter.setIsdominate(true);
        typeAdapter.notifyDataSetChanged();

        classifyName.setLength(0);
        classifyName.append(classifyname);                    //确认分类名
        mPresenter.getAllMedicine(classifyname, row);        //加载药品数据

    }

    @Override
    public void backType() {
        row = 0;
        //切换标题
        tvType.setText(R.string.mine_drug_bank_type);
        tvClassify.setText(R.string.mine_drug_bank_classify_list);

        //切换分类数据为类型数据
        typeList.clear();
        typeList.addAll(typeMasterList);
        typeAdapter.setPosi(typeList.indexOf(type.toString()));
        typeAdapter.setIsdominate(false);
        typeAdapter.notifyDataSetChanged();

        //切换显示分类数据
        rvClassify.setVisibility(VISIBLE);
        refreshSearchLayout.setVisibility(View.GONE);
        medicineLists.clear();
        medicineAdapter.notifyDataSetChanged();
    }

    @Override
    public void selectedMedicine(String id) {
        Intent intent = new Intent(this, MedicineDetailActivity.class);
        intent.putExtra("id", id);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    //以后写成一个控件
    private void loadingData(boolean val) {
        if (val) {
            loadAnimation.start();
            ivLoading.setVisibility(View.VISIBLE);
            NetworkLayout.setVisibility(View.GONE);
        } else {
            loadAnimation.stop();
            ivLoading.setVisibility(View.GONE);
            NetworkLayout.setVisibility(View.VISIBLE);
        }
    }


    private void loadingMedicineData(boolean val) {
        if (val) {
            medicineEmptyLayout.setVisibility(VISIBLE);
            refreshSearchLayout.setVisibility(View.GONE);
            loadMedicineAnimation.start();
            ivMedicineLoading.setVisibility(View.VISIBLE);
            mEmptyLayout.setVisibility(View.GONE);
        } else {
            loadMedicineAnimation.stop();
            ivMedicineLoading.setVisibility(View.GONE);
            mEmptyLayout.setVisibility(View.VISIBLE);
        }
    }

}
