package com.zocki.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.zocki.R;
import com.zocki.baselibrary.fragment.BaseFragment;
import com.zocki.baselibrary.logger.LogUtils;

/**
 * Created by kaisheng3 on 2017/8/18.
 */
public class Button2Fragment extends BaseFragment {

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

    @Override
    protected View getTitleView(ViewGroup parent) {
        return LayoutInflater.from(getContext()).inflate(R.layout.common_title,parent);
    }

    @Override
    protected int getLoadingResId() {
        return R.layout.loading_layout;
    }
}
