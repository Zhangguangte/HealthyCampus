package com.example.HealthyCampus.framework.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.HealthyCampus.greendao.DaoMaster;

/**
 * 数据库升级
 */

public class MySQLiteOpenHelper extends DaoMaster.OpenHelper{

    public MySQLiteOpenHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        super.onUpgrade(db, oldVersion, newVersion);
        //设置需要升级的表
//        MigrationHelper.migrate(db, ChatLogDao.class, UserDao.class, RequestListDao.class);
    }
}
