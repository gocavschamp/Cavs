package com.example.myapp.task;

import android.app.Application;

import com.moonlight.flyvideo.BuildConfig;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.liys.doubleclicklibrary.ViewDoubleHelper;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;
import com.tencent.smtt.sdk.QbSdk;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.uzmap.pkg.openapi.APICloud;

import org.jay.launchstarter.Task;

import cn.jpush.android.api.JPushInterface;


/**
 * @Description TODO
 * @Author yuwenming
 * @Date 2020/11/24 16:04
 */
public class InitGreenDaoTask extends Task {

    @Override
    public void run() {
        //防止点击抖动
        ViewDoubleHelper.init((Application) mContext, 500); //默认时间：1秒
//        initGreenDao(this.getApplication());
        //初始化APICloud，SDK中所有的API均需要初始化后方可调用执行 封装webview
        APICloud.initialize(mContext);
        //友盟分享统计
        BaseAppCache.setVersion_code(BuildConfig.VERSION_CODE);
        UMConfigure.init(mContext, RetrofitConfig.UM_APPKEY, "allenyu", UMConfigure.DEVICE_TYPE_PHONE, "");//pushSecret 58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin(RetrofitConfig.WX_APPID, RetrofitConfig.WX_APISECRET);
        // 请勿在“=”与appid之间添加任何空字符或者转义符
        StringBuffer param = new StringBuffer();
        param.append("appid=5d5a1b6d");
        param.append(",");
        // 设置使用v5+
        param.append(SpeechConstant.ENGINE_MODE + "=" + SpeechConstant.MODE_MSC);
        SpeechUtility.createUtility(mContext, param.toString());
        //初始化x5webview
        initX5WebView();
        JPushInterface.setDebugMode(true);
        JPushInterface.init(mContext);
        LogUtils.e("task  第三方初始化");


    }
    /*使用腾讯x5 webview，解决安卓原生wenview不适配不同机型问题*/
    private void initX5WebView() {

        //搜集本地tbs内核信息并上报服务器，服务器返回结果决定使用哪个内核。
        QbSdk.PreInitCallback cb = new QbSdk.PreInitCallback() {

            @Override
            public void onViewInitFinished(boolean arg0) {
                // TODO Auto-generated method stub
                //x5內核初始化完成的回调，为true表示x5内核加载成功，否则表示x5内核加载失败，会自动切换到系统内核。
                LogUtils.d("onViewInitFinished", " onViewInitFinished is " + arg0);
                if(arg0){
                    LogUtils.d("onViewInitFinished", "腾讯X5内核加载成功");
                }else {
                    LogUtils.d("onViewInitFinished", "腾讯X5内核加载失败，使用原生安卓webview");
                }
            }

            @Override
            public void onCoreInitFinished() {
                // TODO Auto-generated method stub
            }
        };
        //x5内核初始化接口
        QbSdk.initX5Environment(mContext,  cb);
    }
}
