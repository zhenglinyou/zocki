package com.zocki.baselibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DemoFragment2 extends BaseFragment2 {

    // 应该再来一层

    private static final String TAG = DemoFragment2.class.getSimpleName();

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate( 0, container, false);
        //initView(view);
        return view;
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {
        if (isVisible) {
            //更新界面数据，如果数据还在下载中，就显示加载框
        } else {
            //关闭加载框
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        //去服务器下载数据
    }
}
