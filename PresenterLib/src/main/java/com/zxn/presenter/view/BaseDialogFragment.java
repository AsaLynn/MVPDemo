package com.zxn.presenter.view;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.zxn.presenter.presenter.BasePresenter;
import com.zxn.presenter.presenter.CreatePresenter;
import com.zxn.presenter.presenter.IView;
import com.zxn.presenter.R;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zxn on 2019/1/23.
 */
public abstract class BaseDialogFragment<P extends BasePresenter> extends DialogFragment implements IView {
    protected P mPresenter;
    private View mRootView;
    private Unbinder mUnbinder;
    protected String TAG = this.getClass().getSimpleName();

    @Override
    public void showLoading() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.Dialog_FullScreen);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null) {
            mRootView = getLayoutResId() <= 0
                    ? super.onCreateView(inflater, container, savedInstanceState)
                    : inflater.inflate(getLayoutResId(), container, false);
            mUnbinder = ButterKnife.bind(this, mRootView);
        } else {
            ViewGroup parent = (ViewGroup) mRootView.getParent();
            if (parent != null) {
                parent.removeView(mRootView);
            }
        }
        return mRootView;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Dialog dialog = super.onCreateDialog(savedInstanceState);
        Window window = dialog.getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        onDialogCreated(params, dialog);
        window.setAttributes(params);
        return dialog;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (null != mUnbinder) {
            mUnbinder.unbind();
        }
    }

    protected P createPresenter() {
        CreatePresenter annotation = getClass().getAnnotation(CreatePresenter.class);
        Class<P> pClass = null;
        if (annotation != null) {
            pClass = (Class<P>) annotation.value();
        }
        try {
            return pClass.newInstance();
        } catch (Exception e) {
            //Logger.e("Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
            Log.i(TAG, "Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
            return null;
        }
    }

    protected abstract int getLayoutResId();

    protected void onDialogCreated(WindowManager.LayoutParams params, Dialog dialog) {
        dialog.setCancelable(true);
        params.width = WindowManager.LayoutParams.MATCH_PARENT;
        params.gravity = Gravity.BOTTOM;
        params.height = WindowManager.LayoutParams.WRAP_CONTENT;
    }

    public void show(FragmentManager manager) {
        super.show(manager, TAG);
    }

    public void showNow(FragmentManager manager) {
        super.showNow(manager, TAG);
    }

    public int show(FragmentTransaction transaction) {
        return super.show(transaction, TAG);
    }
}