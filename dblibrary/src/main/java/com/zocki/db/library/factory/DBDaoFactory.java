package com.zocki.db.library.factory;

import com.zocki.db.library.PserInitialize;
import com.zocki.db.library.dao.IDBDao;
import com.zocki.db.library.helper.DBOpenHelper;
import com.zocki.db.library.impl.DBDaoImpl;

/**
 * Created by Administrator on 2017/7/30 0030.
 */
public class DBDaoFactory {

    private static DBDaoFactory instance;
    private DBOpenHelper mDBOpenHelper;

    private DBDaoFactory() {
        mDBOpenHelper = new DBOpenHelper(PserInitialize.getContext(),PserInitialize.dbName,PserInitialize.dbVersion);
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
        IDBDao<T> daoSupport = new DBDaoImpl<>();
        daoSupport.init(mDBOpenHelper.getWritableDatabase(),clazz);
        return daoSupport;
    }
}
