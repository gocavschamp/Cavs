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

    override fun onBackPressed() {

    }
    override fun initView() {
        titlelayout.setTitleText("动物合集")
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
            AnimalsItemActivity.Companion.invoke(mContext,position)
        }
    }

    override fun initData() {
        var data = mutableListOf<Animals>()
        data.add(Animals(src = R.mipmap.mao1, url = "", video = "", name = "猫咪"))
        data.add(
            Animals(
                src = R.mipmap.mao2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/pre_post/01e2e49de73d5d0201837003824d0d1939_258.mp4",
                name = "猫猫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.mao3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e3f6e71f23fd6701037103867c77333f_258.mp4",
                name = "小猫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.mao3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e503c94d126d64010370038a96cb5047_258.mp4",
                name = "小猫"
            )
        )
        data.add(Animals(src = R.mipmap.mao4, url = "", video = "", name = "喵喵~"))
        data.add(Animals(src = R.mipmap.gou1, url = "", video = "", name = "狗狗"))
        data.add(
            Animals(
                src = R.mipmap.gou2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e50529361420da010370038a9c2d76f5_258.mp4",
                name = "汪汪"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.gou3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e51d7ea904595d010373038afb396ec1_258.mp4",
                name = "小狗"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.gou4,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e52e42ad109e5b010374038b3cb8499e_258.mp4",
                name = "汪汪汪"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.gou5,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/110/01e391cd1f9ce7f90103700384f18b0809_258.mp4",
                name = "大狗"
            )
        )
        data.add(Animals(src = R.mipmap.gou6, url = "", video = "", name = "狗狗"))
        data.add(
            Animals(
                src = R.mipmap.ji1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4b0ea13e7de27010377038953190e54_258.mp4",
                name = "小鸡"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.ji2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e51b432d1e56e1010373038af27f555c_258.mp4",
                name = "小鸡鸡"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.ji3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4a53e99457d7f0103700389257d1b67_258.mp4",
                name = "公鸡"
            )
        )
        data.add(Animals(src = R.mipmap.ji4, url = "", video = "", name = "母鸡"))
        data.add(Animals(src = R.mipmap.ji5, url = "", video = "", name = "疙瘩"))
        data.add(Animals(src = R.mipmap.ya1, url = "", video = "", name = "鸭子"))
        data.add(
            Animals(
                src = R.mipmap.ya2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e44c912a6b8f780103700387cb19ea41_258.mp4",
                name = "小鸭子"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.ya3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4e5e26d117467010370038a21fe7cb3_258.mp4",
                name = "鸭鸭"
            )
        )
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
        data.add(
            Animals(
                src = R.mipmap.zhu2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4740b50bb0ba90103720388654d5a86_258.mp4",
                name = "哼哼"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.zhu2,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/pre_post/01e347fbfb7d66220183700383d1320d80_258.mp4",
                name = "哼哼"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.niu1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e50e79ae1b1a35010374038ac08c5afb_258.mp4",
                name = "牛"
            )
        )
        data.add(Animals(src = R.mipmap.niu2, url = "", video = "", name = "牛牛"))
        data.add(
            Animals(
                src = R.mipmap.niu3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4aecc81e7d75001037303894ad0174e_258.mp4",
                name = "奶牛"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.yang1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e43b61287903ea010370038787f464b1_258.mp4",
                name = "小羊"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.yang2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e481d3aabe60f001037003889b24226c_258.mp4",
                name = "咩咩"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.yang2,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e3f3644145593e01037103866ec58904_258.mp4",
                name = "咩咩"
            )
        )
        data.add(Animals(src = R.mipmap.yang3, url = "", video = "", name = "洋洋"))
        data.add(
            Animals(
                src = R.mipmap.dxm1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e53473e20aae56010370038b54e550fa_258.mp4",
                name = "熊猫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.dxm2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e532a46013b5c6010373038b4dd2a884_258.mp4",
                name = "大熊猫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.dxm3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e53600f90b4065010377038b5c90d312_258.mp4",
                name = "猫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.dxm4,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e53600f90b4065010377038b5c90d312_258.mp4",
                name = "猫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hu1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e470359bb9edb6010371038856556ceb_258.mp4",
                name = "虎"
            )
        )
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "", name = "老虎"))
        data.add(
            Animals(
                src = R.mipmap.hu2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/01e219bf2863e931018370037f3493278a_258.mp4",
                name = "老虎"
            )
        )
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "", name = "老虎"))
        data.add(Animals(src = R.mipmap.hu3, url = "", video = "", name = "大老虎"))
        data.add(
            Animals(
                src = R.mipmap.shizi1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e423076c20b2a2010370038728d6393c_258.mp4",
                name = "狮子"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.shizi2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4cda9e8e8083f0103700389c3631455_258.mp4",
                name = "大狮子"
            )
        )
        data.add(Animals(src = R.mipmap.shizi3, url = "", video = "", name = "狮"))
        data.add(
            Animals(
                src = R.mipmap.daxiang1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4c5c55b46f1490103710389a48c4602_258.mp4",
                name = "大象"
            )
        )
        data.add(Animals(src = R.mipmap.daxiang2, url = "", video = "", name = "象"))
        data.add(
            Animals(
                src = R.mipmap.daxiang3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e3db6c254574c3010370038ad0f81533_258.mp4",
                name = "大象"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.chong1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4abc0e5e8384e01037703893ee9f634_258.mp4",
                name = "虫"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.chong2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4d3710ca96ccf0103730389d9f2f9e3_258.mp4",
                name = "虫子"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hou1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4af7c53456f1a01037303894d7e1087_258.mp4",
                name = "猴"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hou2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4a9396746df550103700389350aeb6a_258.mp4",
                name = "猴子"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hou3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4d39249a738230103730389da81e6a3_258.mp4",
                name = "金丝猴"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hudie1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/pre_post/01e2b1d0263d7feb010370038186962add_258.mp4",
                name = "蝴蝶"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hudie2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e49b872475437c0103700388ff892808_258.mp4",
                name = "蝴蝶"
            )
        )
        data.add(
            Animals(
                src = R.mipmap.hudie3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e41eeb51b64852010370038718c775e4_258.mp4",
                name = "蝴蝶"
            )
        )
        mAdapter.setNewData(data)

    }


    override fun onDestroy() {
        super.onDestroy()
        chineseToSpeech?.shutdown()
    }
}