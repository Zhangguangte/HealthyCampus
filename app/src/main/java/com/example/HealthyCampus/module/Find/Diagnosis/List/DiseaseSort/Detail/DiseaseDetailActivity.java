package com.example.HealthyCampus.module.Find.Diagnosis.List.DiseaseSort.Detail;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.RequiresApi;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.network.vo.DefaultResponseVo;
import com.example.HealthyCampus.common.network.vo.DiseaseDetailVo;
import com.example.HealthyCampus.common.utils.DialogUtil;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.JsonUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.module.Find.Drug_Bank.Medicine_Detail.MedicineDetailActivity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import retrofit2.Response;
import retrofit2.adapter.rxjava.HttpException;

import static android.view.View.VISIBLE;


public class DiseaseDetailActivity extends BaseActivity<DiseaseDetailContract.View, DiseaseDetailContract.Presenter> implements DiseaseDetailContract.View {


    //自定义标题框
    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.ivBack)
    ImageView ivBack;

    //加载布局
    @BindView(R.id.NetworkLayout)
    LinearLayout NetworkLayout;
    @BindView(R.id.ivLoading)
    ImageView ivLoading;
    @BindView(R.id.emptyLayout)
    RelativeLayout emptyLayout;

    @BindView(R.id.ivHead)
    ImageView ivHead;
    @BindView(R.id.tv_disease_intro)
    TextView tvIntro;
    @BindView(R.id.tv_disease_detailed)
    TextView tvDiseaseDetailed;
    @BindView(R.id.tv_disease_alias)
    TextView tvAlias;
    @BindView(R.id.tv_disease_contagious)
    TextView tvContagious;
    @BindView(R.id.tv_disease_multiple_people)
    TextView tvMultiplePeople;
    @BindView(R.id.tv_disease_visit_department)
    TextView tvVisitDepartment;
    @BindView(R.id.tv_disease_part)
    TextView tvDiseasePart;
    @BindView(R.id.tv_disease_insurance)
    TextView tvDiseaseInsurance;
    @BindView(R.id.tv_disease_check)
    TextView tvDiseaseCheck;
    @BindView(R.id.tv_disease_typical_symptoms)
    TextView tvDiseaseTypicalSymptoms;
    @BindView(R.id.tv_disease_case)
    TextView tvDiseaseCase;
    @BindView(R.id.tv_disease_prevention)
    TextView tvDiseasePrevention;
    @BindView(R.id.tv_cure_way)
    TextView tvCureWay;
    @BindView(R.id.tv_disease_cure_rate)
    TextView tvDiseaseCureRate;
    @BindView(R.id.tv_cure_cost)
    TextView tvCureCost;
    @BindView(R.id.tv_cure_time)
    TextView tvCureTime;
    @BindView(R.id.tv_disease_case_detailed)
    TextView tvCaseDetailed;
    @BindView(R.id.tv_disease_prevention_detailed)
    TextView tvPreventionDetailed;
    @BindView(R.id.complicationLayout)
    LinearLayout complicationLayout;
    @BindView(R.id.recommendDrugLayout)
    LinearLayout recommendDrugLayout;

    private String emptyString;

    private AnimationDrawable loadAnimation;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.find_self_diagnosis_detail);
    }

    @Override
    protected DiseaseDetailContract.Presenter createPresenter() {
        return new DiseaseDetailPresenter();
    }

    @Override
    protected void initView() {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        ivBack.setVisibility(VISIBLE);
    }

    @OnClick(R.id.ivBack)
    public void ivBack(View view) {
        finish();
    }


    @Override
    protected void initData(Bundle savedInstanceState) {
        loadAnimation = (AnimationDrawable) ivLoading.getDrawable();
        loadAnimation.start();
        emptyString = getResources().getString(R.string.find_self_diagnosis_detail_no);
        if (getIntent() != null) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("ID")))
                mPresenter.getDiseaseDetailById(getIntent().getStringExtra("ID"));
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("NAME")))
                mPresenter.getDiseaseDetailByName(getIntent().getStringExtra("NAME"));
