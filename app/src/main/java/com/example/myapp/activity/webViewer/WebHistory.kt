package com.example.myapp.activity.webViewer

/**
 * @Description TODO
 * @Author ${ yuwenming }
 * @Date 2021/10/11 15:22
 *   " (id integer primary key autoincrement," + "title text," + "url text," + "nickname text,"
+ "note text," + "iscollect integer,"
+ "team text)";
 */
class WebHistory(
        val id:Int?,
        val title:String?="",
        val url:String?="",
        val nickname:String?="",
        val note:String?="",
        val team:String?="",
        val iscollect:Int?
)
