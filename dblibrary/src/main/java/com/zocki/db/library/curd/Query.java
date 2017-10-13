package com.zocki.db.library.curd;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.utils.DaoUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by kaisheng3 on 2017/8/1.
 */
public class Query<T> {

    private SQLiteDatabase mSqLiteDatabase;

    private Class<T> mClazz;

    // 查询条件
    private String mSelection;
    // 条件所对应参数
    private String[] mSelectionArgs;
    // 查询条数
    private String mLimit;
    // 排序
    private String mOrderBy;
    // 列
    private String[] mColumns;
    // 查询结果集进行过滤
    private String mHaving;
    // 分组
    private String mGroupBy;

    public Query(SQLiteDatabase sqLiteDatabase, Class<T> clazz ) {
        this.mSqLiteDatabase = sqLiteDatabase;
        this.mClazz = clazz;
    }

    /**
     * 查询条件
     * @param selection
     * @return
     */
    public Query<T> setSelection(String selection ) {
        this.mSelection = selection;
        return this;
    }

    /**
     * 查询条件所对应的值
     * @param selectionArgs
     * @return
     */
    public Query<T> setSelectionArgs(String[] selectionArgs) {
        this.mSelectionArgs = selectionArgs;
        return this;
    }

    public Query<T> setLimit(int limit ) {
        this.mLimit = String.valueOf(limit);
        return this;
    }

    public Query<T> setColumns(String[] columns ) {
        this.mColumns = columns;
        return this;
    }

    public Query<T> setHaving(String having) {
        this.mHaving = having;
        return this;
    }

    /**
     * 排序
     * @param orderBy
     * @return
     */
    public Query<T> setOrderBy(String orderBy) {
        this.mOrderBy = orderBy;
        return this;
    }

    public Query<T> setGroupBy(String groupBy) {
        this.mGroupBy = groupBy;
        return this;
    }

    private void clear() {
        this.mColumns = null;
        this.mSelectionArgs = null;
        this.mSelection = null;
        this.mLimit = null;
        this.mOrderBy = null;
    }

    public List<T> queryAll() {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), null, null, null, null, null, null);
        return cursorToList(cursor);
    }

    public List<T> query() {
        Cursor cursor = mSqLiteDatabase.query(DaoUtil.getTableName(mClazz), mColumns, mSelection, mSelectionArgs, mGroupBy, mHaving, mOrderBy, mLimit);
        return cursorToList(cursor);
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
}