//            Log.e("DiseaseDetailA" + "123456", "NAME():" + getIntent().getStringExtra("NAME"));
//            Log.e("DiseaseDetailA" + "123456", "ID():" + getIntent().getStringExtra("ID"));
        } else {
            ToastUtil.show(getContext(), getString(R.string.data_lose));
            finish();
        }

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
                        ToastUtil.show(getContext(), getString(R.string.data_lose));
                        finish();
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
        loadingData(false);
        emptyLayout.setVisibility(View.VISIBLE);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    @Override
    public void showDiseaseDetailSuccess(DiseaseDetailVo diseaseDetailVo) {
        GlideUtils.display(ivHead, diseaseDetailVo.getUrl(), true);
        tvTitle.setText(diseaseDetailVo.getDiseaseName());
        //简介
        tvIntro.setText("\t\t" + diseaseDetailVo.getIntroduce());
        if (diseaseDetailVo.getIntroduce().length() > 65)
            tvDiseaseDetailed.setVisibility(VISIBLE);
        //病因
        tvDiseaseCase.setText(diseaseDetailVo.getDiseaseCase().trim());
        if (diseaseDetailVo.getDiseaseCase().length() > 65)
            tvCaseDetailed.setVisibility(VISIBLE);
        //预防
        tvDiseasePrevention.setText(diseaseDetailVo.getPrevention().trim());
        if (diseaseDetailVo.getPrevention().length() > 65)
            tvPreventionDetailed.setVisibility(VISIBLE);

        tvAlias.setText(diseaseDetailVo.getAlias());
        tvContagious.setText(diseaseDetailVo.getContagious());
        tvMultiplePeople.setText(diseaseDetailVo.getPopulation());

        //发病分类
        //科室
        tvVisitDepartment.setText(diseaseDetailVo.getCureDepart().replace(")(", "\n").replace("(", "").replace(")", ""));
        //部位
        tvDiseasePart.setText(diseaseDetailVo.getPart().replace(")(", "\n").replace("(", "").replace(")", ""));

        //基本信息
        tvDiseaseInsurance.setText(diseaseDetailVo.getInsurance());
        tvDiseaseCheck.setText(diseaseDetailVo.getCheck().replace(" ", "\n"));

        //疾病知识
        tvDiseaseTypicalSymptoms.setText(diseaseDetailVo.getTypicalSymptoms().replace(" ", "   "));


        complicationLayout(diseaseDetailVo.getComplication());         //设置并发症数据
        recommendDrugLayout(diseaseDetailVo.getRecommendDrug());       //设置推荐药物数据

        //治疗数据
        tvCureWay.setText(diseaseDetailVo.getCureWay());
        tvDiseaseCureRate.setText(diseaseDetailVo.getCureRate());
        tvCureCost.setText(diseaseDetailVo.getCost());
        tvCureTime.setText(diseaseDetailVo.getCureTime());

        loadingData(false);
        emptyLayout.setVisibility(View.GONE);
        dismissProgressDialog();
    }

    //设置推荐药物数据
    private void recommendDrugLayout(String recommendDrug) {
        Message message = mHandler.obtainMessage();
        message.what = 2;
        if (TextUtils.isEmpty(recommendDrug)) {
            message.obj = emptyString;
        } else {
            message.obj = recommendDrug;
        }
        mHandler.sendMessage(message);
    }


    //设置并发症数据
    private void complicationLayout(String complication) {
        Message message = mHandler.obtainMessage();
        message.what = 1;
        if (TextUtils.isEmpty(complication)) {
            message.obj = emptyString;
        } else {
            message.obj = complication;
        }
        mHandler.sendMessage(message);
    }


    @OnClick(R.id.NetworkLayout)      //重试:类型、分类数据为空
    public void NetworkLayout(View view) {
        loadingData(true);
        if (getIntent() != null) {
            if (!TextUtils.isEmpty(getIntent().getStringExtra("ID")))
                mPresenter.getDiseaseDetailById(getIntent().getStringExtra("ID"));
            else if (!TextUtils.isEmpty(getIntent().getStringExtra("NAME")))
                mPresenter.getDiseaseDetailByName(getIntent().getStringExtra("NAME"));
        } else {
            ToastUtil.show(getContext(), getString(R.string.data_lose));
            finish();
        }
    }

    @SuppressLint("InflateParams")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void generateView(String string, String diOrSy) {
        int columnPosition = 0;
        LinearLayout linearLayout;
        if (diOrSy.equals("recommend")) {
            linearLayout = recommendDrugLayout;
        } else {
            linearLayout = complicationLayout;
        }

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);

        String[] strings = string.split(",");
        StringBuilder item = new StringBuilder();

        List<TextView> childTvs = new ArrayList<>();
        for (String item1 : strings) {
            item.setLength(0);
            item.append("【" + item1 + "】");
            int length = item.toString().length();

            LinearLayout.LayoutParams itemParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);


            if (length < 8) {  // 根据字数来判断按钮的空间长度, 少于4个当一个按钮
                itemParams.weight = 1;
                columnPosition++;
            } else if (length < 14) { // <8个两个按钮空间
                itemParams.weight = 2;
                columnPosition += 2;
            } else {
                itemParams.weight = 3;
                columnPosition += 3;
            }

          TextView childTv = (TextView) LayoutInflater.from(this).inflate(R.layout.find_self_diagnosis_detail_item_button, null);
            childTv.setText(item.toString());
            childTv.setOnClickListener(v -> {

                if (emptyString.equals(item1)) {
                    ToastUtil.show(getContext(), emptyString);
                } else if (diOrSy.equals("disease")) {
                    Intent intent = new Intent(DiseaseDetailActivity.this, DiseaseDetailActivity.class);
                    intent.putExtra("NAME", item1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
                } else {
                    Intent intent = new Intent(DiseaseDetailActivity.this, MedicineDetailActivity.class);
                    intent.putExtra("NAME", item1);
                    startActivity(intent);
                    overridePendingTransition(R.anim.zoomin, R.anim.zoomout);

                }
            });
            childTv.setLayoutParams(itemParams);
            childTv.setGravity(Gravity.CENTER);
            childTv.setTag(item.toString());
            if (columnPosition > 3) {
                LinearLayout horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for (TextView addTv : childTvs) {
                    horizLL.addView(addTv);
                }
                linearLayout.addView(horizLL);
                childTvs.clear();
                columnPosition = 0;
                childTvs.add(childTv);
            } else if (columnPosition == 3) {
                childTvs.add(childTv);
                LinearLayout horizLL = new LinearLayout(this);
                horizLL.setOrientation(LinearLayout.HORIZONTAL);
                horizLL.setLayoutParams(layoutParams);

                for (TextView addTv : childTvs) {
                    horizLL.addView(addTv);
                }
                linearLayout.addView(horizLL);
                childTvs.clear();
                columnPosition = 0;
            } else {
                childTvs.add(childTv);
            }

        }
        if (!childTvs.isEmpty()) {
            LinearLayout horizLL = new LinearLayout(this);
            horizLL.setOrientation(LinearLayout.HORIZONTAL);
            horizLL.setLayoutParams(layoutParams);

            for (TextView addTv : childTvs) {
                horizLL.addView(addTv);
            }
            linearLayout.addView(horizLL);
            childTvs.clear();
        }
    }


    @OnClick(R.id.tv_disease_detailed)
    public void tvDiseaseDetailed(View view) {
        DialogUtil.AlertDialog(getContext(), tvIntro.getText().toString(), getResources().getString(R.string.find_self_diagnosis_detail_intro));
    }

    @OnClick(R.id.tv_disease_case_detailed)
    public void tvCaseDetailed(View view) {
        DialogUtil.AlertDialog(getContext(), tvDiseaseCase.getText().toString(), getResources().getString(R.string.find_self_diagnosis_detail_disease_case));
    }

    @OnClick(R.id.tv_disease_prevention_detailed)
    public void tvPreventionDetailed(View view) {
        DialogUtil.AlertDialog(getContext(), tvDiseasePrevention.getText().toString(), getResources().getString(R.string.find_self_diagnosis_detail_disease_prevention));
    }

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    generateView((String) msg.obj, "disease");
                    break;
                case 2:
                    generateView((String) msg.obj, "recommend");
                    break;
            }
        }
    };

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

}
