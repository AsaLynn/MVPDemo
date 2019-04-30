package com.zxn.presenter.model;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by zxn on 2019/4/30.
 */
public interface IApiFactory {

    int MODE_ON_LINE = 0;//线上
    int MODE_ON_TEST = 1;//测试
    int MODE_ON_GRAY = 2;//灰度

    @IntDef({MODE_ON_LINE, MODE_ON_TEST, MODE_ON_GRAY})
    @Retention(RetentionPolicy.SOURCE)
    @interface ModeDef {
    }

    /**
     * 切换开发模式,
     * @param mode
     */
    void setMode(@ModeDef int mode);

}
