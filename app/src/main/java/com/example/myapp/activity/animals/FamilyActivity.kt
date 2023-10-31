package com.example.myapp.activity.animals

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import android.content.pm.ActivityInfo
import android.database.sqlite.SQLiteDatabase
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import android.widget.FrameLayout
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseViewHolder
import com.example.myapp.activity.speak.Animals
import com.example.myapp.activity.speak.AnimalsDetailsAdapter
import com.example.myapp.activity.speak.Family
import com.example.myapp.activity.speak.SpeechUtils
import com.example.myapp.db.MySqliteHelper
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

class FamilyActivity : BaseActivityWithTitle(), View.OnClickListener, ExoPlayerListener {
    private var mySqliteHelper: MySqliteHelper? = null
    private var mCurrentPosition: Int = 0
    private var mExoPlayer: ExoPlayerLayout? = null
    private var mVideoView: View? = null
    private var isRoudom: Boolean = false
    private lateinit var mPagerLayoutManager: PagerLayoutManager
    private lateinit var mAdapter: AnimalsDetailsAdapter
    var chineseToSpeech: SpeechUtils? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_animals_details)
    }

    companion object {
        fun invoke(context: Context, index: Int) {
            val intent = Intent(context, FamilyActivity::class.java);
            intent.putExtra("index", index)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        mySqliteHelper = MySqliteHelper.getHelperInstance(mContext)

        titlelayout.setTitleText("家人")
        titlelayout.setOnClickListener {
            isRoudom = !isRoudom
            ToastUtils.showShort(if (isRoudom) "随机" else "定向")
        }
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
            if (isRoudom){
                val random = Random.nextInt(mAdapter.data.size)
                recyclerView.scrollToPosition(( random))
            }else{
                if (itemPosition != 0) {
                    val p = itemPosition - 1
                    recyclerView.smoothScrollToPosition(( p))
                }
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

    fun addAnim(v: View?) {
        var objectAnimator = ObjectAnimator.ofFloat(v, "scaleX", 0.5F, 1.2F, 1F)
        var objectAnimator2 = ObjectAnimator.ofFloat(v, "scaleY", 0.5F, 1.2F, 1F)
        var set = AnimatorSet()
        set.setDuration(500)
        set.interpolator = BounceInterpolator()
        set.playTogether(objectAnimator, objectAnimator2)
        set.start()
    }

    private fun getData(tableName: String) {

    }

    override fun initData() {
        var data = mutableListOf<Animals>()
        val database: SQLiteDatabase = mySqliteHelper?.readableDatabase!!
        //        database.beginTransaction();
        val cursor =
            database.query(MySqliteHelper.TABLE_NAME_USER, null, null, null, null, null, null)
        if (cursor.moveToFirst()) {
            do {
                when (MySqliteHelper.TABLE_NAME_USER) {
                    MySqliteHelper.TABLE_NAME_USER -> {
                        var f = Animals(
                            src = 0,
                            url = cursor.getString(cursor.getColumnIndex("localurl")),
                            name = cursor.getString(cursor.getColumnIndex("name"))
                        )
                        // name age height weight sex grade book like_star note
                        val nameuser = cursor.getString(cursor.getColumnIndex("name"))
                        val localurl = cursor.getString(cursor.getColumnIndex("localurl"))
                        val noteuser = cursor.getString(cursor.getColumnIndex("note"))
                        val itemuser = "$nameuser-url$localurl-$noteuser"
                        LogUtils.e(itemuser)
                        data.add(f)
                    }
                }
            } while (cursor.moveToNext())
        }
        cursor.close()
        //        database.setTransactionSuccessful();
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