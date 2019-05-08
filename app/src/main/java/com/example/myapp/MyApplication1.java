package com.example.myapp;

import android.app.Application;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;

import com.example.myapp.database.greenDao.db.DaoMaster;
import com.example.myapp.database.greenDao.db.DaoSession;
import com.nucarf.base.BuildConfig;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.utils.BaseAppCache;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;
import com.zhy.autolayout.config.AutoLayoutConifg;

import androidx.multidex.MultiDex;
import cn.jpush.android.api.JPushInterface;

public class MyApplication1 extends Application {
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }
    @Override
    public void onCreate() {
        super.onCreate();
        BaseAppCache.setContext(this);
        //屏幕适配
        AutoLayoutConifg.getInstance().useDeviceSize();

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

        JPushInterface.setDebugMode(true);
        JPushInterface.init(this);
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
