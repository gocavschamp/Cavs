package com.example.myapp.activity

import android.text.InputFilter
import com.example.myapp.R
import com.example.myapp.utils.MoneyTextWatcher
import com.example.myapp.utils.MoneyValueFilter
import com.nucarf.base.ui.BaseActivityWithTitle2
import kotlinx.android.synthetic.main.activity_edit_input.*

/**
 *@auther yuwenming
 *@createTime：2023/5/17 18:08
 *@desc:
 **/
class EditInputActivity: BaseActivityWithTitle2() {
    override fun getLayoutResId(): Int {

        return R.layout.activity_edit_input
    }

    override fun initView() {
    }

    override fun initListener() {
        etInput.addTextChangedListener( MoneyTextWatcher(etInput).setDigits(3))
        etInput2.filters = arrayOf(MoneyValueFilter().setDigits(3))
    }

    override fun initData() {
    }
}