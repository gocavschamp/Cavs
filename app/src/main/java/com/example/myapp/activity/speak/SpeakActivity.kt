package com.example.myapp.activity.speak

import android.os.Bundle
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.KeyboardUtil
import kotlinx.android.synthetic.main.activity_speak.*

class SpeakActivity : BaseActivityWithTitle() {
    var chineseToSpeech: SpeechUtils?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speak)
         chineseToSpeech = SpeechUtils(this)

    }

    override fun initData() {
        btn_speak.setOnClickListener {
            chineseToSpeech?.speakText(et_input.text.toString())
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        chineseToSpeech?.shutdown()
        KeyboardUtil.hideInput(et_input,this)
    }
}