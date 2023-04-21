package com.example.myapp.homepage;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapp.BuildConfig;
import com.example.myapp.R;
import com.example.myapp.activity.WebViewX5KtActivity;
import com.example.myapp.bean.ArticleListBean;
import com.example.myapp.bean.FileVersionBean;
import com.example.myapp.db.MySqliteHelper;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetAdapter;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetContract;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetPresenter;
import com.example.myapp.homepage.mine.OilFilterLimitLayout;
import com.example.myapp.homepage.mine.bean.StationLimitBean;
import com.example.myapp.utils.download.DownloadHelper;
import com.example.myapp.utils.download.DownloadListener;
import com.google.android.material.tabs.TabLayout;
import com.nucarf.base.retrofit.RetrofitConfig;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.GlideUtils;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.widget.RoundImageView;
import com.nucarf.base.widget.StarBar;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.util.List;

import butterknife.BindView;

public class MineFragment extends BaseLazyFragment implements DownloadListener, BottomSheetContract.View, BaseQuickAdapter.RequestLoadMoreListener {


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
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    private MySqliteHelper mySqliteHelper;
    private BottomSheetAdapter bottomSheetAdapter;
    private BottomSheetPresenter mPresenter;

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
//        stringBuffer.append(BuildConfig.APPLICATION_ID + "  id\n");
//        stringBuffer.append(BuildConfig.BUILD_TYPE + "  buildtype\n");
//        stringBuffer.append(BuildConfig.FLAVOR + "  flavor\n");
//        stringBuffer.append(BuildConfig.VERSION_CODE + "  code\n");
//        stringBuffer.append(BuildConfig.VERSION_NAME + "  name\n");
//        stringBuffer.append(getString(R.string.app_name) + "  app name\n");
//        stringBuffer.append(getString(R.string.app_welcome) + "  app welcome\n");
//        stringBuffer.append(getString(R.string.age) + "  age\n");
        tvInfo.setText(stringBuffer);
        GlideUtils.load(mActivity, getString(R.string.kakaluot), roundIvHead);
        GlideUtils.load(mActivity, getString(R.string.kakaluot), ivPic);

        recyclerView.setLayoutManager(new LinearLayoutManager(mActivity, RecyclerView.VERTICAL, false));


        bottomSheetAdapter = new BottomSheetAdapter(R.layout.default_normal_layout);
        recyclerView.setAdapter(bottomSheetAdapter);
        bottomSheetAdapter.setEnableLoadMore(true);
        bottomSheetAdapter.setOnLoadMoreListener(this, recyclerView);
//bottomSheetAdapter.setEnableLoadMore(false);
//        initTabs();
        addListenter();
        getData();

    }

    private void addListenter() {
        bottomSheetAdapter.setOnItemChildClickListener((adapter, view, position) -> {
            WebViewX5KtActivity.Companion.lauch(mActivity,bottomSheetAdapter.getData().get(position).getTitle(),bottomSheetAdapter.getData().get(position).getLink());
        });
    }

    private void getData() {
        mPresenter = new BottomSheetPresenter();
        if (mPresenter != null) {
            mPresenter.attachView(this);
        }
        if (mPresenter != null) {
            mPresenter.loadData(true);
        }
    }
    private StationLimitBean stationLimitBean = new StationLimitBean();

//    private void initTabs() {
//        String[] titles = {"地区", "0#柴油", "品牌", "油站类型"};
//        homeTabLayout.removeAllTabs();
//        homeTabLayout.clearOnTabSelectedListeners();
//        LayoutInflater inflater = LayoutInflater.from(getContext());
//        for (String title : titles) {
//            View view = inflater.inflate(R.layout.home_custome_tab_layout, null);
//            TextView customView = view.findViewById(R.id.tv_title);
//            customView.setSelected(false);
//            customView.setText(title);
//            TabLayout.Tab tab = homeTabLayout.newTab().setCustomView(customView);
//            homeTabLayout.addTab(tab, false);
//        }
//        homeTabLayout.setSelected(false);
//        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                LogUtils.e("tab", "--onTabSelected-");
//                if (stationLimitBean == null && tab.getPosition() != 0) {
////                    getStationLimit();
//                    return;
//                }
//                View customView = tab.getCustomView();
//                customView.findViewById(R.id.tv_title).setSelected(true);//第一个tab被选中
//                homeOilFilterLayout.onTabClick(tab, true);
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                View customView = tab.getCustomView();
//                customView.findViewById(R.id.tv_title).setSelected(false);//第一个tab被选中
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//                LogUtils.e("tab", "--onTabReselected-");
//                if (stationLimitBean == null && tab.getPosition() != 0) {
////                    getStationLimit();
//                    return;
//                }
//                homeOilFilterLayout.onTabClick(tab, false);
//            }
//        };
//        homeTabLayout.addOnTabSelectedListener(onTabSelectedListener);
//        for (int i = 0; i < homeTabLayout.getTabCount(); i++) {
//            TabLayout.Tab tabAt = homeTabLayout.getTabAt(i);
//            View customView = tabAt.getCustomView();
//            customView.findViewById(R.id.tv_title).setSelected(false);//第一个tab被选中
//        }
//        homeOilFilterLayout.setTab(homeTabLayout);
//    }

    // 初始化
    private DownloadHelper mDownloadHelper = new DownloadHelper(RetrofitConfig.TEST_HOST_URL, this);

    int count = 0;

    @SuppressLint("CheckResult")
    @Override
    protected void initView() {
//        String url = downloadFile();
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
//                mDownloadHelper.downloadFile(url, mActivity.getCacheDir().getAbsolutePath(), "nucarf" + count + ".jpg");

            }
        });

    }

    @NotNull
    private String downloadFile() {
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
        return url;
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

    @Override
    public void onLoadMoreRequested() {
        mPresenter.loadData(false);

    }

    @Override
    public void setData(boolean isRefresh, List<ArticleListBean> data, boolean isEnd) {
        if (isRefresh) {
            bottomSheetAdapter.setNewData(data);
        } else {
            bottomSheetAdapter.addData(data);
            bottomSheetAdapter.loadMoreComplete();
        }
        if (isEnd) {
            bottomSheetAdapter.loadMoreEnd();
        }
    }

    @Override
    public int getDataSize() {
        return bottomSheetAdapter.getData().size();
    }

    @Override
    public void showLoading() {

    }

    @Override
    public void closeLoading() {

    }

    @Override
    public void onSucess() {

    }

    @Override
    public void onFail() {

    }

    @Override
    public void onNetError(int errorCode, String errorMsg) {

    }

    @Override
    public void onReLoad() {

    }
}
