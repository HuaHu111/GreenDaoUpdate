package com.rgznworld.mygreendao.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.rgznworld.mygreendao.greendao.DaoMaster;
import com.rgznworld.mygreendao.greendao.UserDao;

/**
 * Created by yuanyueqing on 2018/6/20.
 */
public class DbHelper extends DaoMaster.OpenHelper {

    public DbHelper(Context context, String name) {
        super(context, name);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        if (oldVersion < newVersion) {
            Log.e("MyGreenDaoDbHelper","进行数据库升级");
            new GreenDaoCompatibleUpdateHelper()
                    .setCallBack(
                            new GreenDaoCompatibleUpdateHelper.GreenDaoCompatibleUpdateCallBack() {
                                @Override
                                public void onFinalSuccess() {
                                    Log.e("MyGreenDaoDbHelper","进行数据库升级 ===> 成功");
                                }

                                @Override
                                public void onFailedLog(String errorMsg) {
                                    Log.e("MyGreenDaoDbHelper","升级失败日志 ===> "+errorMsg);
                                }
                            }
                    )
                    .compatibleUpdate(db,UserDao.class,UserDao.class);
            Log.e("MyGreenDaoDbHelper","进行数据库升级--完成");
        }

    }
}


