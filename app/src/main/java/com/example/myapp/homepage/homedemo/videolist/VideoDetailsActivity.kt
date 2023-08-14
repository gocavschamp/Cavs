package com.example.myapp.homepage.homedemo.videolist

import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.content.res.Configuration
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.RelativeLayout
import butterknife.BindView
import butterknife.ButterKnife
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.input.input
import com.chad.library.adapter.base.BaseQuickAdapter.RequestLoadMoreListener
import com.example.loadingbox.LoadingBox
import com.example.myapp.homepage.homedemo.videolist.VideoListData.ResponseBean.VideosBean
import com.example.myapp.mvp.BaseMvpActivity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gyf.barlibrary.ImmersionBar
import com.moonlight.flyvideo.R
import com.nucarf.base.utils.AssetUtil
import com.nucarf.base.utils.BaseAppCache
import com.nucarf.base.utils.LogUtils
import com.nucarf.base.utils.ToastUtils
import com.nucarf.base.widget.TitleLayout
import com.nucarf.exoplayerlibrary.ui.ExoPlayerLayout
import com.nucarf.exoplayerlibrary.ui.ExoPlayerListener
import kotlinx.android.synthetic.main.activity_video_details.*

class VideoDetailsActivity : BaseMvpActivity<VideoListPresenter?>(), VideoListContract.View,
    ExoPlayerListener, View.OnClickListener, RequestLoadMoreListener {
//    @JvmField
//    @BindView(R.id.titlelayout)
//    var titlelayout: TitleLayout? = null
//
//    @JvmField
//    @BindView(R.id.fl_content)
//    var flContent: RelativeLayout? = null
    private val mPlayTime: Long = 0
    private var mExoPlayer: ExoPlayerLayout? = null
    private var mVideoView: View? = null
    private var mCurrentPosition = -1
    private var loadingBox: LoadingBox? = null
    private var videoUrl = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_details)
        ButterKnife.bind(this)
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(titlelayout)
            .statusBarColor(R.color.white).init()
    }

    var videoData: VideoListData? = null
    override fun initInject() {
//        getActivityComponent().inject(this);
        mPresenter = VideoListPresenter()
        videoUrl = intent.getStringExtra("videoUrl")
        try {
            val json_str = AssetUtil.getText(BaseAppCache.getContext(), "video.text")
            videoData = Gson().fromJson(json_str, object : TypeToken<VideoListData?>() {}.type)
        } catch (e: Exception) {
            LogUtils.e("\$name--", e.message)
        }
    }

    override fun initData() {
        initView()
        titlelayout!!.setRightClickListener(View.OnClickListener {
            MaterialDialog(this, ModalDialog)
                .show {
                input(prefill = ""+videoUrl) { dialog, text ->
                    ToastUtils.showShort(videoUrl)
                    // Text submitted with the action button
                    videoUrl = text.toString();
                    startPlay(0,mVideoView)
                }

                positiveButton(null,"submit")
                    negativeButton(null,"cancel")
            }
        })
    }

    private fun initView() {
        titlelayout!!.setLeftClickListener(View.OnClickListener { view: View? -> finish() })
        loadingBox = LoadingBox(this, fl_content)
        loadingBox!!.setExceptionBackgroundColor(Color.GRAY)
        loadingBox!!.setClickListener(View.OnClickListener { v: View? -> mPresenter!!.loadData(true) })
        mVideoView = View.inflate(mContext, R.layout.video_layout, null)
        fl_content!!.addView(mVideoView)
        if (mExoPlayer != null) {
            mExoPlayer!!.onPause()
            mExoPlayer!!.releasePlayer()
            mExoPlayer = null
        }
        mExoPlayer = mVideoView!!.findViewById<View>(R.id.exoPlayer) as ExoPlayerLayout
        val mConverTrans = mVideoView!!.findViewById<View>(R.id.conver_trans)
        mConverTrans.setOnClickListener(this)
        mConverTrans.visibility = View.GONE
        mExoPlayer!!.setExoPlayerListener(this)
        mExoPlayer!!.setOnClickListener(this)
        mExoPlayer!!.releasePlayer()
        mExoPlayer!!.showFirstLoading(true)
        fl_content!!.visibility = View.VISIBLE
        startPlay(0, mVideoView)
    }

    override fun onNetError(errorCode: Int, errorMsg: String) {
        loadingBox!!.showInternetErrorLayout()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }

    private fun startPlay(visibleItemPosition: Int, mVideoView: View?) {
        stopPlay()
        if (mExoPlayer != null) {
            mExoPlayer!!.play(videoUrl, null, null, false, mPlayTime, true)
            mCurrentPosition = visibleItemPosition
        }
    }

    private fun stopPlay() {
        releasPlayer()
    }

    override fun onBackPressed() {
        if (requestedOrientation == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            doVerticalScreen()
            return
        }
        stopPlay()
        super.onBackPressed()
    }

    override fun onDestroy() {
        mExoPlayer = null
        super.onDestroy()
    }

    private fun releasPlayer() {
        if (null != mExoPlayer) {
            mExoPlayer!!.onStop()
            mExoPlayer!!.releasePlayer()
        }
    }

    override fun onSucess() {}
    override fun onReLoad() {}
    override fun setData(isRefresh: Boolean, data: List<VideosBean>, isEnd: Boolean) {}
    override fun doHorizontalScreen() {
        LogUtils.e("video--doHorizontalScreen")
        //横屏全屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        if (mExoPlayer != null) {
            mExoPlayer!!.pause()
            val parent = mVideoView!!.parent
            if (parent is FrameLayout) {
                parent.removeView(mVideoView)
            }
            fl_content!!.visibility = View.VISIBLE
            fl_content!!.removeAllViews()
            fl_content!!.addView(mVideoView)
            mExoPlayer!!.start()
        }
    }

    override fun doVerticalScreen() {
        LogUtils.e("video--doVerticalScreen")
        //竖屏全屏
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        if (mExoPlayer != null) {
            mExoPlayer!!.pause()
            val parent = mVideoView!!.parent
            if (parent is FrameLayout) {
                parent.removeView(mVideoView)
            }
            fl_content!!.visibility = View.VISIBLE
            fl_content!!.removeAllViews()
            fl_content!!.addView(mVideoView)
            mExoPlayer!!.start()
        }
    }

    override fun setIsDisableDraggableView(b: Boolean) {
        LogUtils.e("video--setIsDisableDraggableView")
    }

    override fun playerVideoPrepared() {
        LogUtils.e("video--playerVideoPrepared")
    }

    override fun playerVideoFinish() {
        LogUtils.e("video--playerVideoFinish")
        stopPlay()
    }

    override fun playerVideoDuration(pDuration: Long) {}
    override fun onClickADLayout(url: String) {}
    override fun onClickADVideo(pADVideoUrl: String) {}
    override fun onClickGoodsItem(pGoodsId: String) {}
    override fun setViewHistory(longTime: Long) {
        LogUtils.e("video--setViewHistory")
    }

    override fun onClick(v: View) {
        LogUtils.e("video--onClick" + v.id)
    }

    override fun onLoadMoreRequested() {
        if (null != mPresenter) {
            mPresenter!!.loadData(false)
        }
    }

    companion object {
        fun launcher(context: Context, videoUrl: String?) {
            val intent = Intent(context, VideoDetailsActivity::class.java)
            intent.putExtra("videoUrl", videoUrl)
            context.startActivity(intent)
        }
    }
}