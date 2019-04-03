package com.zxn.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.ButterKnife;

/**
 * 通用的viewholder
 * Created by zxn on 2019/4/2.
 */
public abstract class ViewHolder<T> {

    private ViewGroup mRoot = null;
    protected Context mContext;
    protected View mView;
    protected T mData;

    public ViewHolder(FragmentActivity activity) {
        mContext = activity;
        initView();
    }

    /**
     * @param fragment 所依附的acitivty必须是FragmentActivity
     */
    public ViewHolder(Fragment fragment) {
        this(fragment.getActivity());
    }

    private void initView() {
        if (getLayoutResId() == 0) {
            return;
        }
        mView = LayoutInflater.from(mContext).inflate(getLayoutResId(), mRoot, false);
        ButterKnife.bind(this, mView);
        onInitView(mView);
    }

    protected abstract void onInitView(View rootView);

    public void setVisibility(boolean visibility) {
        if (null == mView)
            return;
        mView.setVisibility(visibility ? View.VISIBLE : View.GONE);
    }

    protected abstract int getLayoutResId();

    public View getView() {
        return mView;
    }

    public void setData(T data) {
        mData = data;
    }
}
