package com.zocki.db.library.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.tablemanager.Generator;
import com.zocki.db.library.tablemanager.Upgradetor;

/**
 * Created by kaisheng3 on 2017/8/21.
 * 数据库帮助类
 */
public class DBOpenHelper extends SQLiteOpenHelper {

    //http://blog.csdn.net/crystalddd/article/details/37742021

    public DBOpenHelper(Context context, String name, int version) {
        super(context, name, null, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Generator.create(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtils.e( " 数据库升级 " );
        Upgradetor.updata(db, oldVersion, newVersion);
    }
}
