package com.zocki.framelibrary;

import android.content.Context;

import com.zocki.baselibrary.http.HttpUtils;
import com.zocki.framelibrary.http.OkHttpEngine;

/**
 * Created by kaisheng3 on 2017/8/1.
 */

public class FrameLibraryInit {

    public static void setHttpEngine(Context context) {
        HttpUtils.setDefalutHttpConnector( new OkHttpEngine() );
    }

}
