package com.example.HealthyCampus.module.Find;

import android.support.v4.app.Fragment;
import android.view.View;

import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;

public class FindFragment extends BaseFragment<FindContract.View, FindContract.Presenter> implements FindContract.View, ITabFragment {
    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return 0;
    }

    @Override
    public void setUpView(View view) {

    }

    @Override
    protected FindContract.Presenter setPresenter() {
        return null;
    }
}
