package com.zocki;

import com.zocki.baselibrary.activity.BaseActivity;

/**
 * Created by kaisheng3 on 2017/8/28.
 */

public class TestActivity extends BaseActivity {
    @Override
    protected int getContentResId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        TestActivityHelper h = new TestActivityHelper(this);
    }
}
