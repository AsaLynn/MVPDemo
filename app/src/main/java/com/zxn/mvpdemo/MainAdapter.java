package com.zxn.mvpdemo;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;

/**
 * Created by zxn on 2019/4/2.
 */
class MainAdapter extends BaseQuickAdapter<String, BaseViewHolder> {

    public MainAdapter() {
        super(R.layout.item_main);
    }

    @Override
    protected void convert(BaseViewHolder helper, String item) {

    }
}
