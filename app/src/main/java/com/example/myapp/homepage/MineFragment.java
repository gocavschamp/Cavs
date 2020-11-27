package com.example.myapp.homepage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.myapp.BuildConfig;
import com.example.myapp.R;
import com.example.myapp.bean.FileVersionBean;
import com.example.myapp.db.MySqliteHelper;
import com.example.myapp.utils.download.DownloadHelper;
import com.example.myapp.utils.download.DownloadListener;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.GlideUtils;
import com.nucarf.base.widget.RoundImageView;
import com.nucarf.base.widget.StarBar;

import java.io.File;

import butterknife.BindView;

public class MineFragment extends BaseLazyFragment implements DownloadListener {


    @BindView(R.id.star_bar)
    StarBar starBar;
    @BindView(R.id.tv_info)
    TextView tvInfo;
    @BindView(R.id.round_iv_head)
    RoundImageView roundIvHead;
    @BindView(R.id.iv_pic)
    ImageView ivPic;
    @BindView(R.id.fl_content)
    FrameLayout flContent;
    private MySqliteHelper mySqliteHelper;

    public MineFragment() {
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.mine_page;
    }

    @Override
    protected void initData() {
//        ImmersionBar immersionBar = ImmersionBar.with(this).setOnKeyboardListener(this);

        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("卡卡罗特\n");
        stringBuffer.append(BuildConfig.APPLICATION_ID + "  id\n");
        stringBuffer.append(BuildConfig.BUILD_TYPE + "  buildtype\n");
        stringBuffer.append(BuildConfig.FLAVOR + "  flavor\n");
        stringBuffer.append(BuildConfig.VERSION_CODE + "  code\n");
        stringBuffer.append(BuildConfig.VERSION_NAME + "  name\n");
        stringBuffer.append(getString(R.string.app_name) + "  app name\n");
        stringBuffer.append(getString(R.string.app_welcome) + "  app welcome\n");
        stringBuffer.append(getString(R.string.age) + "  age\n");
        tvInfo.setText(stringBuffer);
        GlideUtils.load(mActivity, getString(R.string.kakaluot), roundIvHead);
        GlideUtils.load(mActivity, getString(R.string.kakaluot), ivPic);

    }

    // 初始化
    private DownloadHelper mDownloadHelper = new DownloadHelper(RetrofitConfig.TEST_HOST_URL, this);

    int count = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
        String url = "https://gss0.baidu.com/-fo3dSag_xI4khGko9WTAnF6hhy/zhidao/wh%3D600%2C800/sign=9933924977310a55c471d6f287756f91/314e251f95cad1c8c983f49d713e6709c93d514b.jpg";
//        String url = "http://fenghuoyunji.jb51.net:81/201906/tools/OkHttpJar_jb51.rar";
        Log.e("TAG", "下载文件 start");

        // 下载文件
        mDownloadHelper.downloadFile(url, mActivity.getCacheDir().getAbsolutePath(), "nucarf" + count + ".jpg");
        mySqliteHelper = MySqliteHelper.getHelperInstance(mActivity);
        FileVersionBean fileVersionBean = mySqliteHelper.queryAllVersionData();
        if (fileVersionBean != null) {
            Log.e("TAG", "id = " + fileVersionBean.getId()
                    + "\n  v = " + fileVersionBean.getV()
                    + "\n  cv = " + fileVersionBean.getCv()
                    + "\n  native = " + fileVersionBean.getIs_native()
            );

        } else {
            Log.e("TAG", "无数据 ");
            FileVersionBean fileVersionBean1 = new FileVersionBean();
            fileVersionBean1.setId(1);
            fileVersionBean1.setV("1");
            String[] split = BuildConfig.VERSION_NAME.split("_");
            fileVersionBean1.setCv(split[0]);
            fileVersionBean1.setIs_native(1);
            mySqliteHelper.addVersionDataReturnID(fileVersionBean1);
            Log.e("TAG", "添加数据 ");
            queryData();

        }
        ivPic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.e("TAG", "更新数据 ");
                FileVersionBean fileVersionBean1 = new FileVersionBean();
                fileVersionBean1.setId(1);
                fileVersionBean1.setV("1");
                String[] split = BuildConfig.VERSION_NAME.split("_");
                fileVersionBean1.setCv("2.6.0");
                fileVersionBean1.setIs_native(0);
                mySqliteHelper.updateVersionnData(fileVersionBean1);
                queryData();
                count++;
                mDownloadHelper.downloadFile(url, mActivity.getCacheDir().getAbsolutePath(), "nucarf" + count + ".jpg");

            }
        });

    }

    private void queryData() {
        FileVersionBean fileVersionBean = mySqliteHelper.queryAllVersionData();

        if (fileVersionBean != null) {
            Log.e("TAG --查询", "id = " + fileVersionBean.getId()
                    + "\n  v = " + fileVersionBean.getV()
                    + "\n  cv = " + fileVersionBean.getCv()
                    + "\n  native = " + fileVersionBean.getIs_native()
            );
        }
    }

    @Override
    public void onStartDownload() {
        Log.e("TAG", "下载文件 start");

    }

    @Override
    public void onProgress(int progress) {
//        Log.e("TAG", "下载文件 start" + progress);

    }

    @Override
    public void onFinishDownload(File file) {
        Log.e("TAG", "下载文件 finish   " + file.getName() + "--\n" + file.getAbsolutePath());
        Glide.with(mActivity).load(Uri.fromFile(file)).into(ivPic);

    }

    @Override
    public void onFail(Throwable ex) {
        Log.e("TAG", "下载文件 failed");

    }
}
