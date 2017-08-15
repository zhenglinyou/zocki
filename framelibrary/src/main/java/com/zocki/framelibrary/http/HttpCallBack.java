package com.zocki.framelibrary.http;

import android.content.Context;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.zocki.baselibrary.AppConfig;
import com.zocki.baselibrary.http.EngineCallBack;
import com.zocki.baselibrary.http.HttpUtils;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.IDBDao;
import com.zocki.db.library.factory.DBDaoFactory;
import com.zocki.framelibrary.entity.BaseEntity;
import com.zocki.framelibrary.http.cache.CacheData;

import java.util.List;
import java.util.Map;

/**
 * Created by kaisheng3 on 2017/7/28.
 * 回调扩展
 */
public abstract class HttpCallBack<T extends BaseEntity> implements EngineCallBack {

    private String preCacheData;

    @Override
    public void onPreExcute(boolean cache, String url, Context context, Map<String, Object> params) {
        // 添加默认参数

        // dialog显示
        onPreExcute();

        if(cache) {
            // 可以去线程调度
            IDBDao<CacheData> daoSupport = DBDaoFactory.getInstance().getDao(CacheData.class);
            List<CacheData> cacheDatas = daoSupport.query()
                    .setSelection("key=?")
                    .setSelectionArgs(new String[]{url})
                    .setLimit(1)
                    .query();

            if( cacheDatas.size() != 0 ) {
                // 缓存存在，直接返回
                preCacheData = cacheDatas.get(0).getValue();
                if( !TextUtils.isEmpty(preCacheData) ) {
                    handlerJsonResponse(cache, false, url, preCacheData );
                }
            }
        }
    }

    public void onPreExcute() {
    }

    @Override
    public void onSuccess(boolean cache, boolean isNewData, String url, String result) {

        if( preCacheData != null && cache) {
            // 与缓存数据相同
            if( preCacheData.equals(result) ) {
                return ;
            }
        }

        if( AppConfig.ADB ) LogUtils.e( "新数据 ==> " + result );

        handlerJsonResponse(cache, isNewData, url, result );
    }

    private void handlerJsonResponse(boolean isNeedCache, boolean isNewData, String url, String result) {
        try{
            Gson gson = new Gson();
            final T analysEntity =  (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
            analysEntity.isNewData = isNewData;
            if( isNeedCache && isNewData ) {
                // 只有返回接口状态码正确，才缓存数据
                if (analysEntity.code == AppConfig.HTTP_OK_CODE) {
                    IDBDao<CacheData> dao = DBDaoFactory.getInstance().getDao(CacheData.class);
                    dao.delete("key=?", new String[]{url});
                    dao.insert(new CacheData(url, result, System.currentTimeMillis()));
                }
            }
            HttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess ( analysEntity );
                }
            });
        } catch (final Exception e) {
            HttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    onError ( e );
                }
            });
            LogUtils.e( "json解析异常" );
        }
    }

    public abstract void onSuccess(T result) ;

    public final EngineCallBack DEFUALT_CALL_BACK = new EngineCallBack() {

        @Override
        public void onPreExcute(boolean isNewData, String url, Context context, Map<String, Object> params) {
        }

        @Override
        public void onError(Exception e) {
        }

        @Override
        public void onSuccess( boolean cache,boolean isNewData, String url, String result) {
        }
    };
}
