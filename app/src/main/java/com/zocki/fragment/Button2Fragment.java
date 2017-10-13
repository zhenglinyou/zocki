package com.zocki.fragment;

import android.view.View;
import android.widget.Toast;

import com.weightlibrary.loading.DefaultStatusBar;
import com.weightlibrary.loading.StatusView;
import com.weightlibrary.title.DefTitleView;
import com.weightlibrary.title.DefaultTitleBar;
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
        LogUtils.e( "2222222222222222222222" );
    }

    @Override
    public void initData() {
    }

    @Override
    protected View getTitleView() {

        DefTitleView defTitleView = DefaultTitleBar.Builder(getContext()).setLeftOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "123123", Toast.LENGTH_SHORT).show();
            }
        }).create();

        return defTitleView.getLayout();
    }

    @Override
    protected View getLoadingView() {
        StatusView statusView = DefaultStatusBar
                .Builder(getContext())
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
