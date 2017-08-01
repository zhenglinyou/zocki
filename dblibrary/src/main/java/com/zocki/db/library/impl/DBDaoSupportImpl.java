package com.zocki.db.library.impl;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;

import com.zocki.baselibrary.AppConfig;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.DaoUtil;
import com.zocki.db.library.IDBDaoSupport;
import com.zocki.db.library.curd.QuerySupport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDaoSupportImpl<T> implements IDBDaoSupport<T> {

    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;
    private final Object[] mPutMethondAttrs = new Object[2];
    private final Map<String,Method> mPutMethods = new HashMap<>();
    private QuerySupport<T> mQuerySupport;

    private boolean filterName( String name ) {
       return name.equals("serialVersionUID");
    }

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
        
        sql.append("CREATE TABLE IF NOT EXISTS ")
                .append(DaoUtil.getTableName(clazz))
                .append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, ");

        // 反射 获取变量名称
        for (Field field : mClazz.getDeclaredFields()) {

            if( field.isSynthetic() ) continue;
            field.setAccessible(true);

            String name = field.getName();
            if( filterName(name) ) continue;

            // type 需要进行转换 int --> integer String --> text
            String type = field.getType().getSimpleName();
            sql.append( name ).append(" ").append(DaoUtil.getColumnType(type)).append(", ");
        }

        sql.replace(sql.length() - 2, sql.length(),")");

        // if(AppConfig.ADB) LogUtils.e( " create table sql = " + sql.toString() );

        mSqLiteDatabase.execSQL( sql.toString() );
    }

    @Override
    public long insert(T obj) {
        ContentValues values = transfromContentValue( obj );
        return mSqLiteDatabase.insert( DaoUtil.getTableName(obj.getClass()),null, values );
    }

    @Override
    public void insert(List<T> datas) {
        if( datas == null ) return ;
        try {
            // 批量插入，采用事务方式
            mSqLiteDatabase.beginTransaction();

            for (T data : datas) {
                insert( data );
            }

            mSqLiteDatabase.setTransactionSuccessful();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mSqLiteDatabase.endTransaction();
        }
    }

    /**
     * 查询所有
     * @return All
     */
    @Override
    public QuerySupport<T> query() {
        if( mQuerySupport == null ) mQuerySupport = new QuerySupport<>(mSqLiteDatabase,mClazz);
        return mQuerySupport;
    }

    @Override
    public int delete(String whereClause,String[] whereArgs) {
        return mSqLiteDatabase.delete(DaoUtil.getTableName(mClazz),whereClause,whereArgs);
    }

    @Override
    public int update(T obj, String whereClause, String... whereArgs) {
        ContentValues values = transfromContentValue(obj);
        return mSqLiteDatabase.update(DaoUtil.getTableName(mClazz),values,whereClause,whereArgs);
    }

    /**
     * Obj 转成 contentValues
     * @param obj
     * @return
     */
    private ContentValues transfromContentValue(T obj) {

        ContentValues values = new ContentValues();
        for (Field field : obj.getClass().getDeclaredFields()) {
            field.setAccessible(true);
            try {

                if( field.isSynthetic() ) continue;

                String key = field.getName();
                if( filterName(key) ) continue;

                Object value = field.get( obj );

                mPutMethondAttrs[0] = key;
                mPutMethondAttrs[1] = value;

                String fieldNameType = field.getType().getName();

                Method putMethod = mPutMethods.get(fieldNameType);
                if( putMethod == null ) {
                    // 插入方法
                    putMethod = ContentValues.class.getDeclaredMethod("put", String.class, value.getClass());
                    mPutMethods.put(fieldNameType,putMethod);
                }

                // putMethod.invoke(values, key, value);
                // 优化
                putMethod.invoke(values,mPutMethondAttrs);

                // value 需要制定确定类型
                // values.put(field.getName(), value);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                mPutMethondAttrs[0] = mPutMethondAttrs[1] = null;
            }
        }
        return values;
    }
}
