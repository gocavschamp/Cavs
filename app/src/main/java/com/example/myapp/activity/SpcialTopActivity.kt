package com.example.myapp.activity

import android.os.Bundle
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import kotlinx.android.synthetic.main.activity_spcial_top.*

 class SpcialTopActivity : BaseActivityWithTitle() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_spcial_top)
    }

    override fun initData() {
        TODO("Not yet implemented")
        refreshLayout.setEnableRefresh(true)


    }
}
