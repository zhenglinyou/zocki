package com.zocki.fragment;

import com.zocki.R;
import com.zocki.baselibrary.fragment.BaseFragment;
import com.zocki.baselibrary.logger.LogUtils;

/**
 * Created by kaisheng3 on 2017/8/18.
 */
public class Button3Fragment extends BaseFragment {

    @Override
    protected int getContentResId() {
        return R.layout.fragment;
    }

    @Override
    public void initView() {
        LogUtils.e(getClass().getName());
    }

    @Override
    public void initData() {

    }
}
