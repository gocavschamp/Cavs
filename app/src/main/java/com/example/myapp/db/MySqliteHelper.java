package com.example.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.myapp.bean.FileVersionBean;
import com.nucarf.base.utils.LogUtils;

public class MySqliteHelper extends SQLiteOpenHelper {
    public static final String TABLE_NAME = "JsVersion";
    private static final String VALUE_V = "v";
    private static final String VALUE_CV = "cv";
    private static final String VALUE_NATIVE = "is_native";
    private static final String VALUE_ID = "id";
    //数据库中创建一张VERSION表
    public static final String JsVersion = "create table JsVersion ("
            + "id integer primary key autoincrement," + "v text,"
            + "cv text," + "is_native integer)";

    //<--------数据库操作---------
    //数据库版本
    private static final int DB_VERSION = 5;
    private static final String DB_NAME = "happy.db";
    public static final String TABLE_NAME_STUDENT = "t_student";
    public static final String TABLE_NAME_LIBRARY = "t_library";
    public static final String TABLE_NAME_NBA = "t_nba";
    public static final String TABLE_NAME_WEB = "t_web_history";
    public static final String TABLE_NAME_LABEL = "t_web_label";

    /**
     * 数据库中创建一张student表
     * name age height weight sex grade note  book like_star note
     */
    public static final String CREAT_STUDENT = "create table " + TABLE_NAME_STUDENT +
            " (id integer primary key autoincrement," + "name text,"
            + "age integer," + "height integer," + "grade integer," + "note text," + "like_star text,"
            + "weight integer," + "book text," + "sex integer)";

    /**
     * 数据库中创建一张library表
     * book_name price author pages borrow_to note
     */
    public static final String CREAT_LIBRARY = "create table " + TABLE_NAME_LIBRARY +
            " (id integer primary key autoincrement," + "book_name text,"
            + "price real," + "author text," + "pages integer," + "note text,"
            + "borrow_to text)";

    /**
     * 数据库中创建一张nba表
     * name age height weight score rebound assists  team note
     */
    public static final String CREAT_NBA = "create table " + TABLE_NAME_NBA +
            " (id integer primary key autoincrement," + "name text," + "age integer," + "weight integer,"
            + "height integer," + "score real," + "rebound real," + "assists real," + "note text,"
            + "team text)";
    /**
     * 数据库中创建一张WEB记录表
     * name age height weight score rebound assists  team note
     */
    public static final String CREAT_WEB = "create table " + TABLE_NAME_WEB +
            " (id integer primary key autoincrement," + "title text," + "url text," + "nickname text,"
             + "note text," + "iscollect integer,"
            + "team text)";
    /**
     * 数据库中创建一张WEB 书签表
     * name age height weight score rebound assists  team note
     */
    public static final String CREAT_LABEL = "create table " + TABLE_NAME_LABEL +
            " (id integer primary key autoincrement," + "title text," + "url text," + "nickname text,"
             + "note text," + "iscollect integer,"
            + "team text)";

    /*私有的静态对象，为整个应用程序提供一个sqlite操作的静态实例，
     * 并保证只能通过下面的静态方法getHelper(Context context)获得，
     * 防止使用时绕过同步方法改变它*/
    private static MySqliteHelper instance; //这里主要解决死锁问题,是static就能解决死锁问题

    //----------数据库操作---------->
    public synchronized static MySqliteHelper getHelperInstance(Context context) {
        if (instance == null) {
            instance = new MySqliteHelper(context);
        }
        return instance;
    }

