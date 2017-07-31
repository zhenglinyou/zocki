package com.zocki.framelibrary.http;

import android.content.Context;

import com.google.gson.Gson;
import com.zocki.baselibrary.http.EngineCallBack;
import com.zocki.baselibrary.http.HttpUtils;
import com.zocki.baselibrary.logger.LogUtils;

import java.util.Map;

/**
 * Created by kaisheng3 on 2017/7/28.
 * 回调扩展
 */
public abstract class HttpCallBack<T> implements EngineCallBack {

    @Override
    public void onPreExcute(Context context,Map<String, Object> params) {
        // 添加默认参数

        // dialog显示
        onPreExcute();
    }

    public void onPreExcute() {
    }

    @Override
    public void onSuccess(String result) {
        Gson gson = new Gson();
        try{
            final T analysT =  (T) gson.fromJson(result, HttpUtils.analysisClazzInfo(this));
            HttpUtils.handler.post(new Runnable() {
                @Override
                public void run() {
                    onSuccess ( analysT );
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
        public void onPreExcute(Context context,Map<String, Object> params) {
        }
        @Override
        public void onError(Exception e) {
        }
        @Override
        public void onSuccess(String result) {
        }
    };
}
