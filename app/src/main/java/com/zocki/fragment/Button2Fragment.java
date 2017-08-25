package com.zocki.fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import com.weightlibrary.loading.DefaultStatusView;
import com.weightlibrary.loading.StatusView;
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
    protected View getLoadingView() {
        StatusView statusView = DefaultStatusView.Builder(getContext())
                .setErrorOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e("2222222222222222");
                    }
                })
                .create();
        statusView.showError();
        return statusView.getLayout();
    }
}
