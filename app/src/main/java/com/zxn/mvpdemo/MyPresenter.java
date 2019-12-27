package com.zxn.mvpdemo;

import com.zxn.presenter.presenter.IView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zxn on 2019/6/30.
 */
public class MyPresenter extends MainPresenter<MyPresenter.IMyPresenterView>{

//    public void init() {
//        mData = new ArrayList<>();
//        mData.add("123");
//        mData.add(123);
//    }

    public interface IMyPresenterView extends IView {
        void on();
    }


}
