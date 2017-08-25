package com.zocki.db.library.utils;

import android.text.TextUtils;

import com.zocki.db.library.annotation.Column;
import com.zocki.db.library.annotation.Extend;
import com.zocki.db.library.annotation.TableName;
import com.zocki.db.library.annotation.attr.ColumnAttr;

import java.lang.reflect.Field;
import java.util.Locale;

import static com.zocki.db.library.tablemanager.TableCreateManager.filterName;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class DaoUtil {

    /**
     * 获取表名
     * @param clazz
     * @return
     */
    public static String getTableName(Class<?> clazz) {
        TableName tableName = clazz.getAnnotation(TableName.class);
        if( tableName != null ) {
            String name = tableName.name();
            if( !TextUtils.isEmpty(name) ) return name;
        }
        return clazz.getSimpleName();
    }

    public static ColumnAttr getColumnAttr(Field columnField) {

        // Android Studio 自带
        if( columnField.isSynthetic() ) return null;

        columnField.setAccessible(true);

        String _columnName = columnField.getName();
        // 热修复或者其它的关键字
        if( filterName(_columnName) ) return null;

        // 扩展字段
        if( columnField.getAnnotation(Extend.class) != null ) return null;

        ColumnAttr columnAttr = new ColumnAttr();

        boolean _notNull = false;
        boolean _unique = false;
        String _defaultValue = null;

        // type 需要进行转换 int --> integer String --> text
        String _columnType = getColumnType ( columnField.getType().getSimpleName() );


        Column columnAnotation = columnField.getAnnotation(Column.class);

        if( columnAnotation != null ) {
            if( !TextUtils.isEmpty(columnAnotation.columnName()) ) {
                _columnName = columnAnotation.columnName();
            }
            _notNull = columnAnotation.notNull();
            _unique = columnAnotation.unique();
            _defaultValue = columnAnotation.defaultValue();
        }

        columnAttr.setColumnType( _columnType );
        columnAttr.setColumnName( _columnName );
        columnAttr.setNotNull( _notNull );
        columnAttr.setUnique( _unique );
        columnAttr.setDefaultValue( _defaultValue );

        return columnAttr;
    }

    public static String getColumnType(String type) {
        if( type.contains("String") ) {
            return "text";
        } else if( type.contains("int")) {
            return "integer";
        } else if( type.contains("boolean") ) {
            return "boolean";
        } else if( type.contains("float") ) {
            return "float";
        } else if( type.contains("double") ) {
            return "double";
        } else if( type.contains("char") ) {
            return "varchar";
        } else if(type.contains("long") ) {
            return "long";
        }
        return null;
    }

    public static String capitalize(String string) {
        if( !TextUtils.isEmpty(string) ) {
            return string.substring(0,1).toUpperCase(Locale.US) + string.substring(1);
        }
        return null;
    }
}
