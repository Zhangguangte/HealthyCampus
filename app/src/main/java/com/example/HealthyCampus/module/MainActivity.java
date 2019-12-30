package com.example.HealthyCampus.module;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.utils.DensityUtil;
import com.example.HealthyCampus.common.utils.SnackbarUtil;
import com.example.HealthyCampus.common.widgets.tab.TabLayout;
import com.example.HealthyCampus.framework.BaseActivity;
import com.example.HealthyCampus.framework.ITabFragment;
import com.example.HealthyCampus.module.Find.FindFragment;
import com.example.HealthyCampus.module.HomePage.HomePageFragment;
import com.example.HealthyCampus.module.Loading.LoadingActivity;
import com.example.HealthyCampus.module.Message.MessageFragment;
import com.example.HealthyCampus.module.Mine.MineFragment;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * OK
 */
public class MainActivity extends BaseActivity<MainContract.View, MainContract.Presenter> implements MainContract.View, TabLayout.OnTabClickListener {

    @BindView(R.id.mCoordinatorLayout)
    CoordinatorLayout mCoordinatorLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.appBarLayout)
    AppBarLayout appBarLayout;
    @BindView(R.id.mFragmentContainerLayout)
    FrameLayout mFragmentContainerLayout;
    @BindView(R.id.mBottomTabLayout)
    TabLayout mTabLayout;

    private ITabFragment currentFragment;

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    protected MainContract.Presenter createPresenter() {
        return new MainPresenter();
    }

    @Override
    protected void initView() {

        //添加tab（因为tab里的Fragment的添加方式不是预先加入Layout容器内，无需开启Fragment的懒加载）
        ArrayList<TabLayout.Tab> tabs = new ArrayList<>();
        tabs.add(new TabLayout.Tab(R.drawable.ic_home_white, R.string.tab_homepage, HomePageFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_message_white, R.string.tab_message, MessageFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_find_white, R.string.tab_find, FindFragment.class));
        tabs.add(new TabLayout.Tab(R.drawable.ic_mine_white, R.string.tab_mine, MineFragment.class));
        mTabLayout.setUpData(tabs, this);
        mTabLayout.setCurrentTab(0);
        //immersive();
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        int action = intent.getIntExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_BACK_TO_HOME);
        switch (action) {
            case ConstantValues.ACTION_KICK_OUT:
                break;
            case ConstantValues.ACTION_LOGOUT:
                break;
            case ConstantValues.ACTION_RESTART_APP:
                protectApp();
                break;
            case ConstantValues.ACTION_BACK_TO_HOME:
                break;
        }
    }

    @Override
    protected void protectApp() {
        startActivity(new Intent(this, LoadingActivity.class));
        finish();
    }


    @Override
    public void onTabClick(TabLayout.Tab tab) {
        try {
            setTitle(tab.labelResId);
            setElevation(tab.labelResId);
            ITabFragment tmpFragment = (ITabFragment) getSupportFragmentManager().findFragmentByTag(tab.targetFragmentClz.getSimpleName());
            if (currentFragment == null) {      //当前Fragment为空
                if (tmpFragment == null) {
                    tmpFragment = tab.targetFragmentClz.newInstance();
                    getSupportFragmentManager().beginTransaction()
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commitNowAllowingStateLoss();
                    currentFragment = tmpFragment;
                } else {

                    ITabFragment newTmpFragment = tab.targetFragmentClz.newInstance();
                    getSupportFragmentManager().beginTransaction().replace(R.id.mFragmentContainerLayout, newTmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commitNowAllowingStateLoss();
                    currentFragment = newTmpFragment;
                }
            } else {
                if (tmpFragment == null) {
                    tmpFragment = tab.targetFragmentClz.newInstance();

                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment.getFragment())
                            .add(R.id.mFragmentContainerLayout, tmpFragment.getFragment(), tab.targetFragmentClz.getSimpleName())
                            .commitNowAllowingStateLoss();
                } else {
                    getSupportFragmentManager().beginTransaction()
                            .hide(currentFragment.getFragment())
                            .show(tmpFragment.getFragment())
                            .commitNowAllowingStateLoss();
                }
                currentFragment = tmpFragment;
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    private void setElevation(int labelResId) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            switch (labelResId) {
                case R.string.tab_about:
                    appBarLayout.setElevation((float) DensityUtil.getInstance().getDip2Px(5));
                    break;
                default:
                    appBarLayout.setElevation(0);
                    break;
            }
        }
    }

    @Override
    public void onBackPressed() {
        mPresenter.exitApp();
    }   //退出

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void showSnackBar(int resId) {       //显示退出提示框
        SnackbarUtil.shortSnackbar(mCoordinatorLayout, getString(resId), SnackbarUtil.Info);
    }

    @Override
    public void finishView() {
        finish();
    }       //结束


}
