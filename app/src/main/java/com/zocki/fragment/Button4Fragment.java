package com.zocki.fragment;

import com.zocki.R;
import com.zocki.baselibrary.fragment.BaseFragment;
import com.zocki.baselibrary.logger.LogUtils;

public class Button4Fragment extends BaseFragment {

    @Override
    protected int getContentResId() {
        return R.layout.fragment;
    }

    @Override
    public void initView() {
        LogUtils.e( "4444444444444444444444444444" );
    }

    @Override
    public void initData() {
        LogUtils.e( "kkkkkkkkkkkkkkkkk" );
    }
}
