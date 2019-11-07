package com.example.HealthyCampus.module.HomePage;

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
    //    immersive();
        return R.layout.fragment_base_page;
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


    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
//        if(hidden)
//        {
//            StatusBarUtil.setTranslucentStatusShow(mActivity);
//        }
//        Log.i("MessageListFragm" + "123456", "onHiddenChanged:2");
    }
}
