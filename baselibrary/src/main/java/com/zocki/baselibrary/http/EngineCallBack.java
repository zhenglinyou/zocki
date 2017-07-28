package com.zocki.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by kaisheng3 on 2017/7/28.
 */

public interface EngineCallBack {

    void onPreExcute(Context context,Map<String, Object> params);

    // 失败
    void onError( Exception e ) ;
    // 成功
    void onSuccess( String result );

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
