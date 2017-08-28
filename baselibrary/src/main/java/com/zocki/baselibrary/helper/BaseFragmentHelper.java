package com.zocki.baselibrary.helper;

import android.view.View;

import com.zocki.baselibrary.fragment.BaseFragment;

/**
 * Created by kaisheng3 on 2017/8/28.
 */

public abstract class BaseFragmentHelper <T extends BaseFragment> extends BaseHelper {

    public T X;

    public BaseFragmentHelper( T t ) {
        this.X = t;
    }

    public <K extends View> K getView(int id ) {
        return X.getView(id);
    }
}

