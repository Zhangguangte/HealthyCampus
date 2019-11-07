package com.example.HealthyCampus.framework.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.HealthyCampus.greendao.DaoMaster;
import com.example.HealthyCampus.greendao.DaoSession;

/**
 * 数据库初始化的辅助类
 */

public class GreenDaoHelper {

    Context context;
    DaoMaster.DevOpenHelper helper;
    SQLiteDatabase db;
    DaoMaster daoMaster;
    DaoSession daoSession;


    public GreenDaoHelper(Context context) {
        this.context = context;
    }

    public DaoSession initDao() {
        helper = new DaoMaster.DevOpenHelper(context, "test", null);
        db = helper.getWritableDatabase();
        daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
        return daoSession;
    }
}
