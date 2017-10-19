package com.zocki.baselibrary.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.zocki.baselibrary.R;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.baselibrary.viewheper.FindViewById;

public abstract class SimpleTempFragment extends BaseFragment2 {

    public View rootView;

    private FindViewById mFindViewById;

    private ViewStub mContentViewStub, mLoadingViewStub;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if( rootView != null ) return rootView;
        rootView = inflater.inflate(R.layout.base_fragment_layout,null);

        mFindViewById = new FindViewById(rootView);

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if( parent != null ) parent.removeView( rootView );

        return rootView;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        initTitle();

        initContentView();

        initLoadingView();

        initView();
    }

    @Override
    protected void onFragmentVisibleChange(boolean isVisible) {

        LogUtils.e( isVisible );

        if (isVisible) {
            // 更新界面数据，如果数据还在下载中，就显示加载框
        } else {
            // 关闭加载框
        }
    }

    @Override
    protected void onFragmentFirstVisible() {
        // 去服务器下载数据
        LogUtils.e(" 加载数据 ");
    }


    public final <E extends View> E getView(int viewId) {
        return mFindViewById.getView(viewId);
    }

    private void initContentView(){

        mContentViewStub = mFindViewById.getView(R.id.view_stub_content);

        int resLayoutId = getContentResId();
        if( resLayoutId <= 0 ) {
            throw new IllegalArgumentException("layout resId is null");
        } else {
            mContentViewStub.setLayoutResource(resLayoutId);
            mContentViewStub.inflate();
        }
    }

    private void initTitle() {
        FrameLayout mFramelayout = mFindViewById.getView(R.id.framelayout_title);
        View titleView = getTitleView();
        if( titleView != null ) {
            mFramelayout.setVisibility(View.VISIBLE);
            mFramelayout.addView(titleView);
        }
    }

    private void initLoadingView() {
        mLoadingViewStub = mFindViewById.getView(R.id.view_stub_loading);

        int resId = getLoadingResId();
        if( resId != 0 ) {
            mLoadingViewStub.setLayoutResource(resId);
            mLoadingViewStub.inflate();
            return ;
        }

        FrameLayout loadingFramelayout = (FrameLayout) mLoadingViewStub.inflate();
        View loadingView = getLoadingView();
        if( loadingView != null ) {
            loadingFramelayout.addView(loadingView);
            loadingFramelayout.setVisibility(View.VISIBLE);
            return;
        }

        mLoadingViewStub.setVisibility(View.GONE);
    }


    // 初始化头部
    protected View getTitleView() {
        return null;
    }

    protected int getLoadingResId(){
        return 0;
    }

    protected View getLoadingView(){
        return null;
    }

    // 设置布局
    protected abstract int getContentResId();

    // 初始化界面
    protected abstract void initView();

    // 初始化数据
    protected abstract void initData();

}
