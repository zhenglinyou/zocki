package com.zocki;

import android.graphics.Color;

import com.qmuiteam.qmui.util.QMUIStatusBarHelper;
import com.zocki.baselibrary.activity.BaseActivity;
import com.zocki.baselibrary.logger.LogUtils;

public class TestActivity extends BaseActivity {

    @Override
    protected int getContentResId() {
        return R.layout.test_activity;
    }

    @Override
    protected void initView() {

        QMUIStatusBarHelper.translucent(this, Color.parseColor("#FFFFFF"));

        QMUIStatusBarHelper.setStatusBarLightMode(this);

        LogUtils.e("123123");
    }

    @Override
    protected void initData() {
        TestActivityHelper h = new TestActivityHelper(this);
    }
}
