package com.zxn.mvpdemo;

import com.zxn.presenter.presenter.IView;

import java.util.List;

/**
 * Created by zxn on 2019/6/30.
 */
public class OrderBillPresenter extends MainPresenter<OrderBillPresenter.IOrderBillView> {


    public interface IOrderBillView extends IView {
        void onBillAll();
    }

//    @Override
//    public List<Object> getData() {
//        return super.getData();
//    }
}
