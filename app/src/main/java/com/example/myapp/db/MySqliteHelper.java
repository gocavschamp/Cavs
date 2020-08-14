package com.example.myapp.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
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
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "happy.db";
    public static final String TABLE_NAME_STUDENT = "t_student";
    public static final String TABLE_NAME_LIBRARY = "t_library";
    public static final String TABLE_NAME_NBA = "t_nba";

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

    /*私有的静态对象，为整个应用程序提供一个sqlite操作的静态实例，
     * 并保证只能通过下面的静态方法getHelper(Context context)获得，
     * 防止使用时绕过同步方法改变它*/
    private static MySqliteHelper instance; //这里主要解决死锁问题,是static就能解决死锁问题

    //----------数据库操作---------->
    public static MySqliteHelper getHelperInstance(Context context) {
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

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
        LogUtils.e("DatabaseHelper_onUpgrade");
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
