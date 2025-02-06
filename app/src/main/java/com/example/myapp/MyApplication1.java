package com.example.myapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.os.StrictMode;

import com.example.myapp.database.greenDao.db.DaoMaster;
import com.example.myapp.database.greenDao.db.DaoSession;
import com.nucarf.base.BuildConfig;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.utils.ActivityHelper;
import com.nucarf.base.utils.BaseAppCache;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.umeng.umcrash.UMCrash;
import com.yanbo.lib_screen.VApplication;

import androidx.multidex.MultiDex;

import cn.jpush.android.api.JPushInterface;
import me.jessyan.autosize.AutoSizeConfig;

public class MyApplication1 extends Application  {
//    private FlutterEngine flutterEngine;
    private static MyApplication1 instance;

    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        BaseAppCache.setContext(this);
        //投屏初始化
        VApplication.init(this);
        //insta gally
        initGreenDao();
        //友盟分享统计
        ApplicationInfo appInfo = null;
        try {
            appInfo = this.getPackageManager()
                    .getApplicationInfo(getPackageName(),
                            PackageManager.GET_META_DATA);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        String MTA_CHANNEL_VALUE = appInfo.metaData.getString("CHANNEL_VALUE");
        BaseAppCache.setChannel_name(MTA_CHANNEL_VALUE);
        BaseAppCache.setVersion_code(BuildConfig.VERSION_CODE);
        UMConfigure.preInit(this,RetrofitConfig.UM_APPKEY, MTA_CHANNEL_VALUE);
        // 选用AUTO页面采集模式
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        UMConfigure.setLogEnabled(false);
        //友盟分享统计
        UMConfigure.init(this, RetrofitConfig.UM_APPKEY, MTA_CHANNEL_VALUE, UMConfigure.DEVICE_TYPE_PHONE, "");//pushSecret 58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        UMCrash.init(this, RetrofitConfig.UM_APPKEY, "MTA_CHANNEL_VALUE");

        PlatformConfig.setWeixin(RetrofitConfig.WX_APPID, RetrofitConfig.WX_APISECRET);
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        BaseAppCache.setContext(this);
        BaseAppCache.setApplication(this);
        disableAPIDialog();
        // android 7.0系统解决拍照的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
//        flutterEngine = new FlutterEngine(this);
//        // Start executing Dart code to pre-warm the FlutterEngine.
//        flutterEngine.getDartExecutor().executeDartEntrypoint(
//                DartExecutor.DartEntrypoint.createDefault()
//        );
//        // Cache the FlutterEngine to be used by FlutterActivity.
//        FlutterEngineCache
//                .getInstance()
//                .put("my_engine_id", flutterEngine);
        //去除9.0 弹框
        registerActivityLifecycleCallbacks(new ActivityHelper());
        //屏幕适配
        AutoSizeConfig.getInstance().setExcludeFontScale(true);
    }

    public static synchronized MyApplication1 getInstance() {
        return instance;
    }

//    public static AppComponent getAppComponent() {
//        return DaggerAppComponent.builder()
//                .appModule(new AppModule(instance))
//                .build();
//    }

    /**
     * 反射 禁止弹窗
     */
    private void disableAPIDialog() {
//        if (Build.VERSION.SDK_INT < 28) return;
//        try {
//            Class aClass = Class.forName("android.content.pm.PackageParser$Package");
//            Constructor declaredConstructor = aClass.getDeclaredConstructor(String.class);
//            declaredConstructor.setAccessible(true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        try {
//            Class clazz = Class.forName("android.app.ActivityThread");
//            Method currentActivityThread = clazz.getDeclaredMethod("currentActivityThread");
//            currentActivityThread.setAccessible(true);
//            Object activityThread = currentActivityThread.invoke(null);
//            Field mHiddenApiWarningShown = clazz.getDeclaredField("mHiddenApiWarningShown");
//            mHiddenApiWarningShown.setAccessible(true);
//            mHiddenApiWarningShown.setBoolean(activityThread, true);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
    /**
     * 初始化GreenDao,直接在Application中进行初始化操作
     */
    private void initGreenDao() {
        DaoMaster.DevOpenHelper helper = new DaoMaster.DevOpenHelper(this, "aserbao.db");
        SQLiteDatabase db = helper.getWritableDatabase();
        DaoMaster daoMaster = new DaoMaster(db);
        daoSession = daoMaster.newSession();
    }

    private DaoSession daoSession;

    public DaoSession getDaoSession() {
        return daoSession;
    }

}
