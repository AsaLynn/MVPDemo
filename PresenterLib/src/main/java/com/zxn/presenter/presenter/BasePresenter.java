package com.zxn.presenter.presenter;

import android.support.v4.util.Preconditions;
import android.util.Log;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

/**
 * Presenter基类，弱引用管理View生命周期
 * Created by zxn on 2019/3/28.
 */
public abstract class BasePresenter<V extends IView> {

    private String TAG = "BasePresenter";
    protected Reference<V> mRootView;

    protected V getView() {
        return mRootView.get();
    }

    public boolean isViewAttached() {
        return mRootView != null && mRootView.get() != null;
    }

    public void attachView(V view) {

        if (view == null) {
            throw new NullPointerException("view cannot be null");
        }
        mRootView = new WeakReference<V>(view);
        Log.i(TAG, "BasePresenter" + mRootView.get());
    }

    public void detachView() {
        if (mRootView != null) {
            mRootView.clear();
            mRootView = null;
        }
    }

    protected void showLoading(String msg) {
        if (isViewAttached()) {
            getView().showLoading(msg);
        }
    }

    protected void showLoading(boolean cancelable) {
        if (isViewAttached()) {
            getView().showLoading(cancelable);
        }
    }

    protected void showLoading(String msg, boolean cancelable) {
        if (isViewAttached()) {
            getView().showLoading(msg, cancelable);
        }
    }

    protected void showLoading(int msgResId) {
        if (isViewAttached()) {
            getView().showLoading(msgResId);
        }
    }

    protected void showLoading() {
        if (isViewAttached()) {
            getView().showLoading();
        }
    }

    protected void closeLoading() {
        if (isViewAttached()) {
            getView().closeLoading();
        }
    }

    protected void showToast(int msg) {
        if (isViewAttached()) {
            getView().showToast(msg);
        }
    }

    protected void showToast(String msg) {
        if (isViewAttached()) {
            getView().showToast(msg);
        }
    }


}
