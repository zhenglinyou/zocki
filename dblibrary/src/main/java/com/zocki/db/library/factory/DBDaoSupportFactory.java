package com.zocki.db.library.factory;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.IDBDaoSupport;
import com.zocki.db.library.impl.DBDaoSupportImpl;

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
            boolean b = dbRoot.mkdirs();
        }

        File dbFile = new File( dbRoot, "zocki.db");

        // 打开或创建一个数据库
        mSqLiteDatabase = SQLiteDatabase.openOrCreateDatabase(dbFile, null);

        LogUtils.e( mSqLiteDatabase.getVersion() );

        mSqLiteDatabase.setVersion( 1 );
    }

    public static DBDaoSupportFactory getInstance() {
        if(instance == null) {
            synchronized (DBDaoSupportFactory.class) {
                if(instance == null) instance = new DBDaoSupportFactory();
            }
        }
        return instance;
    }

    public <T> IDBDaoSupport<T> getDao(Class<T> clazz) {
        IDBDaoSupport<T> daoSupport = new DBDaoSupportImpl<T>();
        daoSupport.init(mSqLiteDatabase,clazz);
        return daoSupport;
    }

}
