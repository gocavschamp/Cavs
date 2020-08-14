package com.example.myapp.homepage.homedemo;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.myapp.R;
import com.example.myapp.db.MySqliteHelper;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.ToastUtils;

import org.greenrobot.greendao.database.DatabaseOpenHelper;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class DBTestActivity extends BaseActivityWithTitle {

    @BindView(R.id.btn_add_student)
    Button btnAddStudent;
    @BindView(R.id.btn_delete_student)
    Button btnDeleteStudent;
    @BindView(R.id.btn_add_book)
    Button btnAddBook;
    @BindView(R.id.btn_delete_book)
    Button btnDeleteBook;
    @BindView(R.id.btn_add_player)
    Button btnAddPlayer;
    @BindView(R.id.btn_delete_player)
    Button btnDeletePlayer;
    @BindView(R.id.btn_execute_sql)
    Button btnExecuteSql;
    @BindView(R.id.btn_sqlite_upgrate)
    Button btnSqliteUpgrate;
    @BindView(R.id.et_sql)
    EditText etSql;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        ButterKnife.bind(this);
        setTitle("database test");
    }

    @Override
    protected void initData() {

    }

    @OnClick({R.id.btn_add_student, R.id.btn_delete_student, R.id.btn_add_book, R.id.btn_delete_book, R.id.btn_add_player, R.id.btn_delete_player, R.id.btn_execute_sql, R.id.btn_sqlite_upgrate})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_student:
                addStudent();
                break;
            case R.id.btn_delete_student:
                break;
            case R.id.btn_add_book:
                break;
            case R.id.btn_delete_book:
                break;
            case R.id.btn_add_player:
                break;
            case R.id.btn_delete_player:
                break;
            case R.id.btn_execute_sql:
                if(!TextUtils.isEmpty(etSql.getText().toString())) {
                    String sql = etSql.getText().toString();
                }else {
                    ToastUtils.show_middle(mContext,"plase input right sql--",0);
                }
                break;
            case R.id.btn_sqlite_upgrate:
                break;
        }
    }
    /**
     * 数据库中创建一张student表
     * name age height weight sex grade note  book like_star note
     */
    private void addStudent() {
        MySqliteHelper sqliteHelper = MySqliteHelper.getHelperInstance(mContext);
        SQLiteDatabase database = sqliteHelper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
//        values.put();
    }
    /**
     * 数据库中创建一张library表
     * book_name price author pages borrow_to note
     */
    private void addBook() {
        MySqliteHelper sqliteHelper = MySqliteHelper.getHelperInstance(mContext);
        SQLiteDatabase database = sqliteHelper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
//        values.put();
    }
    /**
     * 数据库中创建一张nba表
     * name age height weight score rebound assists  team note
     */
    private void addPlayer() {
        MySqliteHelper sqliteHelper = MySqliteHelper.getHelperInstance(mContext);
        SQLiteDatabase database = sqliteHelper.getWritableDatabase();
        database.beginTransaction();
        ContentValues values = new ContentValues();
//        values.put();
    }
}
