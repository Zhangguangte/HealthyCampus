package com.example.HealthyCampus.framework;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;


/**
 * OK
 * @param <T>
 */
public abstract class BasePresenter<T> {

    protected Reference<T> mViewRef; //View接口类型的弱引用
    protected RxManager mRxManager;

    public abstract void onStart();

    public T getView() {
        return mViewRef.get();
    }

    public void attatchView(T view) {
        mViewRef = new WeakReference<>(view);//建立关联
        mRxManager = new RxManager();
        this.onStart();
    }

    public RxManager getRxManager() {
        return mRxManager;
    }

    public boolean isViewAttachted() {
        return mViewRef != null && mViewRef.get() != null;
    }

    public void detachView() {
        if (mViewRef != null) {
            mViewRef.clear();
            mViewRef = null;
        }
        if (mRxManager != null) {
            mRxManager.clear();
        }
    }

}
