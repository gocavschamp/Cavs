package com.example.myapp.activity.game

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.myapp.R
import com.example.myapp.bean.SearchWayBean
import com.google.gson.Gson
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.AssetUtil
import com.nucarf.base.utils.LogUtils
import kotlinx.android.synthetic.main.activity_game.*

class GameActivity : BaseActivityWithTitle() {
    private lateinit var adapter: GameAdapter
    private var gameList: GameListBean?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        titlelayout.setTitleText("娱乐天地")
    }

    override fun initData() {

        try {
            val json_str = AssetUtil.getText(mContext, "game.text")
             gameList = Gson().fromJson(json_str, GameListBean::class.java)
        } catch (e: Exception) {
            LogUtils.e("$name--", e.message)
            return
        }
        recyclerView.layoutManager = GridLayoutManager(mContext,2,GridLayoutManager.VERTICAL,false)
        adapter =  GameAdapter(R.layout.item_game_layout);
        recyclerView.adapter = adapter
        adapter.setNewData(gameList?.game)

    }
}