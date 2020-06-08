package com.example.myapp.homepage.homedemo.bannertest;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.example.myapp.R;
import com.example.myapp.homepage.homedemo.bannertest.adapter.ImageAdapter;
import com.example.myapp.homepage.homedemo.bannertest.adapter.ImageNetAdapter;
import com.example.myapp.homepage.homedemo.bannertest.adapter.ImageTitleAdapter;
import com.example.myapp.homepage.homedemo.bannertest.adapter.ImageTitleNumAdapter;
import com.example.myapp.homepage.homedemo.bannertest.adapter.MultipleTypesAdapter;
import com.example.myapp.homepage.homedemo.bannertest.adapter.TopLineAdapter;
import com.example.myapp.homepage.homedemo.bannertest.bean.DataBean;
import com.google.android.material.snackbar.Snackbar;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.youth.banner.Banner;
import com.youth.banner.config.BannerConfig;
import com.youth.banner.config.IndicatorConfig;
import com.youth.banner.indicator.CircleIndicator;
import com.youth.banner.indicator.RectangleIndicator;
import com.youth.banner.indicator.RoundLinesIndicator;
import com.youth.banner.listener.OnPageChangeListener;
import com.youth.banner.transformer.AlphaPageTransformer;
import com.youth.banner.transformer.ZoomOutPageTransformer;
import com.youth.banner.util.BannerUtils;
import com.youth.banner.util.LogUtils;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class BannerActivity extends BaseActivityWithTitle implements OnPageChangeListener {

    @BindView(R.id.tip)
    TextView tip;
    @BindView(R.id.banner)
    Banner banner;
    @BindView(R.id.indicator)
    RoundLinesIndicator indicator;
    @BindView(R.id.style_image)
    Button styleImage;
    @BindView(R.id.style_image_title)
    Button styleImageTitle;
    @BindView(R.id.style_image_title_num)
    Button styleImageTitleNum;
    @BindView(R.id.style_multiple)
    Button styleMultiple;
    @BindView(R.id.style_net_image)
    Button styleNetImage;
    @BindView(R.id.change_indicator)
    Button changeIndicator;
    @BindView(R.id.gallery)
    Button gallery;
    @BindView(R.id.tip2)
    TextView tip2;
    @BindView(R.id.image_top)
    ImageView imageTop;
    @BindView(R.id.banner2)
    Banner banner2;
    @BindView(R.id.image_right)
    ImageView imageRight;
    @BindView(R.id.topLine)
    RelativeLayout topLine;
    @BindView(R.id.swipeRefresh)
    SwipeRefreshLayout refresh;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_banner_test);
        ButterKnife.bind(this);
        setTitle("banner  test");

    }

    @Override
    protected void initData() {

        ImageAdapter adapter = new ImageAdapter(DataBean.getTestData());


        banner.setAdapter(adapter)//设置适配器
//              .setCurrentItem(3,false)
                .addBannerLifecycleObserver(this)//添加生命周期观察者
                .setBannerRound(BannerUtils.dp2px(5))//圆角
//              .addPageTransformer(new RotateYTransformer())//添加切换效果
                .setIndicator(new CircleIndicator(this))//设置指示器
                .addOnPageChangeListener(this)//添加切换监听
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
                    LogUtils.d("position：" + position);
                });//设置点击事件,传this也行


        //添加间距(如果使用了画廊效果就不要添加间距了，因为内部已经添加过了)
//        banner.addPageTransformer(new MarginPageTransformer((int) BannerUtils.dp2px(10)));
        //魅族效果
//        banner.setBannerGalleryMZ(20);

        //实现1号店和淘宝头条类似的效果
        banner2.setAdapter(new TopLineAdapter(DataBean.getTestData2()))
                .setOrientation(Banner.VERTICAL)
                .setPageTransformer(new ZoomOutPageTransformer())
                .setOnBannerListener((data, position) -> {
                    Snackbar.make(banner, ((DataBean) data).title, Snackbar.LENGTH_SHORT).show();
//                    LogUtils.d("position：" + position);
                });


        //和下拉刷新配套使用
        refresh.setOnRefreshListener(() -> {
            //模拟网络请求需要3秒，请求完成，设置setRefreshing 为false
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    refresh.setRefreshing(false);
                    //给banner重新设置数据
                    banner.setDatas(DataBean.getTestData2());
                    //对setdatas不满意？你可以自己控制数据，可以参考setDatas()的实现修改
//                    adapter.updateData(DataBean.getTestData2());
                }
            }, 3000);
        });
    }

    @OnClick({R.id.style_image, R.id.style_image_title, R.id.style_image_title_num, R.id.style_multiple, R.id.style_net_image, R.id.change_indicator, R.id.gallery})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.style_image:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.CENTER);
                break;
            case R.id.style_image_title:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleAdapter(DataBean.getTestData()));
                banner.setIndicator(new CircleIndicator(this));
                banner.setIndicatorGravity(IndicatorConfig.Direction.RIGHT);
                banner.setIndicatorMargins(new IndicatorConfig.Margins(0, 0,
                        BannerConfig.INDICATOR_MARGIN, (int) BannerUtils.dp2px(12)));
                break;
            case R.id.style_image_title_num:
                refresh.setEnabled(true);
                banner.setAdapter(new ImageTitleNumAdapter(DataBean.getTestData()));
                banner.removeIndicator();
                break;
            case R.id.style_multiple:
                refresh.setEnabled(true);
                banner.setAdapter(new MultipleTypesAdapter(this, DataBean.getTestData()));
                banner.setIndicator(new RectangleIndicator(this));
                banner.setIndicatorNormalWidth((int) BannerUtils.dp2px(12));
                banner.setIndicatorSpace((int) BannerUtils.dp2px(4));
                banner.setIndicatorRadius(0);
                break;
            case R.id.style_net_image:
                refresh.setEnabled(false);
                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));
                banner.setIndicator(new RoundLinesIndicator(this));
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.change_indicator:
                indicator.setVisibility(View.VISIBLE);
                //在布局文件中使用指示器，这样更灵活
                banner.setIndicator(indicator, false);
                banner.setIndicatorSelectedWidth((int) BannerUtils.dp2px(15));
                break;
            case R.id.gallery:
                refresh.setEnabled(false);
                banner.setAdapter(new ImageNetAdapter(DataBean.getTestData3()));

                //添加画廊效果(可以和其他PageTransformer组合使用，比如AlphaPageTransformer，注意但和其他带有缩放的PageTransformer会显示冲突)
                banner.setBannerGalleryEffect(18, 10);
                //添加透明效果(画廊配合透明效果更棒)
                banner.addPageTransformer(new AlphaPageTransformer());
                break;
        }
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
