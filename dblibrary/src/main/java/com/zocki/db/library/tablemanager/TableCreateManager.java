package com.zocki.db.library.tablemanager;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.annotation.attr.ColumnAttr;
import com.zocki.db.library.utils.DaoUtil;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by kaisheng3 on 2017/8/22.
 * 表的创建、删除、临时表，管理
 */

public class TableCreateManager {

    private static String getCreateTabelSql(Class mClazz){
        StringBuffer sql = new StringBuffer();

        sql.append("CREATE TABLE IF NOT EXISTS ")
                .append(DaoUtil.getTableName(mClazz))
                .append("(ID INTEGER PRIMARY KEY AUTOINCREMENT, ");

        // Person2(ID INTEGER PRIMARY KEY AUTOINCREMENT, aaa integer NOT NULL , bbb integer , Pser integer , ttt integer )

        // 反射 获取变量名称
        for (Field field : mClazz.getDeclaredFields()) {

            ColumnAttr columnAttr = DaoUtil.getColumnAttr(field);
            if( columnAttr == null ) continue;

            sql.append( columnAttr.getColumnName() ).append(" ").append( columnAttr.getColumnType() );
            if( columnAttr.isNotNull() ) sql.append(" NOT NULL");
            if( columnAttr.isUnique() ) sql.append(" UNIQUE");
            if( !TextUtils.isEmpty( columnAttr.getDefaultValue() ) ) sql.append(" default ").append(columnAttr.getDefaultValue());
            sql.append(", ");
        }

        sql.replace(sql.length() - 2, sql.length(),")");

        return sql.toString();
    }

    private static String tableColumnToStr(String[] tableColumes) {
        if(  tableColumes == null || tableColumes.length == 0 ) return null;
        StringBuilder builder = new StringBuilder();
        for( int i = 0; i < tableColumes.length; i++ ) {
            builder.append(tableColumes[i]);
            if( i + 1 != tableColumes.length ) {
                builder.append(",");
            }
        }
        return builder.toString();
    }

    private static final String TABLE_COLUME_SQL = "SELECT * FROM %s WHERE ID='0'";

    private static String[] getTableColumn(SQLiteDatabase db, String tableName) {
        Cursor cursor = db.rawQuery(String.format(TABLE_COLUME_SQL, tableName), null);
        if(cursor != null) {
            String[] tableColumes = cursor.getColumnNames();
            cursor.close();
            return tableColumes;
        }
        return null;
    }

    /***************删除临时表****************/

    private static final String DROP_TABLE = "DROP TABLE _TEMP_%s";

    public static void dropTempTable(SQLiteDatabase db,Class mClazz) {
        String tableName = DaoUtil.getTableName(mClazz);
        String dropTableSql = String.format(DROP_TABLE,tableName);
        db.execSQL(dropTableSql);
    }

    /***************临时备份表中的数据copy到新创建的数据库表中****************/

    private static final String INSERT_DATA = "INSERT INTO %s(%s) SELECT %s FROM _TEMP_%s";

    public static void tempInsertTable(SQLiteDatabase db,Class mClazz) {
        String tableName = DaoUtil.getTableName(mClazz);

        String[] tableColumn_New = getTableColumn(db, String.valueOf(tableName));
        String[] tableColumn_Old = getTableColumn(db, String.valueOf("_TEMP_" + tableName));

        String formatValues =  tableColumnToStr(getTableColumnRetain(tableColumn_New,tableColumn_Old));

        String insertSql = String.format(INSERT_DATA,tableName,formatValues,formatValues,tableName);

        db.execSQL(insertSql);
    }

    private static String[] getTableColumnRetain(String[] tableColumn_New,String[] tableColumn_Old) {
        Set<String> retainColumnSet = new HashSet<>();
        retainColumnSet.addAll(Arrays.asList(tableColumn_New));
        retainColumnSet.retainAll(Arrays.asList(tableColumn_Old));
        return retainColumnSet.toArray(new String[]{});
    }

    /*******************************创建表************************************/

    public static void createTable(SQLiteDatabase db,Class mClazz) {
        String createTabelSql = getCreateTabelSql(mClazz);
        // LogUtils.e(createTabelSql);
        db.execSQL(createTabelSql);
    }

    /******************************临时表的创建******************************/

    private static final String CREATE_TEMP_SQL = "ALTER TABLE %S RENAME TO _TEMP_%s";
    // 临时表的创建
    public static void createTempTable(SQLiteDatabase db,Class mClazz) {
        String tableName = DaoUtil.getTableName(mClazz);
        String createTempSql = String.format(CREATE_TEMP_SQL,tableName,tableName);
        db.execSQL(createTempSql);
    }

    public static boolean filterName(String name) {
        return name.equals("serialVersionUID");
    }
}
