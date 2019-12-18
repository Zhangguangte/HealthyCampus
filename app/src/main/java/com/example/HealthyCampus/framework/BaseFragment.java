package com.example.HealthyCampus.framework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.example.HealthyCampus.R;
import com.example.HealthyCampus.common.immersionbar.ImmersionBar;
import com.example.HealthyCampus.common.utils.LogUtil;
import com.example.HealthyCampus.common.utils.StatusBarUtil;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * OK
 *
 * @param <V>
 * @param <T>
 */
public abstract class BaseFragment<V extends BaseView, T extends BasePresenter<V>> extends Fragment implements View.OnTouchListener {

    //    private ViewGroup mView;
    protected T mPresenter;
    private Unbinder unbinder;

    private boolean isVisibleToUser;
    private boolean isViewInitialized;
    private boolean isDataInitialized;
    private boolean isLazyLoadEnabled;

    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.logE("BaseFragment123456", toString() + ":onAttach");
        this.mActivity = (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化沉浸式
        initImmersionBar();
        LogUtil.logE("BaseFragment123456", toString() + ":onCreate");
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.logE("BaseFragment123456", toString() + ":onCreateView");

        View mRootView = inflater.inflate(setContentLayout(), container, false);
        unbinder = ButterKnife.bind(this, mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        //    addStatusBar();
        setUpView(view);
        if (!isLazyLoadEnabled) {
            initPresenter();
        } else {
            isViewInitialized = true;
            if (savedInstanceState != null) {
                onRestoreInstanceState(savedInstanceState);
            }
            if (isDataInitialized) {
                initPresenter();
            } else {
                checkIfLoadData();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.logE("BaseFragment123456", toString() + ":onActivityCreated");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.logE("BaseFragment123456", toString() + ":onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.logE("BaseFragment123456", toString() + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.logE("BaseFragment123456", toString() + ":onResume");
        AVAnalytics.onFragmentStart(toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.logE("BaseFragment123456", toString() + ":onPause");
        AVAnalytics.onFragmentEnd(toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.logE("BaseFragment123456", toString() + ":onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitialized = false;
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        LogUtil.logE("BaseFragment123456", toString() + ":onDestroyView");
    }

    @Override
    public void onDestroy() {
        if (null != unbinder)
            unbinder.unbind();
        super.onDestroy();
        LogUtil.logE("BaseFragment123456", toString() + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.logE("BaseFragment123456", toString() + ":onDetach");
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }


    /**
     * 执行于OnCreateView方法
     *
     * @return layoutId 返回布局Id
     */
    public abstract int setContentLayout();

    /**
     * 执行于onCreateView之后
     *
     * @param view onCreateView创建的视图
     */
    public abstract void setUpView(View view);

    /**
     * 执行于setUpView之后
     *
     * @return
     */
    protected abstract T setPresenter();

    public T getmPresenter() {
        return mPresenter;
    }

    /**
     * 是否开启懒加载
     */
    public void enableLazyLoad() {
        isLazyLoadEnabled = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        LogUtil.logE("BaseFragment123456", toString() + ":setUserVisibleHint:" + isVisibleToUser);
        checkIfLoadData();
    }


    protected void initPresenter() {
        if (mPresenter == null) {
            mPresenter = setPresenter();
            mPresenter.attatchView((V) this);
        }
        initData();
    }


    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        isDataInitialized = true;
    }

    private void checkIfLoadData() {
        if (isVisibleToUser && isViewInitialized && !isDataInitialized) {
            isDataInitialized = true;
            initPresenter();
        }
    }

    protected void onBack() {
        getActivity().onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.logE("BaseFragment123456", toString() + ":onSaveInstanceState");
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        LogUtil.logE("BaseFragment123456", toString() + ":onInflate");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.logE("BaseFragment123456", toString() + ":onHiddenChanged:" + hidden);
    }

    protected void onKeyDown(int keyCode, KeyEvent event) {

    }

    protected void initData() {
    }

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void initImmersionBar() {
        //设置状态栏透明
        StatusBarUtil.setTranslucentStatus(mActivity);
        //白色字体
        StatusBarUtil.setStatusBarDarkTheme(mActivity, false);
    }



}
