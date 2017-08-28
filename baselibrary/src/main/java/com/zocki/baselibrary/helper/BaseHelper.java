package com.zocki.baselibrary.helper;

/**
 * Created by kaisheng3 on 2017/8/28.
 */

public abstract class BaseHelper {

    public int pageNum = 1 ;
    public int pageSize = 20;
    public boolean hasMore = true;
    public boolean isRefreshing = false;

    public BaseHelper() {
    }

    public abstract void initView() ;

    public abstract void initListener() ;

    public void initData() {
    }

    public void requestData( boolean isRefresh ) {
        if( isRefresh ) pageNum = 1;
        else pageNum ++;
    }

    public boolean isNoMore(boolean isRefresh ) {
        return ( !hasMore && !isRefresh );
    }
}
