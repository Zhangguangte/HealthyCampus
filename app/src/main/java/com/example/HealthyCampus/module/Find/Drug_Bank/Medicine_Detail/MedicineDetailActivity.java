package com.example.HealthyCampus.module.Find.Drug_Bank.Medicine_Detail;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.MedicineDetailVo;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.StringUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;

import java.io.IOException;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.VISIBLE;


public class MedicineDetailActivity extends BaseActivity<MedicineDetailContract.View, MedicineDetailContract.Presenter> implements MedicineDetailContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;
    @BindView(R.id.ivFunction)
    ImageView ivFunction;

    //详细数据
    @BindView(R.id.drug_pic)
    ImageView ivDrug;
    @BindView(R.id.tvDetail)
    TextView tvDetail;
    @BindView(R.id.medicine_specifications)
    TextView tvSpecifications;
    @BindView(R.id.medicine_indications)
    TextView tvIndications;
    @BindView(R.id.medicine_price)
    TextView tvPrice;


    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_drug_bank_medicine_detail);
    }

    @Override
    protected MedicineDetailContract.Presenter createPresenter() {
        return new MedicineDetailPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        ivBack.setVisibility(VISIBLE);
        ivFunction.setVisibility(VISIBLE);
        ivFunction.setImageResource(R.drawable.find_collection_empty);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
        showProgressDialog(getResources().getString(R.string.loading_footer_tips));
        if (getIntent() != null) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("id")))
                mPresenter.getMedicineDetailById(getIntent().getStringExtra("id"));
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("NAME")))
                mPresenter.getMedicineDetailByName(getIntent().getStringExtra("NAME"));
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("CODE")))
                mPresenter.getMedicineDetailByCode(getIntent().getStringExtra("CODE"));
        } else {
            ToastUtil.show(getContext(), getString(R.string.data_lose));
            finish();
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void showMedicineDetail(MedicineDetailVo medicineVo) {
        tvTitle.setText(medicineVo.getName());
        tvDetail.setText(StringUtil.dealHtmlText(medicineVo.getDetail()));
        tvSpecifications.setText("规格:" + medicineVo.getSpec() + "/" + medicineVo.getUnit());
        tvIndications.setText(TextUtils.isEmpty(medicineVo.getZhuzhi()) ? getString(R.string.find_self_diagnosis_detail_no) : "主治:\n" + medicineVo.getZhuzhi().replace(" ", "、"));
        tvPrice.setText(TextUtils.isEmpty(medicineVo.getPrice()) ? getString(R.string.mine_drug_bank_price_unknown) : getString(R.string.mine_drug_bank_price) + medicineVo.getPrice());
        GlideUtils.displayMedicineImage(ivDrug, "http:" + medicineVo.getImage());
        dismissProgressDialog();
    }


    @Override
    public void showError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            Response httpException = ((HttpException) throwable).response();
            try {
                DefaultResponseVo response = JsonUtil.format(httpException.errorBody().string(), DefaultResponseVo.class);
                if (response.code == 1007) {
                    ToastUtil.show(getContext(), getString(R.string.database_empty_data));
                    finish();
                } else {
                    ToastUtil.show(this, "未知错误1:" + throwable.getMessage());
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

}
