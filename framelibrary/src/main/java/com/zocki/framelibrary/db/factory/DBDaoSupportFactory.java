package com.zocki.framelibrary.db.factory;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.zocki.framelibrary.db.IDBDaoSupport;
import com.zocki.framelibrary.db.impl.DBDaoSupportImpl;

import java.io.File;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public class DBDaoSupportFactory {

    private static DBDaoSupportFactory instance;
    private SQLiteDatabase mSqLiteDatabase;

    private DBDaoSupportFactory() {
        // 把数据库放在内存卡里面
        File dbRoot = new File(Environment.getExternalStorageDirectory().getAbsolutePath()
            + File.separator + "zocki" + File.separator + "database"
        );

        if( !dbRoot.exists() ) {
            dbRoot.mkdirs();
        }

        File dbFile = new File( dbRoot, "zocki.db");

        // 打开或创建一个数据库
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);
    }

    public static DBDaoSupportFactory getInstance() {
        if( instance == null ) {
            synchronized (DBDaoSupportFactory.class) {
                if( instance == null ) instance = new DBDaoSupportFactory();
            }
        }
        return instance;
    }


    public <T> IDBDaoSupport<T> getDao( Class<T> clazz ) {
        IDBDaoSupport<T> daoSupport = new DBDaoSupportImpl<T>();
        return daoSupport;
    }


}
