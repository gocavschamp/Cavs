package com.example.myapp.activity.svga;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.example.myapp.R;
import com.example.myapp.activity.WebViewX5KtActivity;
import com.example.myapp.activity.tantan.TanTanActivity;
import com.example.myapp.activity.tantan.TanTanAdapter;
import com.example.myapp.bean.GiftBean;
import com.example.myapp.homepage.homedemo.DBTestActivity;
import com.example.myapp.homepage.homedemo.EdittextTextActivity;
import com.example.myapp.homepage.homedemo.FlutterTestActivity;
import com.example.myapp.homepage.homedemo.RxjavaDemoActivity;
import com.example.myapp.homepage.homedemo.amap.SearchWayResultActivity;
import com.example.myapp.homepage.homedemo.apiclound.ApiCloundTestActivity;
import com.example.myapp.homepage.homedemo.bannertest.BannerActivity;
import com.example.myapp.homepage.homedemo.bottomsheet.BottomSheetBihaverActivity;
import com.example.myapp.homepage.homedemo.dialogshow.DialogShowActivity;
import com.example.myapp.homepage.homedemo.dragscallview.DragAndScallActivity;
import com.example.myapp.homepage.homedemo.multitem.MultItemActivity;
import com.example.myapp.homepage.homedemo.videolist.DouYinListActivity;
import com.example.myapp.homepage.homedemo.videolist.VideoListActivity;
import com.example.myapp.homepage.homedemo.xunfei.XunFeiYuYinActivity;
import com.example.myapp.homepage.homedemo.zxing.QrcodeZxingDemoActivity;
import com.example.myapp.widget.gift.GiftRootLayout;
import com.example.myapp.widget.swipecard.OverLayCardLayoutManager;
import com.example.myapp.widget.swipecard.RenRenCallback;
import com.jakewharton.rxbinding2.view.RxView;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.UiGoto;
import com.opensource.svgaplayer.SVGACallback;
import com.opensource.svgaplayer.SVGADrawable;
import com.opensource.svgaplayer.SVGAImageView;
import com.opensource.svgaplayer.SVGAParser;
import com.opensource.svgaplayer.SVGAVideoEntity;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
//import io.flutter.embedding.android.FlutterActivity;
import io.reactivex.android.schedulers.AndroidSchedulers;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class SvgaGiftActivity extends BaseActivityWithTitle {

    @BindView(R.id.tvTitle)
    TextView tvTitle;
    @BindView(R.id.recycleview)
    RecyclerView recycleview;
    @BindView(R.id.svga)
    SVGAImageView svga;
    @BindView(R.id.giftRoot)
     GiftRootLayout giftRoot;//礼物连击
    private MycenterAdapter mycenterAdapter;
    private SVGAParser parser;//svga动画解析器

    public static void startActivity(Context context) {
        Intent intent = new Intent(context, SvgaGiftActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    @SuppressLint("CheckResult")
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svga_gift);
        titlelayout.setTitleText("tan tan");
        titlelayout.setLeftClickListener((v)->{
            finish();
        });
    }

    @Override
    protected void initData() {
        parser = new SVGAParser(mContext);
        //设置礼物动画播放完后清除
        svga.setClearsAfterStop(true);
        initView();


    }



    private void initView() {
        recycleview.setLayoutManager(new GridLayoutManager(mContext, 3, RecyclerView.VERTICAL, false));
        mycenterAdapter = new MycenterAdapter(R.layout.mycenter_item);
        recycleview.setAdapter(mycenterAdapter);
        mycenterAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                String item = mycenterAdapter.getData().get(position);
                switch (position) {
                    case 0:
                        loadSvgaLocal("td_auo.svga",svga);
                        break;
                    case 1:
                        loadSvgaLocal("td_call.svga",svga);

                        break;
                    case 2:
                        loadSvgaLocal("td_rain_coin.svga",svga);

                        break;
                    case 3:
                        loadSvgaLocal("td_rain_diamond.svga",svga);

                        break;
                    case 4:
                        loadSvgaLocal("td_win.svga",svga);

                        break;
                    case 5:
                        loadSvgaLocal("td_super_like.svga",svga);

                        break;
                    case 6:
                        loadSvgaLocal("td_tread.svga",svga);

                        break;
                    case 7:
                        GiftBean giftBean = new GiftBean("一座MVP奖杯奖杯","JAMES");
                        giftBean.setGroup(1);
                        giftBean.setPresent_id("1");
                        giftBean.setPic_url("https://graph.baidu.com/thumb/v4/3968545426,3327092963.jpg");
                        giftBean.setTip_message("你收到一座奖杯");
                        giftRoot.loadGift(giftBean);

                        break;
                    case 8:
                        GiftBean giftBean1 =  new GiftBean("5个总冠军奖杯🏆","JORDAN");
                        giftBean1.setGroup(1);
                        giftBean1.setPresent_id("2");
                        giftBean1.setPic_url("https://bkimg.cdn.bcebos.com/pic/8cb1cb13495409230e8194fc9e58d109b2de494c?x-bce-process=image/resize,m_lfit,w_268,limit_1/format,f_jpg");
                        giftBean1.setTip_message("你收到一座奖杯");
                        giftRoot.loadGift(giftBean1);
                        break;
                }
            }
        });
        List<String> data = new ArrayList<>();
        data.add("td_auo");
        data.add("td_call");
        data.add("td_rain_coin");
        data.add("td_rain_diamond");
        data.add("td_win");
        data.add("td_super_like");
        data.add("td_tread");
        data.add("小礼物");
        data.add("大礼物");
        mycenterAdapter.setNewData(data);
    }

    //加载本地svga
    private void loadSvgaLocal(String anim, SVGAImageView imageView) {
        parser.parse(anim, new SVGAParser.ParseCompletion() {
            @Override
            public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                SVGADrawable drawable = new SVGADrawable(videoItem);
                imageView.setImageDrawable(drawable);
                imageView.startAnimation();
            }

            @Override
            public void onError() {

            }
        });
    }

    /**
     * 加载网络Svga
     *
     * @param url
     */
    private void loadSvgaRemote(String url, SVGAImageView imageView) {
        resetDownloader(parser);
        try {
            parser.parse(new URL(url), new SVGAParser.ParseCompletion() {
                @Override
                public void onComplete(@NotNull SVGAVideoEntity videoItem) {
                    SVGADrawable drawable = new SVGADrawable(videoItem);
                    imageView.setImageDrawable(drawable);
                    imageView.startAnimation();
                    imageView.setCallback(new SVGACallback() {
                        @Override
                        public void onPause() {

                        }

                        @Override
                        public void onFinished() {
//                            loadProGift(proGiftList);
                        }

                        @Override
                        public void onRepeat() {

                        }

                        @Override
                        public void onStep(int frame, double percentage) {

                        }
                    });
                }

                @Override
                public void onError() {

                }
            });
        } catch (Exception e) {
            System.out.print(true);
        }
    }


    /**
     * 设置下载器，这是一个可选的配置项。
     *
     * @param parser
     */
    private void resetDownloader(SVGAParser parser) {
        parser.setFileDownloader(new SVGAParser.FileDownloader() {
            @Override
            public void resume(final URL url, final Function1<? super InputStream, Unit> complete, final Function1<? super Exception, Unit> failure) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        OkHttpClient client = new OkHttpClient();
                        Request request = new Request.Builder().url(url).get().build();
                        try {
                            Response response = client.newCall(request).execute();
                            complete.invoke(response.body().byteStream());
                        } catch (IOException e) {
                            e.printStackTrace();
                            failure.invoke(e);
                        }
                    }
                }).start();
            }
        });
    }

    private class MycenterAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
        public MycenterAdapter(int layout) {
            super(layout);
        }

        @Override
        protected void convert(BaseViewHolder helper, String item) {
            helper.setText(R.id.tv_info, item);

        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

}
