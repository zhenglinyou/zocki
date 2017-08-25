package com.zocki.baselibrary.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.FrameLayout;

import com.zocki.baselibrary.R;
import com.zocki.baselibrary.ioc.ViewUtils;
import com.zocki.baselibrary.viewheper.FindViewById;

/**
 * Created by kaisheng3 on 2017/8/16.
 */
public abstract class BaseFragment extends Fragment {

    private boolean isVisible = false; //当前Fragment是否可见
    private boolean isInitView = false; //是否与View建立起映射关系
    private boolean isFirstLoad = true; //是否是第一次加载数据
    private boolean isRequestData = false;

    public View rootView;

    private FindViewById mFindViewById;

    private ViewStub mContentViewStub, mLoadingViewStub;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        if( rootView != null && !isRequestData ) return rootView;
        rootView = inflater.inflate(R.layout.base_fragment_layout,null);

        mFindViewById = new FindViewById(rootView);

        ViewGroup parent = (ViewGroup) rootView.getParent();
        if( parent != null ) parent.removeView( rootView );

        isInitView = true;

        initTitle();

        initContentView();

        initLoadingView();

        initView();

        ViewUtils.inject(rootView);

        lazyLoadData();

        return rootView;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    public final <E extends View> E getView(int viewId) {
        return mFindViewById.getView(viewId);
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        if ( isVisibleToUser ) {
            isVisible = true;
            lazyLoadData();
        } else {
            isVisible = false;
        }
        super.setUserVisibleHint(isVisibleToUser);
    }

    private void lazyLoadData() {
        if (!isFirstLoad || !isVisible || !isInitView) {
            if( isRequestData ) initData();
            return;
        }

        initData();

        isFirstLoad = false;
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
        View loadingView = getLoadingView();
        if( loadingView != null ) {
            loadingFramelayout.addView(loadingView);
            loadingFramelayout.setVisibility(View.VISIBLE);
            return;
        }

        mLoadingViewStub.setVisibility(View.GONE);
    }


    public final void setRequestData(boolean isRequestData ) {
        this.isRequestData = isRequestData;
    }

    // 初始化头部
    protected View getTitleView(ViewGroup parent) {
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
