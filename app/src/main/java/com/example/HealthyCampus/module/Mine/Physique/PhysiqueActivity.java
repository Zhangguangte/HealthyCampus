package com.example.HealthyCampus.module.Mine.Physique;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.adapter.PhysiqueAdapter;
import com.example.HealthyCampus.common.utils.GlideUtils;
import com.example.HealthyCampus.common.utils.PhysiqueUtils;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.widgets.TurnCardListView;
import com.example.HealthyCampus.framework.BaseActivity;

import java.util.Arrays;

import butterknife.BindView;
import butterknife.OnClick;

import static com.example.HealthyCampus.common.utils.PhysiqueUtils.colors;


public class PhysiqueActivity extends BaseActivity<PhysiqueContract.View, PhysiqueContract.Presenter> implements PhysiqueContract.View, PhysiqueAdapter.onItemClick {


    @BindView(R.id.cardList)
    TurnCardListView cardList;
    @BindView(R.id.ivIcon)
    ImageView ivIcon;
    @BindView(R.id.tvName)
    TextView tvName;
    @BindView(R.id.titleLayout)
    LinearLayout titleLayout;
    @BindView(R.id.tvExpression)
    TextView tvExpression;
    @BindView(R.id.tvFeature)
    TextView tvFeature;
    @BindView(R.id.tvInner)
    TextView tvInner;
    @BindView(R.id.tvPatient)
    TextView tvPatient;
    @BindView(R.id.cvResult)
    CardView cvResult;
    @BindView(R.id.bottom_content)
    RelativeLayout bottomContent;

    private float[] counter = {0, 0, 0, 0, 0, 0, 0, 0, -1};
    private int[] codes = new int[PhysiqueUtils.physiques.length];

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.mine_physique);
    }

    @Override
    protected PhysiqueContract.Presenter createPresenter() {
        return new PhysiquePresenter();
    }


    @Override
    protected void initView() {
        initQuestViedw();
    }

    @Override
    protected void initImmersionBar() {
        StatusBarUtil.setFullsceen(this);
    }

    @Override
    protected void initData(Bundle savedInstanceState) {
    }


    @Override
    public Context getContext() {
        return this;
    }

    private void initQuestViedw() {
        PhysiqueAdapter physiqueAdapter = new PhysiqueAdapter(this);
        cardList.setAdapter(physiqueAdapter);

        //跳转到下一个的监听
        cardList.setOnTurnListener(position -> {
            bottomContent.setBackgroundColor(colors[position] - 60);    //设置下一个的背景色
            cardList.setWork(false);
        });
    }

    @Override
    public void btnClick(int position, int type) {
        cardList.setEnabled(false);
        cardList.setClickable(false);
        switch (type) {
            case 1:
                if (position != PhysiqueUtils.questionList.length - 1) {
                    if (position != 0)
                        codes[position - 1] = 1;
                    cardList.turnTo(position + 1);
                } else {
                    getResult();
                    cvResult.setVisibility(View.VISIBLE);
                    cardList.setVisibility(View.INVISIBLE);
                }
                break;
            case 2:
            case 3:
                codes[position - 1] = type;
                cardList.turnTo(position + 1);
                break;
        }

    }


    private void getResult() {
        Log.e("PhysiqueActivity" + "123456", "codes:" + Arrays.toString(codes));
        float margin = 0.6f;
        switch (codes[0]) {
            case 1:
                counter[0]++;
                counter[1]++;
                counter[3] += margin;
                break;
            case 2:
                counter[2]++;
                counter[4]++;
                counter[5]++;
                counter[5] += 0.3;
                counter[3] += margin;
                break;
            case 3:
                counter[7]++;
                counter[6] += margin;
                counter[3] += margin;
                break;
            default:
                Log.e("PhysiqueActivity" + "123456", codes[0] + "     没执行");
        }
        switch (codes[1]) {
            case 1:
                counter[0]++;
                counter[2] += margin;
                counter[3] += margin;
                break;
            case 2:
                counter[4]++;
                counter[2] += margin;
                counter[3] += margin;
                break;
            case 3:
                counter[1]++;
                counter[5]++;
                counter[7]++;
                counter[6]++;
                counter[2] += margin;
                counter[3] += margin;
                break;
        }
        switch (codes[2]) {
            case 1:
                counter[1]++;
                counter[4]++;
                counter[0] += margin;
                counter[2] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 2:
                counter[3]++;

                counter[0] += margin;
                counter[2] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 3:
                counter[7]++;
                counter[0] += margin;
                counter[2] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
        }
        switch (codes[3]) {
            case 1:
                counter[0]++;
                counter[2]++;
                counter[5]++;
                counter[6]++;
                counter[3] += margin;
                counter[4] += margin;
                break;
            case 2:
                counter[7]++;
                counter[1]++;
                counter[3] += margin;
                counter[4] += margin;
                break;
            case 3:
                break;
        }
        switch (codes[4]) {
            case 1:
                counter[3]++;
                counter[1] += margin;
                counter[2] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 2:
                counter[5]++;
                counter[1] += margin;
                counter[2] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 3:
                counter[7]++;
                counter[1] += margin;
                counter[2] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
        }
        switch (codes[5]) {
            case 1:
                counter[2]++;
                counter[2]++;
                counter[0] += margin;
                counter[3] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 2:
                counter[1]++;
                counter[1]++;
                counter[1]++;
                counter[0] += margin;
                counter[3] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 3:
                counter[7]++;
                counter[0] += margin;
                counter[3] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
        }
        switch (codes[6]) {
            case 1:
                counter[2]++;
                counter[0] += margin;
                counter[3] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 2:
                counter[1]++;
                counter[0] += margin;
                counter[3] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
            case 3:
                counter[7]++;
                counter[0] += margin;
                counter[4] += margin;
                counter[5] += margin;
                counter[6] += margin;
                break;
        }

        int maxIndex = -1;
        for (int i = 0; i < counter.length; i++) {
            if (counter[i] > counter[8]) {
                maxIndex = i;
                counter[8] = counter[i];
            }
        }

        tvFeature.setText(PhysiqueUtils.physiquesCharacteristics[maxIndex]);
        tvExpression.setText(PhysiqueUtils.physiquesExpressions[maxIndex]);
        tvInner.setText(PhysiqueUtils.physiquesMentalitys[maxIndex]);
        tvPatient.setText(PhysiqueUtils.physiquesMatters[maxIndex]);
        tvName.setText(PhysiqueUtils.physiques[maxIndex]);
        GlideUtils.display(ivIcon, PhysiqueUtils.physiquesImageUrls[maxIndex]);
    }

    @OnClick(R.id.btnBack)
    public void btnBack(View view) {
        finish();
    }
}
