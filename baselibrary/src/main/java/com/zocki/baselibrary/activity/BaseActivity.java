package com.zocki.baselibrary.activity;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;

import com.zocki.baselibrary.ioc.ViewUtils;

/**
 * Created by kaisheng3 on 2017/7/20.
 */

public abstract class BaseActivity extends AppCompatActivity {

    public static void startActivity(Context context, Class clazz, Bundle... bundle ) {
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // 设置布局layout
        setContentView();

        // 一些特定的算法，子类基本都会使用的
        ViewUtils.inject(this);

        // 初始化头部
        initTitle();

        // 初始化界面
        initView();

        // 初始化数据
        initData();
    }

    // 设置布局
    protected abstract void setContentView();

    // 初始化头部
    protected abstract void initTitle();

    // 初始化界面
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

    public final <T extends View> T viewById( int resId ) {
        return (T) findViewById(resId);
    }
}
