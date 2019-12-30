package com.example.HealthyCampus.common.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.HealthyCampus.greendao.DaoMaster;
import com.example.HealthyCampus.greendao.DaoSession;

/**
 * 数据库初始化的辅助类
 */

public class GreenDaoHelper {

    private Context context;


    public GreenDaoHelper(Context context) {
        this.context = context;
    }

    public DaoSession initDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(context, "health", null);
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        return daoMaster.newSession();
    }
}
