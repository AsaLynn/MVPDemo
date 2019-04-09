package com.zxn.presenter.presenter;

/**
 * Created by zxn on 2019/3/28.
 */
public interface IView {

    /**
     * 弹出加载框
     */
    void showLoading();

    void showToast(int msg);

    void showToast(String msg);

    void showLoading(boolean cancelable);

    void showLoading(String msg, boolean cancelable);

    void showLoading(String msg);

    void showLoading(int msgResId);

    /**
     * 关闭加载框
     */
    void closeLoading();


}
