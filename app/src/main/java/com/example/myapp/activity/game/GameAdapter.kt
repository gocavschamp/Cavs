package com.example.myapp.activity.game

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapp.R
import kotlinx.android.synthetic.main.item_game_layout.view.*

/**
 * @Description TODO
 * @Author ${ yuwenming }
 * @Date 2021/3/25 13:42
 */
class GameAdapter(layoutResId: Int) : BaseQuickAdapter<GameBean, BaseViewHolder>(layoutResId) {
    override fun convert(helper: BaseViewHolder?, item: GameBean?) {

        helper?.setText(R.id.tv_name,item?.name)

    }


}