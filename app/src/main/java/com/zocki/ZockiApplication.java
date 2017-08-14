package com.zocki;

import android.app.Application;
import android.content.pm.PackageManager;

import com.alipay.euler.andfix.patch.PatchManager;
import com.zocki.baselibrary.crash.ExceptionCrashHandler;
import com.zocki.framelibrary.FrameLibraryInit;
import com.zocki.framelibrary.skin.SkinManager;

/**
 * Created by kaisheng3 on 2017/7/20.
 */
public class ZockiApplication extends Application {

    // Patch管理类
    public static PatchManager mPatchManager;

    @Override
    public void onCreate() {
        super.onCreate();
        // 异常处理
        ExceptionCrashHandler.getInstance().init( this );

        //
        FrameLibraryInit.setHttpEngine( this );

        SkinManager.getInstance().init(getApplicationContext());

        // Ali热修复
        try {
            mPatchManager = new PatchManager(this);
            // 初始化patch版本
            String pkName = this.getPackageName();
            String versionName = getPackageManager().getPackageInfo(pkName, 0).versionName;
            // 初始化版本名称
            mPatchManager.init(versionName);
            // 加载之前的patch
            mPatchManager.loadPatch();
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }
}
