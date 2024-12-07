package com.example.myapp.activity.child

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Interpolator
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapp.activity.speak.Animals
import com.example.myapp.activity.speak.AnimalsDetailsAdapter
import com.example.myapp.activity.speak.LearnAbc
import com.example.myapp.activity.speak.LearnAbcAdapter
import com.example.myapp.activity.speak.SpeechUtils
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.LogUtils
import com.nucarf.base.utils.ToastUtils
import com.nucarf.base.widget.PagerLayoutManager
import com.nucarf.base.widget.PagerLayoutManager.OnPageChangedListener
import com.nucarf.exoplayerlibrary.ui.ExoPlayerLayout
import com.nucarf.exoplayerlibrary.ui.ExoPlayerListener
import kotlinx.android.synthetic.main.activity_animals_details.*
import kotlin.random.Random
import kotlin.random.nextInt

class ChildAbcLearnActivity : BaseActivityWithTitle(), View.OnClickListener, ExoPlayerListener {
    private var isRoudom: Boolean = false
    private var mCurrentPosition: Int = 0
    private var mExoPlayer: ExoPlayerLayout? = null
    private var mVideoView: View? = null
    private lateinit var mPagerLayoutManager: PagerLayoutManager
    private lateinit var mAdapter: LearnAbcAdapter
    var chineseToSpeech: SpeechUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_details)
    }

    companion object {
        fun invoke(context: Context, index: Int) {
            val intent = Intent(context, ChildAbcLearnActivity::class.java);
            intent.putExtra("index", index)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        titlelayout.setTitleText("ABCD")
        chineseToSpeech = SpeechUtils(this)
        chineseToSpeech
        mAdapter = LearnAbcAdapter(R.layout.item_learn_abc_layout)
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
        titlelayout.setOnClickListener {
            isRoudom = !isRoudom
            ToastUtils.showShort(if (isRoudom) "随机" else "定向")
        }
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
                chineseToSpeech?.speakText(mAdapter.data.get(mCurrentPosition).name)
            }
        })
        speak.setOnClickListener {
            addAnim(speak)
            val itemPosition =
                mPagerLayoutManager.findFirstVisibleItemPosition()
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
            addAnim(last)

            val itemPosition =
                mPagerLayoutManager.findFirstCompletelyVisibleItemPosition()
            if (itemPosition != 0) {
                recyclerView.smoothScrollToPosition(itemPosition - 1)
            }
        }
        next.setOnClickListener {
            addAnim(next)
            val itemPosition =
                mPagerLayoutManager.findFirstCompletelyVisibleItemPosition()
            if (isRoudom) {
                val random = Random.nextInt(mAdapter.data.size)
                recyclerView.scrollToPosition(random)
            } else {
                if (itemPosition != mAdapter.data.size - 1) {
                    recyclerView.smoothScrollToPosition(itemPosition + 1)
                }
            }
        }
    }
    fun addAnim(v: View?){
        var  objectAnimator = ObjectAnimator.ofFloat(v,"scaleX",0.5F,1.2F,1F)
        var  objectAnimator2 = ObjectAnimator.ofFloat(v,"scaleY",0.5F,1.2F,1F)
        var set =  AnimatorSet()
        set.setDuration(500)
        set.interpolator = BounceInterpolator()
        set.playTogether(objectAnimator,objectAnimator2)
        set.start()
    }

    override fun initData() {
        var data = mutableListOf<LearnAbc>()
        data.add(LearnAbc(src = R.mipmap.mao1, url = "", video = "", name = "A"))
        data.add(
            LearnAbc(
                src = R.mipmap.mao2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/pre_post/01e2e49de73d5d0201837003824d0d1939_258.mp4",
                name = "B"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.mao3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e3f6e71f23fd6701037103867c77333f_258.mp4",
                name = "C"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.mao3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e503c94d126d64010370038a96cb5047_258.mp4",
                name = "D"
            )
        )
        data.add(LearnAbc(src = R.mipmap.mao4, url = "", video = "", name = "E"))
        data.add(LearnAbc(src = R.mipmap.gou1, url = "", video = "", name = "F"))
        data.add(
            LearnAbc(
                src = R.mipmap.gou2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e50529361420da010370038a9c2d76f5_258.mp4",
                name = "G"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.gou3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e51d7ea904595d010373038afb396ec1_258.mp4",
                name = "H"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.gou4,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e52e42ad109e5b010374038b3cb8499e_258.mp4",
                name = "I"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.gou5,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/110/01e391cd1f9ce7f90103700384f18b0809_258.mp4",
                name = "J"
            )
        )
        data.add(LearnAbc(src = R.mipmap.gou6, url = "", video = "", name = "K"))
        data.add(LearnAbc(src = 0, url = "https://sns-webpic-qc.xhscdn.com/202310311456/f7980b740dfac131135601f687d3de8f/1000g0082hn56f6gis00g5p0nguuki4hgrkivqf8!nd_whgt34_webp_wm_1", video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e49588cd3be6910103700388e8204ab7_258.mp4", name = "L"))
        data.add(LearnAbc( src = 0,url = "https://sns-webpic-qc.xhscdn.com/202310311458/e1440ec12378235592d5659d36268887/1040g00830phgjuv1mm005p435i03olh5uvd557g!nd_whgt34_webp_prv_1", video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e3e988994552000103700386483ec4ce_258.mp4", name = "M"))
        data.add(LearnAbc(src = 0,url = "https://sns-webpic-qc.xhscdn.com/202310311459/16870949c4d8495c612bcbb871c42c24/1040g00830qeefps2041g4bfden8bat9fk2r2dqo!nd_whgt34_webp_wm_1", video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4e0dd9a115121010373038a0e6302d9_258.mp4", name = "N"))
        data.add(LearnAbc(src = 0,url = "https://sns-webpic-qc.xhscdn.com/202310311502/1db52d039d5bc7d263cde1ae6b848550/1040g00830n17d1ps5ac05nkuiha099smsketss0!nd_whgt34_webp_wm_1", video = "", name = "O"))
        data.add(
            LearnAbc(
                src = R.mipmap.ji1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4b0ea13e7de27010377038953190e54_258.mp4",
                name = "P"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.ji2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e51b432d1e56e1010373038af27f555c_258.mp4",
                name = "Q"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.ji3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4a53e99457d7f0103700389257d1b67_258.mp4",
                name = "R"
            )
        )
        data.add(LearnAbc(src = R.mipmap.ji4, url = "", video = "", name = "S"))
        data.add(LearnAbc(src = R.mipmap.ji5, url = "", video = "", name = "T"))
        data.add(LearnAbc(src = R.mipmap.ya1, url = "", video = "", name = "U"))
        data.add(
            LearnAbc(
                src = R.mipmap.ya2,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e44c912a6b8f780103700387cb19ea41_258.mp4",
                name = "V"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.ya3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4e5e26d117467010370038a21fe7cb3_258.mp4",
                name = "W"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.ya3,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e44c912a6b8f780103700387cb19ea41_258.mp4",
                name = "X"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.ya4,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e481d916bb04a301037003889b39a524_258.mp4",
                name = "Y"
            )
        )
        data.add(LearnAbc(src = R.mipmap.e1, url = "", video = "", name = "Z"))
        data.add(LearnAbc(src = R.mipmap.e2, url = "", video = "", name = "ABCD"))
        data.add(LearnAbc(src = R.mipmap.e3, url = "", video = "", name = "EFG"))
        data.add(
            LearnAbc(
                src = R.mipmap.e3,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/stream/110/258/01e4682767bb3d03010373038836db4611_258.mp4",
                name = "HIJK"
            )
        )
        data.add(LearnAbc(src = R.mipmap.zhu1, url = "", video = "", name = "LMN"))
        data.add(
            LearnAbc(
                src = R.mipmap.zhu2,
                url = "",
                video = "https://sns-video-hw.xhscdn.net/pre_post/01e347fbfb7d66220183700383d1320d80_258.mp4",
                name = "OPQ"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.niu1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e50e79ae1b1a35010374038ac08c5afb_258.mp4",
                name = "RST"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.niu3,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e4aecc81e7d75001037303894ad0174e_258.mp4",
                name = "UVW"
            )
        )
        data.add(
            LearnAbc(
                src = R.mipmap.yang1,
                url = "",
                video = "https://sns-video-bd.xhscdn.com/stream/110/258/01e43b61287903ea010370038787f464b1_258.mp4",
                name = "XYZ"
            )
        )
        mAdapter.setNewData(data)
        recyclerView.scrollToPosition(intent.getIntExtra("index", 0))

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
//                    val url: String? = mAdapter.data[visibleItemPosition].video
                   val url = if (Random.nextBoolean()) "https://sns-video-al.xhscdn.com/stream/110/259/01e6e2ab7f9d3de40103700391e56f669b_259.mp4" else "https://sns-video-al.xhscdn.com/stream/110/259/01e7177f41a54ce20103700392b3cd51a8_259.mp4"
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
//        super.onBackPressed()
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