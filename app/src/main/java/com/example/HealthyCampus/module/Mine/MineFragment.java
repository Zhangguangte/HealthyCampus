package com.example.HealthyCampus.module.Mine;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;
import com.example.HealthyCampus.module.Find.Drug_Bank.DrugBankActivity;
import com.example.HealthyCampus.module.Mine.Feedback.FeedbackActivity;
import com.example.HealthyCampus.module.Mine.Physique.PhysiqueActivity;
import com.example.HealthyCampus.module.Mine.Service.ServiceActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class MineFragment extends BaseFragment<MineContract.View, MineContract.Presenter> implements MineContract.View, ITabFragment {

    @BindView(R.id.ServiceLayout)
    LinearLayout ServiceLayout;

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_mine;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected MineContract.Presenter setPresenter() {
        return new MinePresenter();
    }

    @OnClick(R.id.ServiceLayout)
    public void ServiceLayout(View view) {
        startActivity(new Intent(mActivity, ServiceActivity.class));
        mActivity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @OnClick(R.id.physiqueLayout)
    public void physiqueLayout(View view) {
        startActivity(new Intent(mActivity, PhysiqueActivity.class));
        mActivity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @OnClick(R.id.feedbackLayout)
    public void feedbackLayout(View view) {
        startActivity(new Intent(mActivity, FeedbackActivity.class));
        mActivity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }
}
