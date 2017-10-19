package com.zocki.fragment;

import android.view.View;

import com.weightlibrary.title.CustomTitleBar;
import com.weightlibrary.title.CustomTitleView;
import com.zocki.R;
import com.zocki.baselibrary.fragment.SimpleTempFragment;
import com.zocki.baselibrary.logger.LogUtils;

public class Button1Fragment extends SimpleTempFragment implements View.OnClickListener {

    @Override
    protected int getContentResId() {
        return R.layout.fragment_1;
    }

    @Override
    public void initView() {

    }

    @Override
    public void initData() {

    }

    @Override
    protected View getTitleView() {
        CustomTitleView customTitleView = CustomTitleBar.Builder(getContext(), R.layout.default_view_title_layout).create();
        return customTitleView.getLayout();
    }

    @Override
    public void onResume() {
        super.onResume();

        LogUtils.e( isFragmentVisible() );
    }

    @Override
    public void onClick(View v) {

    }

    /*
    @Override
    protected View getLoadingView() {
        statusView = DefaultStatusBar.Builder(getContext())
                .setEmptyOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        LogUtils.e( "111111111111111111111" );
                    }
                }).create();
        statusView.showEmpty();
        return statusView.getLayout();
    }
    */
}
