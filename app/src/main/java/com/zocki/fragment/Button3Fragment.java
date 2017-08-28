package com.zocki.fragment;

import android.view.View;

import com.weightlibrary.loading.DefaultStatusBar;
import com.weightlibrary.loading.StatusView;
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

    @Override
    protected View getLoadingView() {
        StatusView statusView = DefaultStatusBar.Builder(getContext()).create();
        statusView.setLoadingMsg("2222222222222");
        return statusView.getLayout();
    }
}
