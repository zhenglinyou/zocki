package com.zocki.db.library.factory;

import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;

import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.IDBDao;
import com.zocki.db.library.impl.DBDaoImpl;

import java.io.File;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public class DBDaoFactory {

    private static DBDaoFactory instance;
    private SQLiteDatabase mSqLiteDatabase;

    private DBDaoFactory() {
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

    public static DBDaoFactory getInstance() {
        if(instance == null) {
            synchronized (DBDaoFactory.class) {
                if(instance == null) instance = new DBDaoFactory();
            }
        }
        return instance;
    }

    public <T> IDBDao<T> getDao(Class<T> clazz) {
        IDBDao<T> daoSupport = new DBDaoImpl<T>();
        daoSupport.init(mSqLiteDatabase,clazz);
        return daoSupport;
    }

}
