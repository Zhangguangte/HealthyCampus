package com.example.HealthyCampus.module.Mine;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;

public class MineFragment extends BaseFragment<MineContract.View, MineContract.Presenter> implements MineContract.View, ITabFragment {
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
}
