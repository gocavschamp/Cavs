package com.example.myapp.activity.gesture

import android.app.Dialog
import android.util.Log
import android.view.View
import android.widget.Toast
import com.afollestad.materialdialogs.MaterialDialog
import com.dds.fingerprintidentify.FingerprintIdentify
import com.dds.fingerprintidentify.base.BaseFingerprint
import com.dds.fingerprintidentify.base.BaseFingerprint.FingerprintIdentifyExceptionListener
import com.example.myapp.R
import com.nucarf.base.ui.BaseActivityWithTitle2
import com.nucarf.base.utils.DialogUtils
import com.nucarf.base.utils.DialogUtils.DialogClickListener
import kotlinx.android.synthetic.main.activity_gesture_lock.*


/**
 *@auther yuwenming
 *@createTime：2023/5/11 15:29
 *@desc:
 **/
class GestureLockActivity : BaseActivityWithTitle2() {
    private val TAG = "GestureLockActivity"
    private var mFingerprintIdentify: FingerprintIdentify? = null
    private var mAlertDialog: Dialog? = null

    override fun getLayoutResId(): Int {
      return R.layout.activity_gesture_lock
    }

    override fun initView() {
    }

    override fun initListener() {
        //指纹初始化
        //指纹初始化
        btn_init_fingerprint.setOnClickListener(View.OnClickListener {
            Log.i(TAG, "mInitFingerPrint onClick")
            initFingerPrint()
        })


        //启动指纹认证
        btn_fingerprint.setOnClickListener(View.OnClickListener {
            Log.i(TAG, "mStartFingerPrint onClick")
            DialogUtils.getInstance().showRadioDialog(mContext, "指纹验证", "请将手指放于指纹传感器上", "",object :DialogClickListener{
                override fun confirm() {


                }

                override fun cancel() {
                }
            })

            tv_auth_result.setText("等待指纹验证结果")
            mFingerprintIdentify?.startIdentify(3, object :
                BaseFingerprint.FingerprintIdentifyListener {
                override fun onSucceed() {
                    Log.i(TAG, "mFingerprintIdentify onSucceed")
//                    MaterialDialog(mContext)
//                        .setTitle("")
                    DialogUtils.getInstance().showRadioDialog(mContext, "验证通过", "匹配成功", "", object :DialogClickListener{
                        override fun confirm() {


                        }

                        override fun cancel() {
                        }
                    })

                    tv_auth_result.setText("指纹验证通过")
                    Toast.makeText(this@GestureLockActivity, "onSucceed", Toast.LENGTH_SHORT).show()
                }

                override fun onNotMatch(availableTimes: Int) {
                    Log.i(
                        TAG,
                        "mFingerprintIdentify onNotMatch, availableTimes: $availableTimes"
                    )
                    tv_auth_result.setText("指纹验证不通过")
                    DialogUtils.getInstance().showRadioDialog(mContext, "验证失败", "请重试", "", object :DialogClickListener{
                        override fun confirm() {


                        }

                        override fun cancel() {
                        }
                    })


                    Toast.makeText(this@GestureLockActivity, "onNotMatch", Toast.LENGTH_SHORT).show()
                }

                override fun onFailed() {
                    Log.i(TAG, "mFingerprintIdentify onFailed")
                    DialogUtils.getInstance().showRadioDialog(mContext, "警告", "指纹验证错误次数超过上限", "", object :DialogClickListener{
                        override fun confirm() {


                        }

                        override fun cancel() {
                        }
                    })


                    tv_auth_result.setText("指纹验证错误次数超过上限")
                    Toast.makeText(this@GestureLockActivity, "onFailed", Toast.LENGTH_SHORT).show()
                    mFingerprintIdentify!!.cancelIdentify()
                }
            })
        })
        // 初始化手势密码事件
        // 初始化手势密码事件
        btn_init_gesture_unlock.setOnClickListener(View.OnClickListener {
            GestureUnlock.getInstance().init(this@GestureLockActivity.getApplicationContext())
            GestureUnlock.getInstance().createGestureUnlock(this@GestureLockActivity)
        })
        // 验证手势密码事件
        // 验证手势密码事件
        btn_verify_gesture_unlock.setOnClickListener(View.OnClickListener {
            GestureUnlock.getInstance().init(this@GestureLockActivity.getApplicationContext())
            GestureUnlock.getInstance().verifyGestureUnlock(this@GestureLockActivity)
        })
        // 修改手势密码
        // 修改手势密码
        btn_modify_gesture_unlock.setOnClickListener(View.OnClickListener {
            GestureUnlock.getInstance().init(this@GestureLockActivity.getApplicationContext())
            GestureUnlock.getInstance().modifyGestureUnlock(this@GestureLockActivity)
        })
    }

    override fun initData() {
    }

    /**
     * 初始化指纹识别
     */
    private fun initFingerPrint() {
        mFingerprintIdentify = FingerprintIdentify(this,
            FingerprintIdentifyExceptionListener { exception ->
                Log.i(TAG, "initFingerPrint", exception)
                tv_auth_result.setText("指纹验证初始化异常：" + exception.message)
            })
        if (!mFingerprintIdentify!!.isHardwareEnable()) {
            tv_auth_result.setText("指纹验证初始化失败：设备硬件不支持指纹识别")
            return
        }
        if (!mFingerprintIdentify!!.isRegisteredFingerprint()) {
            tv_auth_result.setText("指纹验证初始化失败：未注册指纹")
            return
        }
        if (!mFingerprintIdentify!!.isFingerprintEnable()) {
            tv_auth_result.setText("指纹验证初始化失败：指纹识别不可用")
            return
        }
        tv_auth_result.setText("指纹验证初始化正常")
    }

}