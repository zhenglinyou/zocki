package com.zocki.db.library;

import android.database.sqlite.SQLiteDatabase;

import com.zocki.db.library.curd.QuerySupport;

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
    QuerySupport<T> query();

    /**
     * 删除
     * @param whereClause
     * @param whereArgs
     * @return
     */
    int delete(String whereClause, String[] whereArgs);

    /**
     * 更新
     * @param obj
     * @param whereClause
     * @param whereArgs
     * @return
     */
    int update(T obj, String whereClause, String... whereArgs);
}
