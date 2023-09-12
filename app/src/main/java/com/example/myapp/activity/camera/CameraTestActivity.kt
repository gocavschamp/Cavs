package com.example.myapp.activity.camera

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.hardware.Camera
import android.hardware.Camera.AutoFocusCallback
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.SurfaceHolder
import androidx.appcompat.app.AppCompatActivity
import com.gyf.barlibrary.ImmersionBar
import com.moonlight.flyvideo.R
import com.nucarf.base.utils.ImageUtil
import kotlinx.android.synthetic.main.activity_camera_test.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 *@auther yuwenming
 *@createTime：2023/9/12 14:26
 *@desc:
 **/
class CameraTestActivity : AppCompatActivity() {

    private var mCamera: Camera? = null

    private var mCameraRange: Int = 90

    private lateinit var mSurfaceHolder: SurfaceHolder

    private var screenWidth: Int = 0

    private var screenHeight: Int = 0

    private var previewWidth: Int = 0

    private var previewHeight: Int = 0

    private var screenGY: Int = 0

    private var TAKE_PICTURE_PATH: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_camera_test)

        initView()

        initClick()
    }

    private fun initView() {
        // 设置沉浸式
        ImmersionBar.with(this).init()

        // 获取屏幕尺寸以获取最佳的相机预览尺寸
        getScreenSize()

        mSurfaceHolder = surface_view.holder
        mSurfaceHolder.addCallback(object : SurfaceHolder.Callback {
            override fun surfaceCreated(holder: SurfaceHolder) {
                mCamera = getCustomCamera()
                val parameters = mCamera?.parameters;
                parameters?.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
                parameters?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
                mCamera?.parameters = parameters
            }

            override fun surfaceChanged(holder: SurfaceHolder, p1: Int, p2: Int, p3: Int) {
//                mCamera?.parameters?.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
//                mCamera?.autoFocus(AutoFocusCallback { success, camera -> })
                mCamera?.let {
//                    var parameters = it.parameters
                    getPreviewSize(it.parameters)
                    it.parameters.setPictureSize(previewWidth, previewHeight)
                    // 设置自动对焦模式
//                    it.parameters.focusMode = Camera.Parameters.FOCUS_MODE_AUTO
//                    it.parameters.focusMode = Camera.Parameters.FOCUS_MODE_CONTINUOUS_PICTURE
//                it.parameters = parameters
                    try {
                        it.setPreviewDisplay(mSurfaceHolder)
                    } catch (e: Exception) {
                        e.printStackTrace()
//                    Toast.makeText(this@CameraTestActivity, e.message, Toast.LENGTH_SHORT).show()
                    }
                    it.startPreview()
                }
            }

            override fun surfaceDestroyed(p0: SurfaceHolder) {
                if (null != mCamera) {
                    mSurfaceHolder.removeCallback(this);
                    mCamera?.setPreviewCallback(null);
                    //停止预览
                    mCamera?.stopPreview()
                    mCamera?.lock()
                    //释放相机资源
                    mCamera?.release()
                    mCamera = null
                }
            }
        })
    }

    /**
     * 根据屏幕尺寸以及相机支持的分辨率获取最佳预览分辨率
     *
     * @param parameters 相机参数
     */
    private fun getPreviewSize(parameters: Camera.Parameters) {
        // 选择合适的图片尺寸，必须是手机支持的尺寸
        val sizeList = parameters.supportedPictureSizes
        // 如果sizeList只有一个我们也没有必要做什么了，因为就他一个别无选择
        if (sizeList.size > 1) {
            for (size: Camera.Size in sizeList) {
                Log.i("分辨率>>>", "Width=" + size.height + "__Height=" + size.width)
                val sizeGY = getGY(size.height, size.width)
                if (screenWidth / screenGY == size.height / sizeGY && screenHeight / screenGY == size.width / sizeGY) {
                    previewWidth = size.height
                    previewHeight = size.width
                    return
                }
            }
        } else {
            previewWidth = sizeList[0].height
            previewHeight = sizeList[0].width
        }
        Log.i("分辨率", "previewWidth=" + previewWidth + "__previewHeight=" + previewHeight)
    }

    /**
     * 获取两个数的公约数
     *
     * @param a 数字1
     * @param b 数字2
     *
     * @return 公约数
     */
    private fun getGY(a: Int, b: Int): Int {
        var localA = a
        var localB = b
        while (localA % localB != 0) {
            val temp = localA % localB
            localA = localB
            localB = temp
        }
        return localB
    }

    /**
     * 获取屏幕大小
     */
    private fun getScreenSize() {
        val resource = this.resources
        val dm = resource.displayMetrics
        screenWidth = dm.widthPixels
        screenHeight = dm.heightPixels

        screenGY = getGY(screenWidth, screenHeight)
    }

    /**
     * 初始化点击事件
     */
    private fun initClick() {
        btn_cancle.setOnClickListener { finish() }
        btn_take_picture.setOnClickListener {
            mCamera?.takePicture(
                {}, null
            ) { bytearray, camera ->
                bytearray?.let {
                    TAKE_PICTURE_PATH =
                        Environment.getExternalStorageDirectory().path + "/Download/take_picture_" + Date().time + ".jpg"
                    // 获取外部存储目录的路径
                    val directoryPath = Environment.getExternalStorageDirectory().absolutePath
                    val directory = File(directoryPath, "yuyu")
                    if (!directory.exists()) {
                        directory.mkdirs()
                    }
                    val fileName = UUID.randomUUID().toString() + ".jpg"
                    val saveFile = File(directory, fileName)
                    try {
                        var bitmap = BitmapFactory.decodeByteArray(bytearray, 0, bytearray.size)
                        if (bitmap != null) {
                            bitmap = ImageUtil.getRotatedBitmap(bitmap, mCameraRange)
                            val baos = ByteArrayOutputStream()
                            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
                            val outStream = FileOutputStream(saveFile)
                            outStream.write(baos.toByteArray())
                            outStream.flush()
                            outStream.close()
                            name.text = name.text.append("1")
//                        ImageUtil.saveBitmapToAlbum(this, bitmap, 100, true)

//                            val intent = Intent()
//                            val bundle = Bundle()
//                            bundle.putString("path", TAKE_PICTURE_PATH)
//                            intent.putExtra("take_data", bundle)
//                            this@CameraTestActivity.setResult(RESULT_OK, intent)
//                            finish()
                            mCamera?.startPreview()
                        }
                    } catch (e: Exception) {
                        e.printStackTrace()
                    }
                }
            }
        }
    }

    /**
     * 获取指定的相机摄像头
     */
    private fun getCustomCamera(): Camera {
        if (null == mCamera) {
            // 假如摄像头开启成功则返回一个Camera对象
            mCamera = Camera.open(0)
            //预览画面默认是横屏的，需要旋转90度
            mCamera?.setDisplayOrientation(mCameraRange)
        }
        return mCamera!!
    }

    override fun onDestroy() {
        super.onDestroy()
        mCamera?.setPreviewCallback(null)
        destory()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        destory()
    }

    /**
     * 页面销毁时释放相机资源
     */
    fun destory() {
        mCamera?.setPreviewCallback(null)
        //停止预览
        mCamera?.stopPreview()
        mCamera?.lock()
        //释放相机资源
        mCamera?.release()
        mCamera = null
    }
}
