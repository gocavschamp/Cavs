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

    public MySqliteHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        //生成数据库
        LogUtils.e("DatabaseHelper_onCreate");
        db.execSQL(JsVersion);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        //更新数据库
        LogUtils.e("DatabaseHelper_onUpgrade");
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
        return null;
    }

}
