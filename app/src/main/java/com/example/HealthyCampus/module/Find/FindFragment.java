package com.example.HealthyCampus.module.Find;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;
import com.example.HealthyCampus.module.Find.Diagnosis.DiagnosisActivity;
import com.example.HealthyCampus.module.Find.Drug_Bank.DrugBankActivity;
import com.example.HealthyCampus.module.Find.Nearby.NearbyActivity;
import com.example.HealthyCampus.module.Find.Recipes.RecipesActivity;
import com.example.HealthyCampus.module.Message.New_friend.NewFriendActivity;

import butterknife.BindView;
import butterknife.OnClick;

public class FindFragment extends BaseFragment<FindContract.View, FindContract.Presenter> implements FindContract.View, ITabFragment {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.DrugLayout)
    LinearLayout DrugLayout;
    @BindView(R.id.DiagnosisLayout)
    LinearLayout DiagnosisLayout;
    @BindView(R.id.RecipesLayout)
    LinearLayout RecipesLayout;
    @BindView(R.id.NearbyLayout)
    LinearLayout NearbyLayout;

    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_find;
    }

    @Override
    public void setUpView(View view) {
        setCustomActionBar();
    }

    private void setCustomActionBar() {
        tvTitle.setText(R.string.tab_find);
    }


    @Override
    protected FindContract.Presenter setPresenter() {
        return new FindPresenter();
    }

    @OnClick(R.id.DrugLayout)
    public void DrugLayout(View view) {
        startActivity(new Intent(mActivity, DrugBankActivity.class));
        mActivity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @OnClick(R.id.DiagnosisLayout)
    public void DiagnosisLayout(View view) {
        startActivity(new Intent(mActivity, DiagnosisActivity.class));
        mActivity.overridePendingTransition(R.anim.zoomin, R.anim.zoomout);
    }

    @OnClick(R.id.RecipesLayout)
    public void RecipesLayout(View view) {
        startActivity(new Intent(mActivity, RecipesActivity.class));
        mActivity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }

    //附近药店
    @OnClick(R.id.NearbyLayout)
    public void NearbyLayout(View view) {
        startActivity(new Intent(mActivity, NearbyActivity.class));
        mActivity.overridePendingTransition(R.anim.push_up_in, R.anim.push_up_out);
    }


}
