package com.zocki.db.library.tablemanager;

import android.database.sqlite.SQLiteDatabase;

import com.zocki.db.library.PserInitialize;

import java.util.List;
/**
 * Created by kaisheng3 on 2017/8/22.
 * 更新
 */
public class Upgradetor {

    public static void updata(SQLiteDatabase db, int oldVersion, int newVersion) {
        List<TabModel> tabModels = PserInitialize.getTabModels();
        int N = tabModels.size();

        // 创建临时表
        for( int i = 0; i < N; i++ ) {
            TabModel tabModel = tabModels.get(i);
            TableCreateManager.createTempTable(db,tabModel.getTableClass());
        }

        // 创建新表
        for( int i = 0; i < N; i++ ) {
            TabModel tabModel = tabModels.get(i);
            TableCreateManager.createTable(db,tabModel.getTableClass());
        }

        // 拷贝临时表数据到新表
        for( int i = 0; i < N; i++ ) {
            TabModel tabModel = tabModels.get(i);
            TableCreateManager.tempInsertTable(db,tabModel.getTableClass());
        }

        // 删除临时表
        for( int i = 0; i < N; i++ ) {
            TabModel tabModel = tabModels.get(i);
            TableCreateManager.dropTempTable(db,tabModel.getTableClass());
        }
    }
}
