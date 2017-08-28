package com.zocki.baselibrary.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.jaeger.library.StatusBarUtil;
import com.zocki.baselibrary.R;
import com.zocki.baselibrary.ioc.ViewUtils;
import com.zocki.baselibrary.viewheper.FindViewById;

/**
 * Created by kaisheng3 on 2017/7/20.
 * BaseActivity
 */
public abstract class BaseActivity extends AppCompatActivity {

    public static void startActivity(Context context, Class clazz, Bundle... bundle) {
        Intent intent = new Intent(context,clazz);
        if( bundle != null && bundle.length > 0 ) {
            intent.putExtra("bundle", bundle[0]);
        }
        context.startActivity(intent);
    }

    private FindViewById mFindViewById;

    private ViewStub mContentViewStub, mLoadingViewStub;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.base_activity_layout);

        mFindViewById = new FindViewById(this);

        // 取消状态栏
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        initTitle();

        initContentView();

        // StatusBarUtil.setColor(this, Color.parseColor("#FFFFFF"));

        initLoadingView();

        // IOC
        ViewUtils.inject(this);

        // 初始化界面
        initView();

        // 初始化数据
        initData();
    }

    public final Bundle getBundle() {
        return getIntent().getBundleExtra("bundle");
    }

    private void initContentView(){

        mContentViewStub = getView(R.id.view_stub_content);

        int resLayoutId = getContentResId();

        if( resLayoutId <= 0 ) {
            throw new IllegalArgumentException("layout resId is null");
        } else {
            mContentViewStub.setLayoutResource(resLayoutId);
            mContentViewStub.inflate();
        }
    }

    private void initTitle() {
        FrameLayout mFramelayout = getView(R.id.framelayout_title);
        View titleView = getTitleView(mFramelayout);
        if( titleView != null ) {
            mFramelayout.setVisibility(View.VISIBLE);
        }
    }

    private void initLoadingView() {
        mLoadingViewStub = getView(R.id.view_stub_loading);

        int resId = getLoadingResId();

        if( resId != 0 ) {
            mLoadingViewStub.setLayoutResource(resId);
            mLoadingViewStub.inflate();
            return ;
        }

        FrameLayout loadingFramelayout = (FrameLayout) mLoadingViewStub.inflate();
        View loadingView = getLoadingView(loadingFramelayout);

        if( loadingView != null ) {
            loadingFramelayout.setVisibility(View.VISIBLE);
            return;
        }

        mLoadingViewStub.setVisibility(View.GONE);
    }

    public final <E extends View> E getView(int viewId) {
        return mFindViewById.getView(viewId);
    }

    // 初始化头部
    protected View getTitleView(ViewGroup parent) {
        return null;
    }

    protected int getLoadingResId(){
        return 0;
    }

    protected View getLoadingView(ViewGroup parent){
        return null;
    }

    // 设置布局
    protected abstract int getContentResId();

    // 初始化界面
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();
}