    /**
     * 私有的构造函数，只能自己使用，防止绕过同步方法生成多个实例，
     *
     * @param context
     */
    private MySqliteHelper(@Nullable Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //生成数据库
        LogUtils.e("DatabaseHelper_onCreate");
        db.execSQL(JsVersion);
        db.execSQL(CREAT_STUDENT);
        db.execSQL(CREAT_LIBRARY);
        db.execSQL(CREAT_NBA);
        db.execSQL(CREAT_WEB);
        db.execSQL(CREAT_LABEL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
        LogUtils.e("DatabaseHelper_onUpgrade version" + newVersion+"--old--"+oldVersion);
        //构建删除表的SQL
//        String sql = "DROP TABLE IF EXISTS " + TABLE_NAME_ART;
        switch (oldVersion) {
            case 1:
                try {
                    String alert_nba_name_temp = "alter TABLE t_nba rename to t_nba_temp";
                    String create_new_nba_table = "create table t_nba (id integer primary key autoincrement," +
                            " name text, score real,rebound real,assists real,block real,steal real,team text,note text) ";
                    String copy_data = "insert into t_nba select id ,name ,score ,rebound ,assists,'','',team,note from t_nba_temp";
                    String drop_old_table = "drop table t_nba_temp";
                    db.beginTransaction();
                    db.execSQL(alert_nba_name_temp);
                    db.execSQL(create_new_nba_table);
                    db.execSQL(copy_data);
                    db.execSQL(drop_old_table);
                    db.setTransactionSuccessful();
                    LogUtils.e("DatabaseHelper_onUpgrade ---seccessful");

                } catch (SQLException e) {
                    e.printStackTrace();
                } finally {
                    db.endTransaction();
                }
            case 2:
                    try {
                        String alert_student = "alter table t_student add nickname text";
                        String add_nick_name = "update  t_student set nickname = '--Choice 1--' where age = 12";
                        db.beginTransaction();
                        db.execSQL(alert_student);
                        db.execSQL(add_nick_name);
                        db.setTransactionSuccessful();
                        LogUtils.e("DatabaseHelper_onUpgrade ---seccessful");
                    } catch (SQLException e) {
                        e.printStackTrace();
                    } finally {
                        db.endTransaction();
                    }
            case 5:

                break;
            default:
        }

    }

    @Override
    public void onOpen(SQLiteDatabase db) {
        super.onOpen(db);
    }

    /**
     * 添加数据
     *
     * @param model 数据模型
     * @return 返回添加数据有木有成功
     */
    public FileVersionBean addVersionDataReturnID(FileVersionBean model) {
        //把数据添加到ContentValues
        ContentValues values = new ContentValues();
        values.put(VALUE_V, model.getV());
        values.put(VALUE_CV, model.getCv());
        values.put(VALUE_ID, 1);
        values.put(VALUE_NATIVE, model.getIs_native());

        //添加数据到数据库
        long index = getWritableDatabase().insert(TABLE_NAME, null, values);

        if (index != -1) {
            model.setId(index);
            return model;
        } else {
            return null;
        }
    }

    /**
     * 方法修改数据库数据
     */
    public void updateVersionnData(FileVersionBean model) {

        //将数据添加至ContentValues
        ContentValues values = new ContentValues();
        values.put(VALUE_V, model.getV());
        values.put(VALUE_CV, model.getCv());
        values.put(VALUE_NATIVE, model.getIs_native());
        values.put(VALUE_ID, model.getId());

        //修改model的数据
        getWritableDatabase().update(TABLE_NAME, values, VALUE_ID + "=?", new String[]{"" + 1});
    }

    /**
     * 查询全部数据
     */
    public FileVersionBean queryAllVersionData() {

        //查询全部数据
        Cursor cursor = getWritableDatabase().query(TABLE_NAME, null, null, null, null, null, null, null);
        //移动到首位
        if (cursor.moveToFirst()) {
            int id = cursor.getInt(cursor.getColumnIndex(VALUE_ID));
            String v = cursor.getString(cursor.getColumnIndex(VALUE_V));
            String cv = cursor.getString(cursor.getColumnIndex(VALUE_CV));
            int is_native = cursor.getInt(cursor.getColumnIndex(VALUE_NATIVE));
            FileVersionBean model = new FileVersionBean();
            model.setId(id);
            model.setV(v);
            model.setCv(cv);
            model.setIs_native(is_native);
            return model;
        }
        cursor.close();
        return null;
    }

}
