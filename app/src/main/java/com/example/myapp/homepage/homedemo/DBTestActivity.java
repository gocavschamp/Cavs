package com.example.myapp.homepage.homedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.example.myapp.db.MySqliteHelper;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.ToastUtils;

import java.util.ArrayList;
import java.util.List;

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
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.btn_hide_show)
    Button btnHideShow;
    @BindView(R.id.btn_query_student)
    Button btnQueryStudent;
    @BindView(R.id.btn_query_book)
    Button btnQueryBook;
    @BindView(R.id.btn_query_player)
    Button btnQueryPlayer;
    private MySqliteHelper mySqliteHelper;
    private DataShowAdapter mAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dbtest);
        ButterKnife.bind(this);
        titlelayout.setTitleText("database test");
    }

    @Override
    protected void initData() {
        mySqliteHelper = MySqliteHelper.getHelperInstance(mContext);
        recycleview.setLayoutManager(new LinearLayoutManager(mContext, RecyclerView.VERTICAL, false));
        mAdapter = new DataShowAdapter(R.layout.db_test_item);
        recycleview.setAdapter(mAdapter);
        getData(MySqliteHelper.TABLE_NAME_STUDENT);
    }

    @OnClick({R.id.btn_add_student, R.id.btn_hide_show, R.id.btn_delete_student, R.id.btn_add_book, R.id.btn_delete_book,
            R.id.btn_add_player, R.id.btn_delete_player, R.id.btn_execute_sql, R.id.btn_sqlite_upgrate,
            R.id.btn_query_student, R.id.btn_query_book, R.id.btn_query_player})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.btn_add_student:
                addStudent();
                break;
            case R.id.btn_hide_show:
                rlContent.setVisibility(rlContent.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
                break;
            case R.id.btn_delete_student:
                // delete from Orders where Id = 7
                deleteStudent();
                break;
            case R.id.btn_add_book:
                addBook();
                break;
            case R.id.btn_delete_book:
                break;
            case R.id.btn_add_player:
                addPlayer();
                break;
            case R.id.btn_delete_player:
                break;
            case R.id.btn_execute_sql:
                /**
                 * SQL
                 * --->
                 * Add
                 * 1> db.execSQL("INSERT INTO widgets (name, inventory)"+ "VALUES ('Sprocket', 5)");
                 * database.execSQL("insert into t_student (name,age,note) values ('bob','jasting biber','')");
                 * 2> ContentValues cv=new ContentValues();
                 * cv.put(Constants.TITLE, "example title");
                 * cv.put(Constants.VALUE, SensorManager.GRAVITY_DEATH_STAR_I);
                 * db.insert("mytable", getNullColumnHack(), cv);
                 * <----
                 * delete
                 * ---->
                 * 1> "delete from Orders where Id = 7"
                 * 2> database.delete(MySqliteHelper.TABLE_NAME_STUDENT, "id=?", new String[]{"1"});
                 *   db.delete("Book", "price < ?", new String[]{"100"});
                 *  3> delete FROM
                 *  db.execSQL("delete from person");
                 *   //设置id从1开始（sqlite默认id从1开始），若没有这一句，id将会延续删除之前的id
                 *   db.execSQL("update sqlite_sequence  set seq=0 where name='person'");
                 * <----
                 * update
                 * ---->
                 *   1> Database.execSQL("update student set name='mary key' where id=1");
                 *    2> ContentValues values = new ContentValues();
                 *    values.put("price", 188);
                 *  // 更新所有的 name = ?，其中 ? 是一个占位符，最后的值取决于第四个参数
                 *  db.update("Book", values, "name = ?", new String[]{"fxxk the Android"});
                 * <----
                 * query
                 * ----->
                 * Cursor c=db.rawQuery(
                 *     "SELECT name FROM sqlite_master WHERE type='table' AND name='mytable'",
                 * Cursor cursor = sqliteDatabase.query("student", new String[]{"id","name"}, "id=?", new String[]{"1"}, null, null, null);
                 * <-----
                 *
                 *
                 */
                if (!TextUtils.isEmpty(etSql.getText().toString())) {
                    String sql = etSql.getText().toString();
                    SQLiteDatabase database = mySqliteHelper.getWritableDatabase();
                    database.beginTransaction();
//                    database.execSQL("insert into t_student (name,age,height,weight,sex,grade,book,like_star,note) values ('bob',17,172,61,0,70,'herry pote','jasting biber','')");
                    try {
                        database.execSQL(sql);
                        database.setTransactionSuccessful();
                        ToastUtils.show_middle(mContext, "execute sql succeed", 1);
                    } catch (Exception e) {
                        e.printStackTrace();
                        ToastUtils.show_middle(mContext, "grammar  mistakes  please check again", 1);
                    } finally {
                        database.endTransaction();
                    }
                } else {
                    ToastUtils.show_middle(mContext, "please input right sql--", 1);
                }
                break;
            case R.id.btn_sqlite_upgrate:
                break;
            case R.id.btn_query_student:
                getData(MySqliteHelper.TABLE_NAME_STUDENT);

                break;
            case R.id.btn_query_book:
                getData(MySqliteHelper.TABLE_NAME_LIBRARY);

                break;
            case R.id.btn_query_player:
                getData(MySqliteHelper.TABLE_NAME_NBA);

                break;
        }
    }


    /**
     * 数据库中创建一张student表
     * name age height weight sex grade book like_star note
     */
    private String[][] students = {{"李学习", "12", "155", "50", "1", "87", "西游记", "James", "good"},
            {"王爱国", "14", "185", "70", "0", "99", "红楼梦", "Kobe", "very good"},
            {"张爱党", "15", "165", "60", "1", "67", "金瓶梅", "Jordan", "best"}};
    private int stu_index = 0;

    private void addStudent() {
        SQLiteDatabase database = mySqliteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", students[stu_index % 3][0] + stu_index);
        values.put("age", Integer.parseInt(students[stu_index % 3][1]));
        values.put("height", Integer.parseInt(students[stu_index % 3][2]));
        values.put("weight", Integer.parseInt(students[stu_index % 3][3]));
        values.put("sex", Integer.parseInt(students[stu_index % 3][4]));
        values.put("grade", Integer.parseInt(students[stu_index % 3][5]));
        values.put("book", students[stu_index % 3][6]);
        values.put("like_star", students[stu_index % 3][7]);
        values.put("note", students[stu_index % 3][8]);
        database.insert(MySqliteHelper.TABLE_NAME_STUDENT, null, values);
//        database.execSQL("insert into t_student (name,age,height,weight,sex,grade,book,like_star,note) values ('bob',17,172,61,0,70,'herry pote','jasting biber','')");
        stu_index++;
        getData(MySqliteHelper.TABLE_NAME_STUDENT);

    }

    private void deleteStudent() {

        // delete from Orders where Id = 7
        // 这里都条件筛选很灵活，不仅仅可以是 XX = ?，还可以是XX > ?，XX < ?，甚至是XX > ? and YY = ?，不过这样，第三个参数里面，就需要2个值了。
        SQLiteDatabase database = mySqliteHelper.getWritableDatabase();
        database.delete(MySqliteHelper.TABLE_NAME_STUDENT, "id=?", new String[]{"1"});
        getData(MySqliteHelper.TABLE_NAME_STUDENT);

    }

    /**
     * 数据库中创建一张library表
     * book_name price author pages borrow_to note
     */
    private String[][] books = {{"西游记", "100", "施耐庵", "500", "李学习", "孙悟空nb!!"},
            {"红楼梦", "150", "曹雪芹", "800", "王爱国", "贾宝玉垃圾!!"},
            {"金瓶梅", "190", "兰陵笑笑生", "200", "张爱党", "潘金莲真美!!"}};
    private int book_index = 0;

    private void addBook() {
        SQLiteDatabase database = mySqliteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("book_name", books[book_index % 3][0] + book_index);
        values.put("price", Double.parseDouble(books[book_index % 3][1]));
        values.put("author", books[book_index % 3][2]);
        values.put("pages", Integer.parseInt(books[book_index % 3][3]));
        values.put("borrow_to", books[book_index % 3][4]);
        values.put("note", books[book_index % 3][5]);
        database.insert(MySqliteHelper.TABLE_NAME_LIBRARY, null, values);
        book_index++;
        getData(MySqliteHelper.TABLE_NAME_LIBRARY);

    }

    /**
     * 数据库中创建一张nba表
     * name age height weight score rebound assists  team note
     */
    private String[][] players = {{"James", "36", "206", "105", "25.3", "8.7", "10.3", "Lackers", "湖人总冠军"},
            {"Kobe", "42", "198", "90", "25.5", "5.5", "4.8", "Lackers", "rest in the peace"},
            {"Jordan", "55", "198", "93", "30.3", "6.1", "5.7", "Bulls", "篮球之神"}};
    private int player_index = 0;

    private void addPlayer() {
        SQLiteDatabase database = mySqliteHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", players[player_index % 3][0] + player_index);
        values.put("age", Integer.parseInt(players[player_index % 3][1]));
        values.put("height", Integer.parseInt(players[player_index % 3][2]));
        values.put("weight", Integer.parseInt(players[player_index % 3][3]));
        values.put("score", Double.parseDouble(players[player_index % 3][4]));
        values.put("rebound", Double.parseDouble(players[player_index % 3][5]));
        values.put("assists", Double.parseDouble(players[player_index % 3][6]));
        values.put("team", players[player_index % 3][7]);
        values.put("note", players[player_index % 3][8]);
        database.insert(MySqliteHelper.TABLE_NAME_NBA, null, values);
        player_index++;
        getData(MySqliteHelper.TABLE_NAME_NBA);
    }

    private void getData(String tableName) {
        SQLiteDatabase database = mySqliteHelper.getReadableDatabase();
//        database.beginTransaction();
        Cursor cursor = database.query(tableName, null, null, null, null, null, null);
        List<String> data = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                switch (tableName) {
                    case MySqliteHelper.TABLE_NAME_STUDENT:
                        // name age height weight sex grade book like_star note
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        int age = cursor.getInt(cursor.getColumnIndex("age"));
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        int height = cursor.getInt(cursor.getColumnIndex("height"));
                        int weight = cursor.getInt(cursor.getColumnIndex("weight"));
                        int sex = cursor.getInt(cursor.getColumnIndex("sex"));
                        int grade = cursor.getInt(cursor.getColumnIndex("grade"));
                        String book = cursor.getString(cursor.getColumnIndex("book"));
                        String like_star = cursor.getString(cursor.getColumnIndex("like_star"));
                        String note = cursor.getString(cursor.getColumnIndex("note"));
                        String item = name + "-id" + id + "-age" + age + "-" + height + "-" + weight + "-sex" + sex + "-grade" + grade + "-" + book + "-" + like_star + "-" + note;
                        data.add(item);
                        LogUtils.e(item);

                        break;
                    case MySqliteHelper.TABLE_NAME_LIBRARY:
                        // book_name price author pages borrow_to note
                        String book_name = cursor.getString(cursor.getColumnIndex("book_name"));
                        double price = cursor.getDouble(cursor.getColumnIndex("price"));
                        int pages = cursor.getInt(cursor.getColumnIndex("pages"));
                        int id1 = cursor.getInt(cursor.getColumnIndex("id"));
                        String author = cursor.getString(cursor.getColumnIndex("author"));
                        String borrow_to = cursor.getString(cursor.getColumnIndex("borrow_to"));
                        String note1 = cursor.getString(cursor.getColumnIndex("note"));
                        String item1 = book_name + "-id" + id1 + "-price" + price + "-pages" + pages + "-" + author + "-borrow_to" + borrow_to + "-" + note1;
                        data.add(item1);
                        LogUtils.e(item1);

                        break;
                    case MySqliteHelper.TABLE_NAME_NBA:
                        //name age height weight score rebound assists  team note
                        String name1 = cursor.getString(cursor.getColumnIndex("name"));
                        int age1 = cursor.getInt(cursor.getColumnIndex("age"));
                        int id2 = cursor.getInt(cursor.getColumnIndex("id"));
                        int height1 = cursor.getInt(cursor.getColumnIndex("height"));
                        int weight1 = cursor.getInt(cursor.getColumnIndex("weight"));
                        double score = cursor.getDouble(cursor.getColumnIndex("score"));
                        double rebound = cursor.getDouble(cursor.getColumnIndex("rebound"));
                        double assists = cursor.getDouble(cursor.getColumnIndex("assists"));
                        String team = cursor.getString(cursor.getColumnIndex("team"));
                        String note2 = cursor.getString(cursor.getColumnIndex("note"));
                        String item2 = name1 + "-id" + id2 + "-age" + age1 + "-" + height1 + "-" + weight1 + "-score" + score + "-rebound" + rebound + "-assists" + assists + "-" + team + "-" + note2;
                        data.add(item2);
                        LogUtils.e(item2);

                        break;
                }

            } while (cursor.moveToNext());
        }
        cursor.close();
//        database.setTransactionSuccessful();
        mAdapter.setNewData(data);
    }


    private class DataShowAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public DataShowAdapter(int layout) {
            super(layout);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_info, item);
        }
    }
}
