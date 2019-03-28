package com.zxn.presenter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

/**
 * Created by zxn on 2019/3/28.
 */
public class FragHolder {

    private static final int ID_VIEW_ROOT = 701;
    protected Context mContext;
    protected FrameLayout mView;
    private String TAG = "FragHolder";

    public FragHolder(BaseActivity activity) {
        mContext = activity;
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

    public View attachFragment(BaseFragment fragment) {
        BaseActivity activity = (BaseActivity) mContext;
        activity.getSupportFragmentManager().beginTransaction()
                .add(mView.getId(), fragment)
                .commitAllowingStateLoss();
        return mView;
    }

    public View getView() {
        return mView;
    }

}
