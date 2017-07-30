package com.zocki.framelibrary.db;

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

}
