package com.example.myapp.activity.animals

import android.content.pm.ActivityInfo
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapp.activity.speak.Animals
import com.example.myapp.activity.speak.AnimalsDetailsAdapter
import com.example.myapp.activity.speak.SpeechUtils
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.LogUtils
import com.nucarf.base.widget.PagerLayoutManager
import com.nucarf.base.widget.PagerLayoutManager.OnPageChangedListener
import com.nucarf.exoplayerlibrary.ui.ExoPlayerLayout
import com.nucarf.exoplayerlibrary.ui.ExoPlayerListener
import kotlinx.android.synthetic.main.activity_animals_details.*

class AnimalsItemActivity : BaseActivityWithTitle(), View.OnClickListener, ExoPlayerListener {
    private var mCurrentPosition: Int = 0
    private  var mExoPlayer: ExoPlayerLayout?=null
    private var mVideoView: View? = null
    private lateinit var mPagerLayoutManager: PagerLayoutManager
    private lateinit var mAdapter: AnimalsDetailsAdapter
    var chineseToSpeech: SpeechUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_details)
    }

    override fun initView() {
        chineseToSpeech = SpeechUtils(this)
        mAdapter = AnimalsDetailsAdapter(R.layout.item_animals_details_item)
        mPagerLayoutManager = PagerLayoutManager(mContext)
        mPagerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recyclerView.layoutManager = mPagerLayoutManager
        recyclerView.adapter = mAdapter
        mVideoView = View.inflate(mContext, R.layout.video_layout, null)
//        flContent.addView(mVideoView);
        //        flContent.addView(mVideoView);
        if (mExoPlayer != null) {
            mExoPlayer?.onPause()
            mExoPlayer?.releasePlayer()
        }

        mExoPlayer = mVideoView?.findViewById<View>(R.id.exoPlayer) as ExoPlayerLayout
        val mConverTrans: View = mVideoView?.findViewById<View>(R.id.conver_trans) as View
        mConverTrans.setOnClickListener(this)
        mConverTrans.visibility = View.GONE

        mExoPlayer?.setExoPlayerListener(this)
        mExoPlayer?.setOnClickListener(this)
        mExoPlayer?.releasePlayer()
        mExoPlayer?.showFirstLoading(true)
        fl_content.visibility = View.GONE

    }

    override fun setListener() {
        mPagerLayoutManager.setOnPageChangedListener(object : OnPageChangedListener {
            override fun onPageInitComplete() {
                val position = mPagerLayoutManager.findFirstVisibleItemPosition()
                if (position != -1) {
                    mCurrentPosition = position
                }
            }

            override fun onPageRelease(position: Int, isNext: Boolean) {
                if (mCurrentPosition == position) {
                    stopPlay()
                }
            }

            override fun onPageSelected(position: Int, isLast: Boolean) {
                if (mCurrentPosition == position) {
                    return
                }
                mCurrentPosition = position
            }
        })
        speak.setOnClickListener {
            val itemPosition =
                mPagerLayoutManager.findFirstCompletelyVisibleItemPosition()
            chineseToSpeech?.speakText(mAdapter.data.get(itemPosition).name)
        }
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.play -> {
                    startPlay(position, mVideoView!!)

                }
            }
        }
        last.setOnClickListener {
            val itemPosition =
                mPagerLayoutManager.findFirstCompletelyVisibleItemPosition()
            if (itemPosition != 0) {
                recyclerView.smoothScrollToPosition(itemPosition - 1)
            }
        }
        next.setOnClickListener {
            val itemPosition =
                mPagerLayoutManager.findFirstCompletelyVisibleItemPosition()
            if (itemPosition != mAdapter.data.size - 1) {
                recyclerView.smoothScrollToPosition(itemPosition + 1)
            }
        }
    }

    override fun initData() {
        var data  = mutableListOf<Animals>()
        data.add(Animals(src = R.mipmap.mao1, url = "", video = "",name="猫咪"))
        data.add(Animals(src = R.mipmap.mao2, url = "", video = "",name="猫猫"))
        data.add(Animals(src = R.mipmap.mao3, url = "", video = "",name="小猫"))
        data.add(Animals(src = R.mipmap.mao4, url = "", video = "",name="喵喵~"))
        data.add(Animals(src = R.mipmap.gou1, url = "", video = "",name="狗狗"))
        data.add(Animals(src = R.mipmap.gou2, url = "", video = "",name="汪汪"))
        data.add(Animals(src = R.mipmap.gou3, url = "", video = "",name="小狗"))
        data.add(Animals(src = R.mipmap.gou4, url = "", video = "",name="汪汪汪"))
        data.add(Animals(src = R.mipmap.gou5, url = "", video = "",name="大狗"))
        data.add(Animals(src = R.mipmap.gou6, url = "", video = "",name="狗狗"))
        data.add(Animals(src = R.mipmap.ji1, url = "", video = "",name="小鸡"))
        data.add(Animals(src = R.mipmap.ji2, url = "", video = "",name="小鸡鸡"))
        data.add(Animals(src = R.mipmap.ji3, url = "", video = "",name="公鸡"))
        data.add(Animals(src = R.mipmap.ji4, url = "", video = "",name="母鸡"))
        data.add(Animals(src = R.mipmap.ji5, url = "", video = "",name="疙瘩"))
        data.add(Animals(src = R.mipmap.ya1, url = "", video = "",name="鸭子"))
        data.add(Animals(src = R.mipmap.ya2, url = "", video = "",name="小鸭子"))
        data.add(Animals(src = R.mipmap.ya3, url = "", video = "",name="鸭鸭"))
        data.add(Animals(src = R.mipmap.ya3, url = "", video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e44c912a6b8f780103700387cb19ea41_258.mp4",name="鸭鸭"))
        data.add(Animals(src = R.mipmap.ya4, url = "", video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e481d916bb04a301037003889b39a524_258.mp4",name="嘎嘎"))
        data.add(Animals(src = R.mipmap.e1, url = "", video = "",name="鹅"))
        data.add(Animals(src = R.mipmap.e2, url = "", video = "",name="鹅鹅"))
        data.add(Animals(src = R.mipmap.e3, url = "", video = "",name="嘎嘎"))
        data.add(Animals(src = R.mipmap.e3, url = "", video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e4682767bb3d03010373038836db4611_258.mp4",name="嘎嘎"))
        data.add(Animals(src = R.mipmap.zhu1, url = "", video = "",name="猪"))
        data.add(Animals(src = R.mipmap.zhu2, url = "", video = "",name="哼哼"))
        data.add(Animals(src = R.mipmap.zhu2, url = "", video = "https://sns-video-hw.xhscdn.net/pre_post/01e347fbfb7d66220183700383d1320d80_258.mp4",name="哼哼"))
        data.add(Animals(src = R.mipmap.niu1, url = "", video = "",name="牛"))
        data.add(Animals(src = R.mipmap.niu2, url = "", video = "",name="牛牛"))
        data.add(Animals(src = R.mipmap.niu3, url = "", video = "",name="奶牛"))
        data.add(Animals(src = R.mipmap.yang1, url = "", video = "",name="小羊"))
        data.add(Animals(src = R.mipmap.yang2, url = "", video = "",name="咩咩"))
        data.add(Animals(src = R.mipmap.yang2, url = "", video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e3f3644145593e01037103866ec58904_258.mp4",name="咩咩"))
        data.add(Animals(src = R.mipmap.yang3, url = "", video = "",name="洋洋"))
        data.add(Animals(src = R.mipmap.hu1, url = "", video = "",name="虎"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "",name="老虎"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "",name="老虎"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "",name="老虎"))
        data.add(Animals(src = R.mipmap.hu3, url = "", video = "",name="大老虎"))
        data.add(Animals(src = R.mipmap.shizi1, url = "", video = "",name="狮子"))
        data.add(Animals(src = R.mipmap.shizi2, url = "", video = "",name="大狮子"))
        data.add(Animals(src = R.mipmap.shizi3, url = "", video = "",name="狮"))
        data.add(Animals(src = R.mipmap.daxiang1, url = "", video = "",name="大象"))
        data.add(Animals(src = R.mipmap.daxiang2, url = "", video = "",name="像"))
        data.add(Animals(src = R.mipmap.daxiang3, url = "", video = "",name="大象"))
        data.add(Animals(src = R.mipmap.chong1, url = "", video = "",name="虫"))
        data.add(Animals(src = R.mipmap.chong2, url = "", video = "",name="虫子"))
        data.add(Animals(src = R.mipmap.hou1, url = "", video = "",name="猴"))
        data.add(Animals(src = R.mipmap.hou2, url = "", video = "",name="猴子"))
        data.add(Animals(src = R.mipmap.hou3, url = "", video = "",name="金丝猴"))
        data.add(Animals(src = R.mipmap.hu1, url = "", video = "",name="蝴蝶"))
        data.add(Animals(src = R.mipmap.hu2, url = "", video = "",name="蝴蝶"))
        data.add(Animals(src = R.mipmap.hu3, url = "", video = "",name="蝴蝶"))
        mAdapter.setNewData(data)

    }

    private val mPlayTime: Long = 0

    private fun startPlay(visibleItemPosition: Int, mVideoView: View) {
        if (mCurrentPosition != visibleItemPosition) {
            stopPlay()
        }
        if (visibleItemPosition != -1 && !mExoPlayer?.isPlaying!!) {
            val viewHolder =
                recyclerView.findViewHolderForLayoutPosition(visibleItemPosition) as BaseViewHolder
            val parent = mVideoView.parent
            if (parent is FrameLayout) {
                (parent as ViewGroup).removeView(mVideoView)
            }
            if (viewHolder != null) {
                val mVideoContent = viewHolder.getView<FrameLayout>(R.id.fl_content_item)
                mVideoContent.addView(mVideoView, 0)
                if (mExoPlayer != null) {
                    val url: String? = mAdapter.data[visibleItemPosition].video
                    mExoPlayer?.play(url, null, null, false, mPlayTime, true)
                    mCurrentPosition = visibleItemPosition
                }
            }
        }
    }

    private fun stopPlay() {
        releasPlayer()
        val parent = mVideoView!!.parent
        if (parent is FrameLayout) {
            parent.removeView(mVideoView)
        }
    }

    override fun onBackPressed() {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            doVerticalScreen()
            return
        }
        stopPlay()
        super.onBackPressed()
    }

    private fun releasPlayer() {
        if (null != mExoPlayer) {
            mExoPlayer?.onStop()
            mExoPlayer?.releasePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        chineseToSpeech?.shutdown()
    }

    override fun doHorizontalScreen() {
        LogUtils.e("video--doHorizontalScreen")
        //横屏全屏
        //横屏全屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if (mExoPlayer != null) {
            mExoPlayer?.pause()
            val parent = mVideoView!!.parent
            if (parent is FrameLayout) {
                parent.removeView(mVideoView)
            }
            fl_content.visibility = View.VISIBLE
            fl_content.addView(mVideoView)
            mExoPlayer?.start()
        }
    }

    override fun doVerticalScreen() {
        //竖屏全屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (mExoPlayer != null) {
            mExoPlayer?.pause()
            fl_content.visibility = View.GONE
            fl_content.removeAllViews()
            val viewHolder =
                recyclerView.findViewHolderForLayoutPosition(mCurrentPosition) as BaseViewHolder
            val parent = mVideoView!!.parent
            if (parent is FrameLayout) {
                (parent as ViewGroup).removeView(mVideoView)
            }
            if (viewHolder != null) {
                val mVideoContent = viewHolder.getView<FrameLayout>(R.id.fl_content_item)
                mVideoContent.addView(mVideoView, 0)
                mExoPlayer?.start()
            }
        }
    }

    override fun setIsDisableDraggableView(b: Boolean) {
    }

    override fun playerVideoPrepared() {
    }

    override fun playerVideoFinish() {
        stopPlay()
    }

    override fun playerVideoDuration(pDuration: Long) {
    }

    override fun onClickADLayout(url: String?) {
    }

    override fun onClickADVideo(pADVideoUrl: String?) {
    }

    override fun onClickGoodsItem(pGoodsId: String?) {
    }

    override fun setViewHistory(longTime: Long) {
    }

    override fun onClick(v: View?) {


    }
}