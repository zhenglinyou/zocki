package com.zocki.framelibrary.db;

import android.database.sqlite.SQLiteDatabase;

import java.util.List;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public interface IDBDaoSupport<T> {

    void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz);

    /**
     * 单条插入
     * @param t
     * @return
     */
    long insert(T t);

    /**
     * 批量插入
     * @param datas
     */
    void insert(List<T> datas);

    /**
     * 查询所有
     * @return
     */
    List<T> query();

    /**
     * 指定条件查询
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @param limit
     * @return
     */
    List<T> query( String selection, String[] selectionArgs, String orderBy, int limit );

    /**
     * 指定条件查询
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return
     */
    List<T> query( String selection, String[] selectionArgs, String orderBy );

    /**
     * 指定条件查询
     * @param selection
     * @param selectionArgs
     * @return
     */
    List<T> query( String selection, String[] selectionArgs );

    /**
     * 删除
     * @param whereClause
     * @param whereArgs
     * @return
     */
    int delete(String whereClause,String[] whereArgs);

    /**
     * 更新
     * @param obj
     * @param whereClause
     * @param whereArgs
     * @return
     */
    int update(T obj, String whereClause, String... whereArgs);
}
