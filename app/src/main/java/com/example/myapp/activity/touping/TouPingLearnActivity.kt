package com.example.myapp.activity.touping

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.moonlight.flyvideo.R
import com.nucarf.base.ui.BaseActivityWithTitle
import com.nucarf.base.utils.ToastUtils
import com.yanbo.lib_screen.entity.RemoteItem
import com.yanbo.lib_screen.manager.ClingManager
import kotlinx.android.synthetic.main.activity_touping_learn.button
import kotlinx.android.synthetic.main.activity_touping_learn.button1
import kotlinx.android.synthetic.main.activity_touping_learn.button2
import kotlinx.android.synthetic.main.activity_touping_learn.etUrl
import kotlinx.android.synthetic.main.activity_touping_learn.recycler_view
import org.fourthline.cling.support.model.DIDLObject

class TouPingLearnActivity : BaseActivityWithTitle(), View.OnClickListener {
    private var isRoudom: Boolean = false
    private lateinit var mPagerLayoutManager: LinearLayoutManager
    var url1: String =
        "http://hc.yinyuetai.com/uploads/videos/common/44E4016521C693F23F7E9344AEBF5AF0.mp4?sc=5c4d956adf76a722&br=781&vid=3266995&aid=35&area=ML&vst=0"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_touping_learn)
    }

    companion object {
        fun invoke(context: Context, index: Int) {
            val intent = Intent(context, TouPingLearnActivity::class.java);
            intent.putExtra("index", index)
            context.startActivity(intent)
        }
    }

    override fun initView() {
        titlelayout.setTitleText("投屏")
        mPagerLayoutManager = LinearLayoutManager(mContext)
        mPagerLayoutManager.orientation = LinearLayoutManager.HORIZONTAL

        recycler_view.layoutManager = mPagerLayoutManager

        ClingManager.getInstance().startClingService()

        button.setOnClickListener(View.OnClickListener {
            DeviceListActivity.startSelf(
                this
            )
        })
        button1.setOnClickListener(View.OnClickListener {
            val itemurl1 = RemoteItem(
                "一路之下", "425703", "张杰",
                107362668, "00:04:33", "1280x720", if(etUrl.text.isEmpty())url1 else etUrl.text.toString()
            )
            ClingManager.getInstance().remoteItem = itemurl1
            MediaPlayActivity.startSelf(this)
        })
        button2.setOnClickListener(View.OnClickListener {
           seachLocal()
//            ClingManager.getInstance().localItem = itemurl1
            MediaPlayActivity.startSelf(this)
        })
    }

    //设置本地投屏的信息
    var objectList: List<DIDLObject?> = arrayListOf()

    private fun seachLocal() {


//        val itemVideo = objectList[position]
//
//        if (itemVideo is Container) {
//            //得到本地文件夹
//            val container: Container? = itemVideo as Container?
//            //点进文件夹刷新数据List<DIDLObject> objectList
//            ClingManager.getInstance().searchLocalContent(containerId)
//        } else if (itemVideo is Item) {
//            //得到本地文件
//            val item: Item? = itemVideo as Item?
//            // 设置本地投屏的信息
//            ClingManager.getInstance().localItem = item
//        }

    }

    override fun setListener() {
        titlelayout.setOnClickListener {
            ToastUtils.showShort(if (isRoudom) "随机" else "定向")
        }
    }

    override fun initData() {
    }




    override fun onBackPressed() {
        super.onBackPressed()
    }


    override fun onDestroy() {
        super.onDestroy()
    }




    override fun onClick(v: View?) {


    }
}