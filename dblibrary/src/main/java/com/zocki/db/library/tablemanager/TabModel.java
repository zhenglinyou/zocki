package com.zocki.db.library.tablemanager;

/**
 * Created by kaisheng3 on 2017/8/21.
 * 表的管理类
 */
public class TabModel {
    private String tableName;

    private String className;

    private Class tableClass;

    public TabModel(String tableName, String className, Class tableClass) {
        this.tableName = tableName;
        this.className = className;
        this.tableClass = tableClass;
    }

    public TabModel(Class tableClass) {
        this.tableClass = tableClass;
        this.tableName = tableClass.getSimpleName();
    }

    public Class getTableClass() {
        return tableClass;
    }

    public String getTableName() {
        return tableName;
    }

    public String getClassName() {
        return className;
    }
}
