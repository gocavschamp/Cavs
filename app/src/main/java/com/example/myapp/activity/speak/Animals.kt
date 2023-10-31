package com.example.myapp.activity.speak

/**
 *@auther yuwenming
 *@createTime：2023/10/26 15:20
 *@desc:
 **/
open class Animals(
    open var src: Int,
    open var url: String? = null,
    open var video: String? = null,
    open var id: Int = 0,
    open var name: String? = null
)
open class Family(
    override var src: Int,
    override var url: String? = null,
    override var video: String? = null,
    override var id: Int = 0,
    override var name: String? = null
) : Animals(src, url, video, id, name) {}
