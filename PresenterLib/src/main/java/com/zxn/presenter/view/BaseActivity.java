package com.zxn.presenter.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.gyf.immersionbar.ImmersionBar;
import com.zxn.presenter.BaseApp;
import com.zxn.presenter.R;
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

    private static final String TAG = BaseActivity.class.getSimpleName();
    public P mPresenter;
    protected Context mContext;
    private Unbinder mUnbinder;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        onInitBind();

        onInitImmersionBar();

        createPresenter();

        regEventBus();
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

    /**
     * 启动页面
     *
     * @param intent      启动意图
     * @param requestCode 请求码
     */
    @Override
    public void startActivityForResult(Intent intent, int requestCode) {
        super.startActivityForResult(intent, requestCode);
        // 返回桌面的时候不要动画,
        if (intent.getCategories() == null || !intent.getCategories().toString().contains("HOME")) {
            startAnimation(true);
        }
    }

    /**
     * 关闭页面
     */
    @Override
    public void finish() {
        super.finish();
        startAnimation(false);
    }

    @Override
    public void showToast(int msg) {
        if (getApplication() instanceof BaseApp) {
            BaseApp baseApp = (BaseApp) getApplication();
            baseApp.showToast(msg);
        }
    }

    @Override
    public void showToast(String msg) {
        if (getApplication() instanceof BaseApp) {
            BaseApp baseApp = (BaseApp) getApplication();
            baseApp.showToast(msg);
        }
    }

    /**
     * 初始化绑定view.
     */
    protected void onInitBind() {
        if (getLayoutResId() != 0) {
            setContentView(getLayoutResId());
            mUnbinder = ButterKnife.bind(this);
        }
    }

    /**
     * 通过注解创建Presenter
     */
    private void createPresenter() {
        CreatePresenter annotation = getClass().getAnnotation(CreatePresenter.class);
        Class<P> pClass = null;
        if (annotation != null) {
            pClass = (Class<P>) annotation.value();
        }
        try {
            mPresenter = pClass.newInstance();
        } catch (Exception e) {
            Log.i(TAG, "Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
        }
        if (mPresenter == null) {
            return;
        }
        mPresenter.attachView(this);
    }

    /**
     * 是否使用EventBus，如果需要使用子类重载此方法并返回true
     *
     * @return 是否启用
     */
    protected boolean usedEventBus() {
        return false;
    }

    /**
     * 控制注册使用EventBus
     */
    protected void regEventBus() {
        if (usedEventBus()) {
            if (!EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().register(this);
            }
        }
    }

    protected void unRegEventBus() {
        if (EventBus.getDefault().isRegistered(this))
            EventBus.getDefault().unregister(this);
    }

    protected abstract @LayoutRes
    int getLayoutResId();

    /**
     * 初始化沉浸式
     * Init immersion bar.
     */
    protected void onInitImmersionBar() {
        if (usedStatusBarDarkFont()) {
            setStatusBarDarkFont();
        }
    }

    /**
     * 是否启用白色状态栏,黑色字体.
     *
     * @return false:关闭
     */
    protected boolean usedStatusBarDarkFont() {
        return false;
    }

    /**
     * 白色状态栏,黑色字体,黑色导航栏,解决了白色状态栏无法看见状态栏字体颜色问题
     */
    protected void setStatusBarDarkFont() {
        ImmersionBar.with(this)
                .statusBarDarkFont(true)
                .navigationBarDarkIcon(true)
                .init();
    }

    /**
     * 指定开启动画或者关闭动画
     *
     * @param start true:执行开启动画,false:执行关闭动画.
     */
    protected void startAnimation(boolean start) {
        if (usedAnimation()) {
            if (start) {
                //启动动画
                overridePendingTransition(R.anim.slide_right_in, R.anim.slide_left_out);
            } else {
                //关闭动画
                overridePendingTransition(R.anim.slide_left_in, R.anim.slide_right_out);
            }
        }
    }

    /**
     * 是否启动动画,默认启用动画.
     *
     * @return true:启用,false,不启用.
     */
    protected boolean usedAnimation() {
        return true;
    }
}
