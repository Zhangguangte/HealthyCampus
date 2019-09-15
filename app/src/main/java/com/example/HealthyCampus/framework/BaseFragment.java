package com.example.HealthyCampus.framework;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.avos.avoscloud.AVAnalytics;
import com.example.HealthyCampus.common.utils.LogUtil;

import butterknife.ButterKnife;

/**
 * OK
 * @param <V>
 * @param <T>
 */
public abstract class BaseFragment<V extends BaseView,T extends BasePresenter<V>> extends Fragment{

    protected T mPresenter;

    private boolean isVisibleToUser;
    private boolean isViewInitialized;
    private boolean isDataInitialized;
    private boolean isLazyLoadEnabled;

    protected Activity mActivity;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        LogUtil.logI("BaseFragment123456",toString() + ":onAttach");
        this.mActivity= (Activity) context;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LogUtil.logI("BaseFragment123456",toString() + ":onCreate");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        LogUtil.logI("BaseFragment123456",toString() + ":onCreateView");
        View mRootView= inflater.inflate(setContentLayout(), container, false);
        ButterKnife.bind(this,mRootView);
        return mRootView;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setUpView(view);
        if (!isLazyLoadEnabled){
            initPresenter();
        }else {
            isViewInitialized = true;
            if (savedInstanceState != null){
                onRestoreInstanceState(savedInstanceState);
            }
            if (isDataInitialized){
                initPresenter();
            }else {
                checkIfLoadData();
            }
        }
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        LogUtil.logI("BaseFragment123456",toString() + ":onActivityCreated");

    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
        LogUtil.logI("BaseFragment123456",toString() + ":onViewStateRestored");
    }

    @Override
    public void onStart() {
        super.onStart();
        LogUtil.logI("BaseFragment123456",toString() + ":onStart");
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtil.logI("BaseFragment123456",toString() + ":onResume");
        AVAnalytics.onFragmentStart(toString());
    }

    @Override
    public void onPause() {
        super.onPause();
        LogUtil.logI("BaseFragment123456",toString() + ":onPause");
        AVAnalytics.onFragmentEnd(toString());
    }

    @Override
    public void onStop() {
        super.onStop();
        LogUtil.logI("BaseFragment123456",toString() + ":onStop");
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isViewInitialized = false;
        if(mPresenter!=null){
            mPresenter.detachView();
        }
        LogUtil.logI("BaseFragment123456",toString() + ":onDestroyView");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        LogUtil.logI("BaseFragment123456",toString() + ":onDestroy");
    }

    @Override
    public void onDetach() {
        super.onDetach();
        LogUtil.logI("BaseFragment123456",toString() + ":onDetach");
    }

    /**
     * 执行于OnCreateView方法
     * @return layoutId 返回布局Id
     */
    public abstract int setContentLayout();
    /**
     * 执行于onCreateView之后
     * @param view onCreateView创建的视图
     */
    public abstract void setUpView(View view);
    /**
     * 执行于setUpView之后
     * @return
     */
    protected abstract T setPresenter();

    public T getmPresenter() {
        return mPresenter;
    }

    /**
     * 是否开启懒加载
     */
    public void enableLazyLoad(){
        isLazyLoadEnabled = true;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        this.isVisibleToUser = isVisibleToUser;
        LogUtil.logI("BaseFragment123456",toString() + ":setUserVisibleHint:" + isVisibleToUser);
        checkIfLoadData();
    }


    protected void initPresenter() {
        if(mPresenter==null){
            mPresenter=setPresenter();
            mPresenter.attatchView((V)this);
        }
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

    protected void onBack(){
        getActivity().onBackPressed();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        LogUtil.logI("BaseFragment123456",toString() + ":onSaveInstanceState");
    }


    @Override
    public void onInflate(Context context, AttributeSet attrs, Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        LogUtil.logI("BaseFragment123456",toString() + ":onInflate");
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        LogUtil.logI("BaseFragment123456",toString() + ":onHiddenChanged:" + hidden);
    }

    protected void onKeyDown(int keyCode, KeyEvent event) {

    }
}
