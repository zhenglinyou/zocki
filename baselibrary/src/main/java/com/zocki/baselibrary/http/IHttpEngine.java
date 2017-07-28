package com.zocki.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by kaisheng3 on 2017/7/28.
 * 网络引擎
 */
public interface IHttpEngine {
    // get 请求
    void get(Context context,String url, Map<String,Object> params, EngineCallBack callBack);

    // post 请求
    void post(Context context,String url, Map<String,Object> params, EngineCallBack callBack);

    // 下载文件

    // 上传文件

    // https 添加证书
}
