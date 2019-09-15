package com.example.HealthyCampus.module.HomePage;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.view.View;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.BaseFragment;
import com.example.HealthyCampus.framework.ITabFragment;
import com.example.HealthyCampus.module.HomePage.list.HomePageListFragment;

/**
 * OK
 */

public class HomePageFragment extends BaseFragment<HomePageContract.View, HomePageContract.Presenter> implements HomePageContract.View, ITabFragment {


    @Override
    public Fragment getFragment() {
        return this;
    }

    @Override
    public int setContentLayout() {
        return R.layout.fragment_home_page;
    }

    @Override
    public void setUpView(View view) {
        HomePageListFragment fragment = HomePageListFragment.newInstance();
        if (fragment != null) {
            getChildFragmentManager().beginTransaction()
                    .add(R.id.mContainerLayout, fragment, HomePageListFragment.class.getSimpleName())
                    .commitAllowingStateLoss();

        }

    }

    @Override
    protected HomePageContract.Presenter setPresenter() {
        return new HomePagePresenter();
    }



}
