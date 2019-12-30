package com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.ConsultPatientAdapter;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.common.helper.GreenDaoHelper;
import com.example.HealthyCampus.greendao.PatienInforBeanDao;
import com.example.HealthyCampus.greendao.model.PatienInforBean;
import com.example.HealthyCampus.module.HomePage.Consultation.Picture.Information.Add.InformationAddActivity;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;


public class InformationActivity extends BaseActivity<InformationContract.View, InformationContract.Presenter> implements InformationContract.View, ConsultPatientAdapter.onItemClick {


    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.tvTitleNext)
    TextView tvTitleNext;

    @BindView(R.id.rvPatient)
    RecyclerView rvPatient;

    private ConsultPatientAdapter patientAdapter;
    private List<PatienInforBean> patienInforBeans = new LinkedList<>();
    private PatienInforBeanDao dao;

    private List<PatienInforBean> list = new LinkedList<>();
    private int max = 0;

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setStatusBarColor(this, R.color.white);           //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, true);         // 黑色字体
    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.health_consultation_information);
    }

    @Override
    protected InformationContract.Presenter createPresenter() {
        return new InformationPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
        initPictureRecycle();
    }


    @Override
    protected void initData(Bundle savedInstanceStatme) {
        max = getIntent().getIntExtra("MAX", 0);
        dao = new GreenDaoHelper(this).initDao().getPatienInforBeanDao();
        loadData();
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadData();
    }

    private void loadData() {
        patienInforBeans.clear();
        patienInforBeans.addAll(dao.queryBuilder().orderDesc().list());
        patientAdapter.notifyDataSetChanged();
    }


    private void setCustomActionBar() {
        tvTitle.setText(R.string.health_consult_picture_information);
        tvTitleNext.setText(R.string.sure);
        tvTitleNext.setVisibility(View.VISIBLE);
    }

    private void initPictureRecycle() {
        patientAdapter = new ConsultPatientAdapter(getContext(), patienInforBeans, this);
        rvPatient.setLayoutManager(new GridLayoutManager(getContext(), 2));
        rvPatient.setHasFixedSize(true);
        rvPatient.setAdapter(patientAdapter);
    }

    @Override
    public Context getContext() {
        return this;
    }


    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @OnClick(R.id.tvTitleNext)
    public void tvTitleNext(View view) {
        Intent intent = new Intent();
        Bundle bundle = new Bundle();
        bundle.putSerializable("USER", (Serializable) list);
        setResult(RESULT_OK, intent.putExtras(bundle));
        finish();
    }

    public void addLayout() {
        Intent intent = new Intent(this, InformationAddActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void remove(int position) {
        dao.deleteByKey(patienInforBeans.get(position).getId());
        patienInforBeans.remove(position);
        patientAdapter.notifyDataSetChanged();
    }

    @Override
    public void update(int position) {
        Intent intent = new Intent(this, InformationAddActivity.class);
        intent.putExtra("ID", patienInforBeans.get(position).getId());
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            default:
                Log.e("ConsultPicture" + "123456", "requestCode = " + requestCode);
                break;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void selected(PatienInforBean patienInforBean, boolean val) {
        if ((max - list.size()) > 0) {
            ToastUtil.show(getContext(), "最大选取" + max + "个");
            return;
        }
        if (val)
            list.add(patienInforBean);
        else
            list.remove(patienInforBean);
    }

}
