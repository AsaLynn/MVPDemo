package com.zxn.presenter;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

/**
 * Created by zxn on 2019/3/28.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    private static final String TAG = "BaseActivity";
    public P mPresenter;
    protected Context mContext;

    /***是否显示标题栏*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        mContext = this;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }


    @Override
    public void showLoading() {
        setTheme(android.R.style.Theme);
    }

    private P createPresenter() {
        CreatePresenter annotation = getClass().getAnnotation(CreatePresenter.class);
        Class<P> pClass = null;
        if (annotation != null) {
            pClass = (Class<P>) annotation.value();
        }
        try {
            return mPresenter = pClass.newInstance();
        } catch (Exception e) {
            Log.i(TAG, "Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
            return null;
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }
}
