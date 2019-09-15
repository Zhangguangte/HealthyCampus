package com.example.HealthyCampus.framework;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.KeyEvent;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.utils.ActivityUtils;
import com.example.HealthyCampus.common.utils.LogUtil;

/**
 * OK
 */
public class BaseContainerActivity extends BaseActivity {

    private static String TAG = "BaseContainerActivity";
    private static BaseFragment baseFragment;

    public static void setFragment(BaseFragment baseFragment) {
        BaseContainerActivity.baseFragment = baseFragment;
    }

    @Override
    protected void initData(Bundle savedInstanceState) {

    }

    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_container);
    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    protected void initView() {
        if (baseFragment == null) {
            LogUtil.logE(TAG, "create baseFragment is null !");
            finish();
            return;
        }
        addFragment(baseFragment);
    }

    public void addFragment(Fragment fragment) {
        ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), fragment, R.id.contentFrame);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (baseFragment != null) {
            baseFragment.onKeyDown(keyCode, event);
        }
        return false;
    }

    @Override
    protected void onDestroy() {
        baseFragment = null;
        super.onDestroy();
    }
}
