package com.example.HealthyCampus.framework;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.avos.avoscloud.AVAnalytics;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.constants.ConstantValues;
import com.example.HealthyCampus.common.immersionbar.ImmersionBar;
import com.example.HealthyCampus.common.utils.AppStatusTracker;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;
import com.example.HealthyCampus.common.utils.ToastUtil;
import com.example.HealthyCampus.module.MainActivity;
import com.umeng.socialize.UMShareAPI;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * OK
 *
 * @param <V>
 * @param <T>
 */
public abstract class BaseActivity<V, T extends BasePresenter<V>> extends AppCompatActivity {

    protected T mPresenter;
    private Unbinder unbinder;
    //加载中
    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //初始化沉浸式
        initImmersionBar();
        switch (AppStatusTracker.getInstance().getAppStatus()) {
            case ConstantValues.STATUS_FORCE_KILLED:
                protectApp();
                break;
            case ConstantValues.STATUS_KICK_OUT:
                kickOut();
                break;
            case ConstantValues.STATUS_LOGOUT:
            case ConstantValues.STATUS_OFFLINE:
            case ConstantValues.STATUS_ONLINE:
                setUpContentView();
                unbinder = ButterKnife.bind(this);//添加View注解
                ButterKnife.bind(this);
                initPresenter();
                initView();
                initData(savedInstanceState);
                break;
        }
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        StatusBarUtil.setTranslucentStatus(this);   //设置状态栏透明
        StatusBarUtil.setStatusBarDarkTheme(this, false);     //白色字体
    }

    private void initPresenter() {
        mPresenter = createPresenter();
        if (mPresenter != null) {
            mPresenter.attatchView((V) this);
        }
    }

    protected void protectApp() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_RESTART_APP);
        startActivity(intent);
    }

    protected void kickOut() {
        // TODO: show dialog to confirm
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra(ConstantValues.KEY_HOME_ACTION, ConstantValues.ACTION_KICK_OUT);
        startActivity(intent);
    }

    protected abstract void setUpContentView();

    protected abstract T createPresenter();

    protected abstract void initView();

    protected abstract void initData(Bundle savedInstanceState);


    @Override
    protected void onRestart() {
        super.onRestart();
        LogUtil.logE("BaseActivity123456", toString() + ":onRestart");
    }

    @Override
    protected void onStart() {
        super.onStart();
        LogUtil.logE("BaseActivity123456", toString() + ":onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        AVAnalytics.onResume(this);
        LogUtil.logE("BaseActivity123456", toString() + ":onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        AVAnalytics.onPause(this);
        LogUtil.logE("BaseActivity123456", toString() + ":onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        LogUtil.logE("BaseActivity123456", toString() + ":onStop");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //UmengShare 回调相关配置
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);
        LogUtil.logE("BaseActivity123456", toString() + ":onActivityResult");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        unbinder.unbind();
        LogUtil.logE("BaseActivity123456", toString() + ":onDestroy");
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        LogUtil.logE("BaseActivity123456", toString() + ":finish");
    }


    /*自定义消息的加载进度条*/
    public void showProgressDialog(String msg) {
        if (progressDialog != null && progressDialog.isShowing()) {
            progressDialog.dismiss();
            progressDialog = null;
        }
        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage(msg);
        progressDialog.setCancelable(true);
        progressDialog.setCanceledOnTouchOutside(false);
        try {
            progressDialog.show();
        } catch (WindowManager.BadTokenException exception) {
            exception.printStackTrace();
        }
    }

    /**
     * 隐藏正在加载的进度条
     */
    public void dismissProgressDialog() {
        if (null != progressDialog && progressDialog.isShowing()) {
            progressDialog.dismiss();
        }
    }


}
