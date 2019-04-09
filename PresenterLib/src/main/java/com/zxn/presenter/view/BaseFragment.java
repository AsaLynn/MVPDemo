package com.zxn.presenter.view;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zxn.presenter.presenter.BasePresenter;
import com.zxn.presenter.presenter.CreatePresenter;
import com.zxn.presenter.presenter.IView;

import org.greenrobot.eventbus.EventBus;

import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by zxn on 2019/3/28.
 */
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IView {

    private String TAG = "BaseFragment";

    protected P mPresenter;
    protected View mRootView;
    protected boolean isVisible;
    private boolean isPrepared;//标志位，标志已经初始化完成
    private boolean isFirst = true;

    private Unbinder mUnbinder;
    protected String mPageTitle = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (createPresenter() != null) {
            mPresenter = createPresenter();
            mPresenter.attachView(this);
        }
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        isPrepared = true;
        if (usedEventBus())
            regEventBus();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getUserVisibleHint()) {
            setUserVisibleHint(true);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (mUnbinder != null)
            mUnbinder.unbind();
        unRegEventBus();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mPresenter != null) {
            mPresenter.detachView();
        }
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            isVisible = true;
            lazyLoad();
        } else {
            isVisible = false;
            onInvisible();
        }
    }

    /**
     * 懒加载
     */
    protected void lazyLoad() {
        if (!isPrepared || !isVisible || !isFirst) {
            return;
        }
        initData();
        isFirst = false;
    }

    /**
     * fragment被设置为不可见时调用
     */
    protected abstract void onInvisible();

    protected abstract int getLayoutResId();

    /**
     * 这里获取数据，刷新界面，此方法执行在initView后面
     */
    protected abstract void initData();

    protected P createPresenter() {
        CreatePresenter annotation = getClass().getAnnotation(CreatePresenter.class);
        Class<P> pClass = null;
        if (annotation != null) {
            pClass = (Class<P>) annotation.value();
        }
        try {
            return pClass.newInstance();
        } catch (Exception e) {
//            throw new RuntimeException("Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
            Log.i(TAG, "Presenter创建失败!，检查是否声明了@CreatePresenter(xx.class)注解");
            return null;
        }
    }

    @Override
    public void showLoading() {

    }


    public CharSequence getPageTitle() {
        return mPageTitle;
    }

    /**
     * 是否使用EventBus，如果需要使用子类重载此方法并返回true
     *
     * @return
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


}
