package com.zocki;

import android.app.Application;
import android.content.pm.PackageManager;
import android.os.Environment;

import com.alipay.euler.andfix.patch.PatchManager;
import com.zocki.baselibrary.crash.ExceptionCrashHandler;
import com.zocki.db.library.PserInitialize;
import com.zocki.db.library.tablemanager.TabModel;
import com.zocki.dbtest.Person;
import com.zocki.dbtest.Person2;
import com.zocki.framelibrary.FrameLibraryInit;
import com.zocki.framelibrary.skin.SkinManager;

import java.io.File;
import java.util.ArrayList;

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

        FrameLibraryInit.setHttpEngine( this );

        SkinManager.getInstance().init(getApplicationContext());


        /**************** 数据库配置 *******************/

        String dbPath = Environment.getExternalStorageDirectory().getAbsolutePath()
                + File.separator + "zocki" + File.separator + "database";

        PserInitialize.initialize(this, dbPath, "zocki1133", 11);

        ArrayList<TabModel> tabModels = new ArrayList<>();
        tabModels.add(new TabModel(Person.class));
        tabModels.add(new TabModel(Person2.class));

        PserInitialize.addTableModels(tabModels);

        /**************** 数据库配置 end *******************/

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
