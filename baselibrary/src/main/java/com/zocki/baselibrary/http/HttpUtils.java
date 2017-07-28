package com.zocki.baselibrary.http;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
/**
 * Created by kaisheng3 on 2017/7/28.
 * 统一请求网络
 */
public class HttpUtils {

    private static IHttpEngine mHttpEngine = new OkHttpEngine();

    public static Handler handler;

    static {
        handler = new Handler(Looper.getMainLooper());
    }

    public enum MethodType {
        GET,POST
    }

    /**
     * 请求连接
     */
    private String mUrl;
    /**
     * 请求方法
     */
    private MethodType mMethodType = MethodType.GET;
    /**
     * 参数
     */
    private Map<String,Object> mParams;

    private Context mContext;

    private HttpUtils(Context context) {
        this.mContext = context;
        this.mParams = new HashMap<>();
    }

    public static HttpUtils with( Context context ) {
        return new HttpUtils(context);
    }

    /**
     * 设置URL
     * @param url
     * @return
     */
    public HttpUtils url( String url ) {
        mUrl = url;
        return this;
    }

    /**
     * 请求方式
     * @return
     */
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

    public void execute(EngineCallBack callBack) {
        if( callBack == null ) {
            callBack = EngineCallBack.DEFUALT_CALL_BACK;
        }

        if( mMethodType == MethodType.GET ) {
            get( mUrl, mParams, callBack );
        } else {
            post( mUrl, mParams, callBack );
        }
    }

    /**
     * 初始化引擎
     * @param httpEngine
     */
    public static void init(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
    }

    public static String joinParams( String url, Map<String,Object> parmas) {
        StringBuffer buffer = new StringBuffer(url).append("?");
        int paramCount = parmas == null ? 0 : parmas.size();
        for( String key : parmas.keySet() )
        {
            buffer.append(key).append("=").append(parmas.get(key).toString());
            paramCount --;
            if( paramCount > 0 ) buffer.append("&");
        }
        return buffer.toString();
    }

    /**
     * 请求时自带引擎
     * @param httpEngine
     */
    public HttpUtils exchangeEngine(IHttpEngine httpEngine) {
        mHttpEngine = httpEngine;
        return this;
    }

    private void get(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.get(mContext,url, params, callBack);
    }

    private void post(String url, Map<String, Object> params, EngineCallBack callBack) {
        mHttpEngine.post(mContext,url, params, callBack);
    }

    public static Class<?> analysisClazzInfo( Object object ) {
        Type getType = object.getClass().getGenericSuperclass();
        Type[] params = ((ParameterizedType) getType).getActualTypeArguments();
        return (Class<?>) params[0];
    }

}
