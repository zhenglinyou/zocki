package com.zocki.framelibrary.db.impl;

import android.database.sqlite.SQLiteDatabase;

import com.zocki.baselibrary.AppConfig;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.framelibrary.db.DaoUtil;
import com.zocki.framelibrary.db.IDBDaoSupport;

import java.lang.reflect.Field;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class DBDaoSupportImpl<T> implements IDBDaoSupport<T> {

    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;

    public void init(SQLiteDatabase sqLiteDatabase, Class<T> clazz) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;

        // 创建表
        StringBuffer sql = new StringBuffer();
        /*sql.append("create table if not exists Persion(")
                .append("id integer primary key autoincreament,")
                .append("name text,")
                .append("age integer,")
                .append("flag boolean")
                .append(";");*/

        sql.append("create table if not exists")
                .append(DaoUtil.getTableName(clazz))
                .append("(id integer primary key autoincreament,");

        // 反射 获取变量名称
        for (Field field : mClazz.getDeclaredFields()) {
            field.setAccessible(true);
            String name = field.getName();
            // type 需要进行转换 int --> integer String --> text
            String type = field.getType().getSimpleName();
            sql.append( name ).append(DaoUtil.getColumnType(type) ).append(", ");
        }

        sql.replace(sql.length()-2,sql.length(),")");

        if(AppConfig.ADB) LogUtils.e( " create table sql = " + sql.toString() );

        mSqLiteDatabase.execSQL( sql.toString() );
    }

    @Override
    public int insert(T t) {
        return 0;
    }
}
