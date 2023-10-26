package com.example.myapp.activity.animals

import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.example.myapp.activity.WebStationAdapter
import com.example.myapp.activity.speak.Animals
import com.example.myapp.activity.speak.AnimalsListAdapter
import com.example.myapp.activity.speak.SpeechUtils
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.KeyboardUtil
import com.nucarf.base.utils.UiGoto
import kotlinx.android.synthetic.main.activity_animals_list.*

class AnimalsListActivity : BaseActivityWithTitle() {
    private lateinit var mAdapter: AnimalsListAdapter
    var chineseToSpeech: SpeechUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_list)
    }

    override fun initView() {
        chineseToSpeech = SpeechUtils(this)
        mAdapter = AnimalsListAdapter(R.layout.item_animals_item)
        recyclerView.layoutManager = GridLayoutManager(mContext, 2)
        recyclerView.adapter = mAdapter
    }
    override fun setListener() {
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            chineseToSpeech?.speakText(mAdapter.data[position].name)
        }
        mAdapter.setOnItemClickListener { adapter, view, position ->
            UiGoto.startAty(mContext, AnimalsItemActivity::class.java)

        }
    }

    override fun initData() {
        var data = mutableListOf<Animals>()
        data.add(Animals(src = R.mipmap.mao1, url = "", video = "", name = "猫咪"))
        data.add(Animals(src = R.mipmap.mao2, url = "", video = "", name = "猫猫"))
        data.add(Animals(src = R.mipmap.mao3, url = "", video = "", name = "小猫"))
        data.add(Animals(src = R.mipmap.mao4, url = "", video = "", name = "喵喵~"))
        data.add(Animals(src = R.mipmap.gou1, url = "", video = "", name = "狗狗"))
        data.add(Animals(src = R.mipmap.gou2, url = "", video = "", name = "汪汪"))
        data.add(Animals(src = R.mipmap.gou3, url = "", video = "", name = "小狗"))
        data.add(Animals(src = R.mipmap.gou4, url = "", video = "", name = "汪汪汪"))
        data.add(Animals(src = R.mipmap.gou5, url = "", video = "", name = "大狗"))
        data.add(Animals(src = R.mipmap.gou6, url = "", video = "", name = "狗狗"))
        data.add(Animals(src = R.mipmap.ji1, url = "", video = "", name = "小鸡"))
        data.add(Animals(src = R.mipmap.ji2, url = "", video = "", name = "小鸡鸡"))
        data.add(Animals(src = R.mipmap.ji3, url = "", video = "", name = "公鸡"))
        data.add(Animals(src = R.mipmap.ji4, url = "", video = "", name = "母鸡"))
        data.add(Animals(src = R.mipmap.ji5, url = "", video = "", name = "疙瘩"))
        data.add(Animals(src = R.mipmap.ya1, url = "", video = "", name = "鸭子"))
        data.add(Animals(src = R.mipmap.ya2, url = "", video = "", name = "小鸭子"))
        data.add(Animals(src = R.mipmap.ya3, url = "", video = "", name = "鸭鸭"))
        data.add(
            Animals(
                src = R.mipmap.ya3,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e44c912a6b8f780103700387cb19ea41_258.mp4",
                name = "鸭鸭"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.ya4,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e481d916bb04a301037003889b39a524_258.mp4",
                name = "嘎嘎"
            )
        )
        data.add(Animals(src = R.mipmap.e1, url = "", video = "", name = "鹅"))
        data.add(Animals(src = R.mipmap.e2, url = "", video = "", name = "鹅鹅"))
        data.add(Animals(src = R.mipmap.e3, url = "", video = "", name = "嘎嘎"))
        data.add(
            Animals(
                src = R.mipmap.e3,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e4682767bb3d03010373038836db4611_258.mp4",
                name = "嘎嘎"
            )
        )
        data.add(Animals(src = R.mipmap.zhu1, url = "", video = "", name = "猪"))
        data.add(Animals(src = R.mipmap.zhu2, url = "", video = "", name = "哼哼"))
        data.add(
            Animals(
                src = R.mipmap.zhu2,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/pre_post/01e347fbfb7d66220183700383d1320d80_258.mp4",
                name = "哼哼"
            )
        )
        data.add(Animals(src = R.mipmap.niu1, url = "", video = "", name = "牛"))
        data.add(Animals(src = R.mipmap.niu2, url = "", video = "", name = "牛牛"))
        data.add(Animals(src = R.mipmap.niu3, url = "", video = "", name = "奶牛"))
        data.add(Animals(src = R.mipmap.yang1, url = "", video = "", name = "小羊"))
        data.add(Animals(src = R.mipmap.yang2, url = "", video = "", name = "咩咩"))
        data.add(
            Animals(
                src = R.mipmap.yang2,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e3f3644145593e01037103866ec58904_258.mp4",
                name = "咩咩"
            )
        )
        data.add(Animals(src = R.mipmap.yang3, url = "", video = "", name = "洋洋"))
        data.add(Animals(src = R.mipmap.hu1, url = "", video = "", name = "虎"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "", name = "老虎"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "", name = "老虎"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "", name = "老虎"))
        data.add(Animals(src = R.mipmap.hu3, url = "", video = "", name = "大老虎"))
        data.add(Animals(src = R.mipmap.shizi1, url = "", video = "", name = "狮子"))
        data.add(Animals(src = R.mipmap.shizi2, url = "", video = "", name = "大狮子"))
        data.add(Animals(src = R.mipmap.shizi3, url = "", video = "", name = "狮"))
        data.add(Animals(src = R.mipmap.daxiang1, url = "", video = "", name = "大象"))
        data.add(Animals(src = R.mipmap.daxiang2, url = "", video = "", name = "像"))
        data.add(Animals(src = R.mipmap.daxiang3, url = "", video = "", name = "大象"))
        data.add(Animals(src = R.mipmap.chong1, url = "", video = "", name = "虫"))
        data.add(Animals(src = R.mipmap.chong2, url = "", video = "", name = "虫子"))
        data.add(Animals(src = R.mipmap.hou1, url = "", video = "", name = "猴"))
        data.add(Animals(src = R.mipmap.hou2, url = "", video = "", name = "猴子"))
        data.add(Animals(src = R.mipmap.hou3, url = "", video = "", name = "金丝猴"))
        data.add(Animals(src = R.mipmap.hu1, url = "", video = "", name = "蝴蝶"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "", name = "蝴蝶"))
        data.add(Animals(src = R.mipmap.hu3, url = "", video = "", name = "蝴蝶"))
        mAdapter.setNewData(data)

    }


    override fun onDestroy() {
        super.onDestroy()
        chineseToSpeech?.shutdown()
    }
}