package com.zxn.presenter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * 废弃,此类不可用.
 * Created by zxn on 2019/3/28.
 */
@Deprecated
public class FragHolder {

    private static final int ID_VIEW_ROOT = 701;
    protected Context mContext;
    protected FrameLayout mView;
    private String TAG = "FragHolder";
    private Fragment mParentFragment;

    @Deprecated
    public FragHolder(BaseActivity activity) {
        mContext = activity;
        onInitView();
    }

    @Deprecated
    public FragHolder(AppCompatActivity activity) {
        mContext = activity;
        onInitView();
    }

    public FragHolder(FragmentActivity activity) {
        mContext = activity;
        onInitView();
    }

    public FragHolder(Fragment fragment) {
        mContext = fragment.getActivity();
        mParentFragment = fragment;
        onInitView();
    }

    private void onInitView() {
        //mView = LayoutInflater.from(mContext).inflate(R.layout.view_holder_common, null);
        mView = new FrameLayout(mContext);
        mView.setId(ID_VIEW_ROOT);
        ViewGroup.LayoutParams layoutParams
                = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        mView.setLayoutParams(layoutParams);

    }


    public View attachFragment(@Deprecated BaseFragment fragment) {
        BaseActivity activity = (BaseActivity) mContext;
        activity.getSupportFragmentManager().beginTransaction()
                .add(mView.getId(), fragment)
                .commitAllowingStateLoss();
        return mView;
    }

    /**
     * 在FragmentActivity中添加Fragment
     * @param fragment  要添加Fragment
     * @return  当前view
     */
    public View attachFragment(Fragment fragment) {
        if (mContext instanceof FragmentActivity) {
            FragmentActivity activity = (FragmentActivity) mContext;
            activity.getSupportFragmentManager().beginTransaction()
                    .add(mView.getId(), fragment)
                    .commitAllowingStateLoss();
        }
        return mView;
    }

    /**
     * 在Fragment中的view添加,子Fragment
     * @param fragment  要添加Fragment
     * @return  当前view
     */
    public View attachChildFragment(Fragment fragment) {
        if (null != mParentFragment) {
            mParentFragment.getChildFragmentManager().beginTransaction()
                    .add(mView.getId(), fragment)
                    .commitAllowingStateLoss();
        }
        return mView;
    }

    public View getView() {
        return mView;
    }

}
