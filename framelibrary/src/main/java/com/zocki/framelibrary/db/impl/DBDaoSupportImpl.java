package com.zocki.framelibrary.db.impl;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zocki.baselibrary.AppConfig;
import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.framelibrary.db.DaoUtil;
import com.zocki.framelibrary.db.IDBDaoSupport;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DBDaoSupportImpl<T> implements IDBDaoSupport<T> {

    private SQLiteDatabase mSqLiteDatabase;
    private Class<T> mClazz;
    private final Object[] mPutMethondAttrs = new Object[2];
    private final Map<String,Method> mPutMethods = new HashMap<>();

    private boolean filterName( String name ) {
        if( name.equals("serialVersionUID") ) return true;
        return false;
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
        
        sql.append("create table if not exists ")
                .append(DaoUtil.getTableName(clazz))
                .append("(id integer primary key autoincrement, ");

        // 反射 获取变量名称
        for (Field field : mClazz.getDeclaredFields()) {

            if( field.isSynthetic() ) continue;
            field.setAccessible(true);

            String name = field.getName();
            if( filterName(name) ) continue;

            // type 需要进行转换 int --> integer String --> text
            String type = field.getType().getSimpleName();
            sql.append( name ).append(" ").append(DaoUtil.getColumnType(type) ).append(", ");
        }

        sql.replace(sql.length()-2,sql.length(),")");

        if(AppConfig.ADB) LogUtils.e( " create table sql = " + sql.toString() );

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

        // 批量插入，采用事务方式
        mSqLiteDatabase.beginTransaction();

        for (T data : datas) {
            insert(data);
        }

        mSqLiteDatabase.setTransactionSuccessful();
        mSqLiteDatabase.endTransaction();
    }

    /**
     * 查询所有
     * @return All
     */
    @Override
    public List<T> query() {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    /**
     * 有条件查询
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @param limit
     * @return
     */
    @Override
    public List<T> query( String selection, String[] selectionArgs, String orderBy, int limit ) {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), null, selection, selectionArgs, null, null, orderBy, String.valueOf(limit));
        return cursorToList( cursor );
    }

    /**
     * 有条件查询
     * @param selection
     * @param selectionArgs
     * @param orderBy
     * @return
     */
    @Override
    public List<T> query(String selection, String[] selectionArgs, String orderBy) {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), null, selection, selectionArgs, null, null, orderBy, null);
        return cursorToList( cursor );
    }

    /**
     * 有条件查询
     * @param selection
     * @param selectionArgs
     * @return
     */
    @Override
    public List<T> query(String selection, String[] selectionArgs) {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), null, selection, selectionArgs, null, null, null, null);
        return cursorToList( cursor );
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

    private List<T> cursorToList(Cursor cursor) {
        List<T> list = new ArrayList<>();
        if( cursor != null && cursor.moveToFirst() )
        {
            do {
                try {
                    T instance = mClazz.newInstance();
                    Field[] fields = mClazz.getDeclaredFields();
                    for (Field field : fields) {
                        field.setAccessible(true);
                        String name = field.getName();
                        int index = cursor.getColumnIndex(name);
                        if( index < 0 ) {
                            continue;
                        }
                        // 通过反射获取游标的方法
                        Method cursorMethod = cursorMethod(field.getType());
                        if( cursorMethod != null )
                        {
                            Object value = cursorMethod.invoke(cursor,index);
                            if( value == null ) {
                                continue;
                            }

                            Class<?> fieldType = field.getType();
                            // 特殊部分
                            if( fieldType == boolean.class || fieldType == Boolean.class ) {
                                if( "0".equals(String.valueOf(value)) ) {
                                    value = false;
                                } else if( "1".equals(String.valueOf(value)) ) {
                                    value = true;
                                }
                            } else if( fieldType == char.class || fieldType == Character.class  ) {
                                value = value.toString().charAt(0);
                            } else if( field.getType() == Date.class ) {
                                long date = (long) value;
                                if( date <= 0 ) {
                                    value = null;
                                } else {
                                    value = new Date(date);
                                }
                            }
                            field.set(instance,value);
                        }
                    }
                    // 加入集合
                    list.add( instance );
                }catch (Exception e) {
                    String message = e.getMessage();
                    if( message.contains("has no zero argument constructor") ) {
                        LogUtils.e( mClazz.getName() + " 需要添加无参构造函数 ");
                    }
                    e.printStackTrace();
                }
            }while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    private Method cursorMethod(Class<?> type) throws NoSuchMethodException {
        String methodName = getColumnMethodName(type);
        Method method = Cursor.class.getMethod(methodName,int.class);
        return method;
    }

    private String getColumnMethodName(Class<?> fieldType) {

        String typeName;
        if( fieldType.isPrimitive() ) {
            typeName = DaoUtil.capitalize(fieldType.getName());
        } else {
            typeName = fieldType.getSimpleName();
        }

        String methodName = "get" + typeName;
        if( "getBoolean".equals(methodName) ) {
            methodName = "getInt";
        } else if( "getChar".equals(methodName) ) {
            methodName = "getString";
        } else if( "getDate".equals(methodName) ) {
            methodName = "getLong";
        } else if( "getInteger".equals(methodName) ) {
            methodName = "getInt";
        }

        return methodName;
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
