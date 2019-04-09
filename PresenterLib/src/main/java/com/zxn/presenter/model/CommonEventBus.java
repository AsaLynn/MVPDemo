package com.zxn.presenter.model;

import org.greenrobot.eventbus.EventBus;

/**
 * 通用的CommonEventBus
 * Created by zxn on 2019/4/9.
 */
public class CommonEventBus {

    /**
     * EventBus发送消息.
     *
     * @param event 通用的CommonEvent
     */
    public static void post(CommonEvent event) {
        EventBus.getDefault().post(event);
    }


}
