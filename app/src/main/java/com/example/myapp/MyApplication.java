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
import com.nucarf.base.utils.BaseAppCache;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import androidx.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
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
        UMConfigure.init(this, RetrofitConfig.UM_APPKEY, MTA_CHANNEL_VALUE, UMConfigure.DEVICE_TYPE_PHONE, "");//pushSecret 58edcfeb310c93091c000be2 5965ee00734be40b580001a0
        PlatformConfig.setWeixin(RetrofitConfig.WX_APPID, RetrofitConfig.WX_APISECRET);

        //极光推送
        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
        // android 7.0系统解决拍照的问题
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
            builder.detectFileUriExposure();
        }
    }
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
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
