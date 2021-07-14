package com.example.myapp.homepage;

import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.myapp.R;
import com.example.myapp.homepage.mine.AppBarStateChangeListener;
import com.example.myapp.homepage.mine.HomeFragmentAdapter;
import com.example.myapp.homepage.mine.OilFilterLimitLayout;
import com.example.myapp.homepage.mine.adpter.HomeIconAdapter;
import com.example.myapp.homepage.mine.bean.MineIconBean;
import com.example.myapp.homepage.mine.bean.StationLimitBean;
import com.example.myapp.widget.HorizontalScrollBarDecoration;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.tabs.TabLayout;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.ui.BaseLazyFragment;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.ScreenUtil;
import com.nucarf.base.utils.SharePreUtils;
import com.nucarf.base.widget.CircleImageView;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.youth.banner.Banner;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 首页fragment
 */
public class MyCenterTestFragment extends BaseLazyFragment {
    private static final int REQUEST_CODE = 1344;
    private static final String TAG = "MyCenterFragment";
    @BindView(R.id.iv_head)
    CircleImageView ivHead;
    @BindView(R.id.home_speak_search)
    TextView homeSpeakSearch;
    @BindView(R.id.ll_search_station)
    LinearLayout llSearchStation;
    @BindView(R.id.iv_sercice)
    ImageView ivSercice;
    @BindView(R.id.iv_msg)
    ImageView ivMsg;
    @BindView(R.id.tv_msg_count)
    TextView tvMsgCount;
    @BindView(R.id.rl_title_layout)
    ConstraintLayout rlTitleLayout;
    @BindView(R.id.tv_gift3)
    TextView tvGift3;
    @BindView(R.id.tv_gift2)
    TextView tvGift2;
    @BindView(R.id.tv_gift1)
    TextView tvGift1;
    @BindView(R.id.tv_flag)
    TextView tvFlag;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.banner)
    Banner banner;
