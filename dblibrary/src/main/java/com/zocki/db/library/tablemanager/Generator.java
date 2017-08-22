package com.zocki.db.library.tablemanager;

import android.database.sqlite.SQLiteDatabase;

import com.zocki.baselibrary.logger.LogUtils;
import com.zocki.db.library.PserInitialize;

import java.util.List;

/**
 * Created by kaisheng3 on 2017/8/21.
 * 表的创建
 */
public class Generator {

    public static void create(SQLiteDatabase sqLiteDatabase) {
        List<TabModel> tabModels = PserInitialize.getTabModels();
        int N = tabModels.size();

        if( N <= 0 ) {
            LogUtils.e( "tables size is null" );
            return ;
        }

        for (int i = 0; i < N; i++ ){
            TableCreateManager.createTable(sqLiteDatabase,tabModels.get(i).getTableClass());
        }
    }
}
