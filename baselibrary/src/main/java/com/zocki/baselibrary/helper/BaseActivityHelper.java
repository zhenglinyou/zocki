package com.zocki.baselibrary.helper;

import android.view.View;

import com.zocki.baselibrary.activity.BaseActivity;

/**
 * Created by kaisheng3 on 2017/8/28.
 */

public abstract class BaseActivityHelper<T extends BaseActivity> extends BaseHelper {

    public T X;

    public BaseActivityHelper( T t ) {
        this.X = t;
    }

    public <K extends View> K getView(int id ) {
        return X.getView(id);
    }
}
