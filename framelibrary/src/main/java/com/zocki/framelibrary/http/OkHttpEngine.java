package com.zocki.framelibrary.http;

import android.content.Context;
import android.support.annotation.NonNull;

import com.zocki.baselibrary.AppConfig;
import com.zocki.baselibrary.http.EngineCallBack;
import com.zocki.baselibrary.http.HttpUtils;
import com.zocki.baselibrary.http.IHttpEngine;
import com.zocki.baselibrary.logger.LogUtils;

import java.io.File;
import java.io.IOException;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * Created by kaisheng3 on 2017/7/28.
 * OK http 默认请求
 */
public class OkHttpEngine implements IHttpEngine {

    private static OkHttpClient mOkHttpClient;

    static {
        mOkHttpClient = new OkHttpClient();
    }

    @Override
    public void get(final boolean cache, Context context, String url, Map<String, Object> params, @NonNull final EngineCallBack httpCallBack) {

        final String mKeyUrl = HttpUtils.joinParams(url,params);

        if(AppConfig.ADB) LogUtils.e( "get 请求链接 : " + mKeyUrl);

        httpCallBack.onPreExcute(cache, mKeyUrl, context, params);

        Request.Builder requestBuilder = new Request.Builder().url(mKeyUrl).tag(context);
        Request request = requestBuilder.build();

        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                httpCallBack.onError(e);
            }

            @Override
            public void onResponse(Call call, @NonNull Response response) throws IOException {
                ResponseBody body = response.body();
                if( body != null ) {
                    final String resultJson = body.string();
                    httpCallBack.onSuccess(cache, true, mKeyUrl, resultJson);
                } else {
                    httpCallBack.onError(new NullPointerException( mKeyUrl + " respose body() is null"));
                }
            }
        });
    }

    @Override
    public void post(final boolean cache, Context context, final String url, Map<String, Object> params, final EngineCallBack httpCallBack) {

        final String mKeyUrl = HttpUtils.joinParams(url,params);

        if(AppConfig.ADB) LogUtils.e( "post 请求链接 : " + mKeyUrl);

        if( httpCallBack != null ) httpCallBack.onPreExcute(cache,mKeyUrl,context,params);

        RequestBody requestBody = appendBody(params);
        Request request = new Request.Builder()
                .url(url)
                .tag(context)
                .post(requestBody)
                .build();

        mOkHttpClient.newCall(request).enqueue(
                new Callback() {
                    @Override
                    public void onFailure(Call call, final IOException e) {
                        executeError(httpCallBack, e);
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        ResponseBody body = response.body();
                        if (body != null) {
                            String resultJson = body.string();
                            executeSuccessMethod(cache,httpCallBack, mKeyUrl, resultJson);
                        }
                    }
                }
        );
    }

    private RequestBody appendBody(Map<String,Object> params) {
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM);
        addParams(builder,params);
        return builder.build();
    }

    private void addParams(MultipartBody.Builder builder, Map<String, Object> params) {
        if( params != null && !params.isEmpty() )
        {
            for( String key : params.keySet() ) {
                builder.addFormDataPart( key, params.get(key).toString() );
                Object value = params.get(key);
                if( value instanceof File ) {
                    File file = (File) value;
                    builder.addFormDataPart(key, file.getName(),
                            RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())),file));
                } else if( value instanceof List ) {
                    try {
                        List<File> listFiles = (List<File>) value;
                        for( int i = 0; i < listFiles.size(); i++ ) {
                            File file = listFiles.get(i);
                            builder.addFormDataPart(key, file.getName(),
                                    RequestBody.create(MediaType.parse(guessMimeType(file.getAbsolutePath())),file));
                        }
                    }catch (Exception e ) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    private String guessMimeType( String path ) {
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(path);
        if( contentTypeFor == null ) {
            contentTypeFor = "application/octet-stream";
        }
        return contentTypeFor;
    }

    /**
     *  执行成功的方法
     **/
    private void executeSuccessMethod(boolean cache,final EngineCallBack httpCallBack, String url, final String resultJson) {
        try {
            httpCallBack.onSuccess(cache, true, url, resultJson);
        } catch (Exception e) {
            executeError(httpCallBack, e);
            e.printStackTrace();
        }
    }

    /**
     *  执行失败的方法
     */
    private void executeError(final EngineCallBack httpCallBack, final Exception e) {
        HttpUtils.handler.post(new Runnable() {
            @Override
            public void run() {
                httpCallBack.onError(e);
            }
        });
    }
}
