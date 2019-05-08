package com.zxn.presenter.view;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.LayoutRes;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.zxn.presenter.presenter.BasePresenter;
import com.zxn.presenter.presenter.CreatePresenter;
import com.zxn.presenter.presenter.IView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * 继承此类后需要配置主题,主题继承BaseAppTheme
 * Created by zxn on 2019/3/28.
 */
public abstract class BaseActivity<P extends BasePresenter> extends AppCompatActivity implements IView {

    private static final String TAG = "BaseActivity";
    public P mPresenter;
    protected Context mContext;
    private Unbinder mUnbinder;

    /***是否显示标题栏*/

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(isPortraitOrientation() ? ActivityInfo.SCREEN_ORIENTATION_PORTRAIT : ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
            mUnbinder = ButterKnife.bind(this);
        }
        mContext = this;
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
        if (usedEventBus())
            regEventBus();
    }

    /**
     * 屏幕方向
     *
     * @return :true:竖屏,false:横向
     */
    public boolean isPortraitOrientation() {
        return true;
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
        if (mUnbinder != null) {
            mUnbinder.unbind();
        }
        unRegEventBus();
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

    /**
     * 是否使用EventBus，如果需要使用子类重载此方法并返回true
     *
     * @return 是否启用
     */
    protected boolean usedEventBus() {
        return false;
    }

    protected void regEventBus() {
        if (!EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().register(this);
    }

    protected void unRegEventBus() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    protected abstract @LayoutRes
    int getLayoutResId();
}
