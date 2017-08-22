package com.zocki.db.library.utils;

import android.text.TextUtils;

import com.zocki.db.library.annotation.Column;

import java.util.Locale;

/**
 * Created by Administrator on 2017/7/30 0030.
 */

public class DaoUtil {

    public static String getTableName(Class<?> clazz) {
        return clazz.getSimpleName();
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
