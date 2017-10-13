package com.zocki.baselibrary.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

public class HttpUtils {

    private static IHttpConnector mHttpConnector = null;

    public static void setDefalutHttpConnector(IHttpConnector h) {
        HttpUtils.mHttpConnector = h;
    }

    public static Handler handler;

    static {
        handler = new Handler(Looper.getMainLooper());
    }

    public enum MethodType {
        GET, POST
    }

    private String mUrl;
    private MethodType mMethodType = MethodType.GET;
    private Map<String,Object> mParams;
    private boolean mCache;
    private Context mContext;

    private HttpUtils(Context context) {
        this.mContext = context;
        this.mParams = new HashMap<>();
    }

    public static HttpUtils with( Context context ) {
        return new HttpUtils(context);
    }

    public HttpUtils url( String url ) {
        mUrl = url;
        return this;
    }

    public HttpUtils post() {
        mMethodType = MethodType.POST;
        return this;
    }

    public HttpUtils get() {
        mMethodType = MethodType.GET;
        return this;
    }

    public HttpUtils addParam(String key, Object value) {
        mParams.put(key,value);
        return this;
    }

    public HttpUtils addParam(Map<String,Object> params) {
        mParams.putAll(params);
        return this;
    }

    public HttpUtils cache( boolean isCache ) {
        this.mCache = isCache;
        return this;
    }

    public void execute(IHttpCallBack callBack) {
        if( callBack == null ) {
            callBack = IHttpCallBack.DEFUALT_CALL_BACK;
        }

        if( mMethodType == MethodType.GET ) {
            get( mCache,mUrl, mParams, callBack );
        } else {
            post( mCache,mUrl, mParams, callBack );
        }
    }

    public static void init(IHttpConnector httpEngine) {
        mHttpConnector = httpEngine;
    }

    public static String joinParams( String url, Map<String,Object> parmas) {
        StringBuilder buffer = new StringBuilder(url);
        int paramCount = parmas == null ? 0 : parmas.size();
        if( paramCount > 0 ) {
            buffer.append("?");
            for (String key : parmas.keySet()) {
                buffer.append(key).append("=").append(parmas.get(key).toString());
                paramCount--;
                if (paramCount > 0) buffer.append("&");
            }
        }
        return buffer.toString();
    }

    public HttpUtils exchangeEngine(IHttpConnector httpEngine) {
        mHttpConnector = httpEngine;
        return this;
    }

    private void get(boolean cache, String url, Map<String, Object> params, IHttpCallBack callBack) {
        mHttpConnector.get(cache,mContext,url, params, callBack);
    }

    private void post(boolean cache,String url, Map<String, Object> params, IHttpCallBack callBack) {
        mHttpConnector.post(cache,mContext,url, params, callBack);
    }

    public static Class<?> analysisClazzInfo( Object object ) {
        Type getType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) getType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
