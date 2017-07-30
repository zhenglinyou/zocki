package com.zocki.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public interface IDBDaoSupport<T> {

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    public int insert( T t );

}