//        @BindView(R.id.home_tab_layout)
//        TabLayout homeTabLayout;
    @BindView(R.id.home_appbar_layout)
    AppBarLayout homeAppbarLayout;
    @BindView(R.id.home_viewPager)
    ViewPager homeViewPager;
    @BindView(R.id.home_refresh_layout)
    SmartRefreshLayout homeRefreshLayout;
    @BindView(R.id.home_back_top)
    ImageView homeBackTop;
    @BindView(R.id.ll_top_top)
    LinearLayout llTopTop;
    @BindView(R.id.home_oil_filter_layout)
    OilFilterLimitLayout homeOilFilterLayout;
    @BindView(R.id.home_tab_layout)
    TabLayout homeTabLayout;
    private HomeIconAdapter homeIconAdapter;

    public MyCenterTestFragment() {
    }
    private List<MineIconBean> nameList;

    public static MyCenterTestFragment newInstance() {
        return new MyCenterTestFragment();
    }

    @Override
    protected int setLayoutId() {
        return R.layout.mycenter_test_layout;
    }

    @Override
    protected void initData() {

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    protected void onInvisible() {
        super.onInvisible();
        LogUtils.i("---my center ---on onInvisible");
    }

    @Override
    protected void onVisible() {
        super.onVisible();
        LogUtils.i("---my center ---on onVisible");
    }

    @Subscribe
    public void OnEvent(Object event) {
    }

    @Override
    protected void initView() {
        ImmersionBar.with(this).statusBarDarkFont(true, 0.2f).titleBar(rlTitleLayout).init();
        homeRefreshLayout.setEnableLoadMore(true);
        ClassicsFooter classicsFooter = new ClassicsFooter(mActivity);
        classicsFooter.setBackgroundColor(Color.TRANSPARENT);
        homeRefreshLayout.setRefreshFooter(classicsFooter);

        ClassicsHeader refreshHeader = (ClassicsHeader) homeRefreshLayout.getRefreshHeader();
//        initTabs();
        addListener();
        MineFragment myCenterTestFragment = MineFragment.newInstance();
        homeViewPager.setAdapter(new HomeFragmentAdapter(getChildFragmentManager(), myCenterTestFragment));

        homeRefreshLayout.setEnableLoadMore(false);
        homeAppbarLayout.addOnOffsetChangedListener(new AppBarStateChangeListener() {
            @Override
            public void onStateChanged(AppBarLayout appBarLayout, State state) {
                if (state == State.COLLAPSED) {
//                    homeOilFilterLayout.setVisibility(View.VISIBLE);
                    int i0 = AppBarLayout.LayoutParams.SCROLL_FLAG_SCROLL;
                    int i1 = AppBarLayout.LayoutParams.SCROLL_FLAG_EXIT_UNTIL_COLLAPSED;
                    View appBarChildAt = homeAppbarLayout.getChildAt(0);
                    AppBarLayout.LayoutParams appBarParams = (AppBarLayout.LayoutParams) appBarChildAt.getLayoutParams();
//                    if (position == 0) {
//                        appBarParams.setScrollFlags( i0 | i1);// 重置折叠效果
//                    } else {
//                        appBarParams.setScrollFlags(0);//这个加了之后不可滑动
//                    }
                    appBarChildAt.setLayoutParams(appBarParams);
                    homeBackTop.setVisibility(View.VISIBLE);
                } else if (state == State.EXPANDED) {
//                    homeOilFilterLayout.setVisibility(View.GONE);
                    homeBackTop.setVisibility(View.GONE);

                } else {
//                    idel

                }
            }
        });
        ScreenUtil.setRecycleviewGridLayout(mActivity,recycleview,2,false);
        recycleview.addItemDecoration(new HorizontalScrollBarDecoration(5,50,"#FF4081","#FFEAF1FE"));
        homeIconAdapter = new HomeIconAdapter(R.layout.mine_icon_item);
        recycleview.setAdapter(homeIconAdapter);
        initTabs();
        initIcons();
        homeIconAdapter.setNewData(nameList);

    }
    /**
     * icons  初始化
     */
    private void initIcons() {
        nameList = new ArrayList<>();
        nameList.add(new MineIconBean("消费记录", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("淘油宝", R.mipmap.icon_center_habit).setType(2));
        nameList.add(new MineIconBean("储值记录", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("转万金油", R.mipmap.icon_center_habit).setShow(true));
        nameList.add(new MineIconBean("油站地图", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("违章查询", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("路线查询", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("推荐油站", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("新手引导", R.mipmap.icon_center_habit));
        nameList.add(new MineIconBean("最新活动", R.mipmap.icon_center_habit).setTip_icon(true).setType(2).setIcon_info("手慢无"));
    }
    private StationLimitBean stationLimitBean = new StationLimitBean();

    private void initTabs() {
        String[] titles = {"地区", "0#柴油", "品牌", "油站类型"};
        homeTabLayout.removeAllTabs();
        homeTabLayout.clearOnTabSelectedListeners();
        LayoutInflater inflater = LayoutInflater.from(getContext());
        for (String title : titles) {
            View view = inflater.inflate(R.layout.home_custome_tab_layout, null);
            TextView customView = view.findViewById(R.id.tv_title);
            customView.setSelected(false);
            customView.setText(title);
            TabLayout.Tab tab = homeTabLayout.newTab().setCustomView(customView);
            homeTabLayout.addTab(tab, false);
        }
        homeTabLayout.setSelected(false);
        TabLayout.OnTabSelectedListener onTabSelectedListener = new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                LogUtils.e("tab", "--onTabSelected-");
                if (stationLimitBean == null && tab.getPosition() != 0) {
//                    getStationLimit();
                    return;
                }
                View customView = tab.getCustomView();
                customView.findViewById(R.id.tv_title).setSelected(true);//第一个tab被选中
                homeOilFilterLayout.onTabClick(tab, true);
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View customView = tab.getCustomView();
                customView.findViewById(R.id.tv_title).setSelected(false);//第一个tab被选中
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                LogUtils.e("tab", "--onTabReselected-");
                if (stationLimitBean == null && tab.getPosition() != 0) {
//                    getStationLimit();
                    return;
                }
                homeOilFilterLayout.onTabClick(tab, false);
            }
        };
        homeTabLayout.addOnTabSelectedListener(onTabSelectedListener);
        for (int i = 0; i < homeTabLayout.getTabCount(); i++) {
            TabLayout.Tab tabAt = homeTabLayout.getTabAt(i);
            View customView = tabAt.getCustomView();
            customView.findViewById(R.id.tv_title).setSelected(false);//第一个tab被选中
        }
        homeOilFilterLayout.setTab(homeTabLayout);
    }

    private void addListener() {
        homeRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {

            }
        });
        homeBackTop.setOnClickListener(v->{
            homeAppbarLayout.setExpanded(true);
        });
    }

    /**
     * 获取网络数据
     *
     * @param isRefresh
     */
    private void getData(boolean isRefresh) {
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        return rootView;
    }


}
