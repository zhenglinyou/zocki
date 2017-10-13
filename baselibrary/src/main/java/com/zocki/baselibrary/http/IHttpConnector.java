package com.zocki.baselibrary.http;

import android.content.Context;

import java.util.Map;

/**
 * Created by kaisheng3 on 2017/7/28.
 * 网络引擎
 */
public interface IHttpConnector {
    // get 请求
    void get(boolean cache, Context context, String url, Map<String,Object> params, IHttpCallBack callBack);

    // post 请求
    void post(boolean cache, Context context, String url, Map<String,Object> params, IHttpCallBack callBack);

    // 下载文件

    // 上传文件

    // https 添加证书
}
