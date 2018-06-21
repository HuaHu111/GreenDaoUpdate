package com.rgznworld.mygreendao.Utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.rgznworld.mygreendao.bean.User;
import com.rgznworld.mygreendao.greendao.DaoMaster;
import com.rgznworld.mygreendao.greendao.DaoSession;
import com.rgznworld.mygreendao.greendao.UserDao;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.List;

/**
 * Created by acer on 2018/4/21.
 */

public class DbMangaer {

    private final static String dbName="test_Db";
    private static DbMangaer instance;
    private Context context;
    private DbMangaer(Context context){
        this.context=context;
//        openHelper=new DaoMaster.DevOpenHelper(context,dbName);
        openHelper = new MyGreenDaoDbHelper(context,"test.db");//自定义的 OpenHelper
    }

    private MyGreenDaoDbHelper openHelper=null;

    public static DbMangaer getInstance(Context context){
        if (instance==null){
            synchronized (DbMangaer.class){
                if (instance==null){
                    instance=new DbMangaer(context);
                }
            }
        }
        return instance;
    }

    private SQLiteDatabase getwritebaldatabase(){
        if (openHelper==null){
            openHelper=new MyGreenDaoDbHelper(context,dbName);
        }
        return openHelper.getWritableDatabase();
    }

    private SQLiteDatabase getreadbaldatabase(){
        if (openHelper==null){
            openHelper=new MyGreenDaoDbHelper(context,dbName);
        }
        return openHelper.getReadableDatabase();
    }

    public void saveUser(User user) {
        DaoMaster daoMaster = new DaoMaster(getwritebaldatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.save(user);
    }

    public void saveUsers(List<User> users){
        DaoMaster daoMaster = new DaoMaster(getwritebaldatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.saveInTx(users);
    }

    public void deleteUser(User user){
        DaoMaster daoMaster = new DaoMaster(getwritebaldatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.delete(user);
    }

    public void updateUser(User user){
        DaoMaster daoMaster = new DaoMaster(getwritebaldatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        userDao.update(user);
    }

    public List<User> queryUser(){
        DaoMaster daoMaster = new DaoMaster(getreadbaldatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        List<User> list = qb.list();
        return list;
    }

    public List<User> queryUser(int age){
        DaoMaster daoMaster = new DaoMaster(getreadbaldatabase());
        DaoSession daoSession = daoMaster.newSession();
        UserDao userDao = daoSession.getUserDao();
        QueryBuilder<User> qb = userDao.queryBuilder();
        qb.where(UserDao.Properties.Age.eq(age));
        List<User> list = qb.list();
        return list;
    }

}
