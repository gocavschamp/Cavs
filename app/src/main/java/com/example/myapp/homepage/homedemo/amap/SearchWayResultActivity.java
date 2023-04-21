package com.example.myapp.homepage.homedemo.amap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.help.Tip;
import com.example.myapp.R;
import com.example.myapp.api.AppService;
import com.example.myapp.bean.LineBean;
import com.example.myapp.bean.NewPointsBean;
import com.example.myapp.bean.SearchWayBean;
import com.google.gson.Gson;
import com.gyf.barlibrary.ImmersionBar;
import com.nucarf.base.retrofit.RetrofitUtils;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.ui.BaseActivityWithTitle;
import com.nucarf.base.utils.AppUtil;
import com.nucarf.base.utils.AssetUtil;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.DialogUtils;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.MapRoutUtil;
import com.nucarf.base.utils.ScreenUtil;
import com.nucarf.base.utils.SharePreUtils;
import com.umeng.analytics.MobclickAgent;

import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class SearchWayResultActivity extends BaseActivityWithTitle implements AMap.OnPolylineClickListener, AMap.OnMarkerClickListener, AMap.OnCameraChangeListener {

    @BindView(R.id.map)
    MapView mapView;
    @BindView(R.id.iv_location)
    ImageView ivLocation;
    @BindView(R.id.rl_content)
    RelativeLayout rlContent;
    private AMap aMap;
    private Marker marker;
    boolean isDestroy = false;
    private List<Polyline> polylinelist;
    private LatLng myLoactionLatLng;
    private LatLng mDestLoactionLatLng;
    private String mDestToStr;
    private UiSettings mUiSettings;
    private MyLocationStyle myLocationStyle;
    private static final int STROKE_COLOR = Color.argb(180, 3, 145, 255);
    private static final int FILL_COLOR = Color.argb(10, 0, 0, 180);
    private boolean isClickLocation = false;
    private ArrayList<MarkerOptions> markerOptionlst;
    private ArrayList<Marker> markerList;
    private Marker mClickMarker;
    private Marker mSelectMarker;
    private boolean isLoadLineData = false;
    private SearchWayBean searchWayBean;

    public static void lauch2(Context mContext, ArrayList<Tip> stationList) {
        Intent intent = new Intent(mContext, SearchWayResultActivity.class);
        intent.putParcelableArrayListExtra("stationList", stationList);
        mContext.startActivity(intent);
    }

    public static void lauch(Context mContext, String h_id, ArrayList<Tip> stationList) {
        Intent intent = new Intent(mContext, SearchWayResultActivity.class);
        intent.putExtra("h_id", h_id);
        intent.putParcelableArrayListExtra("stationList", stationList);
        mContext.startActivity(intent);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_way_result);
        ButterKnife.bind(this);
        mapView.onCreate(savedInstanceState);// 此方法必须重写
        titlelayout.setTitleText("查询结果");
        titlelayout.setRightText("切换始终");
        if (aMap == null) {
            aMap = mapView.getMap();
        }
    }

    @Subscribe
    public void OnEvent(Object event) {
    }

    @Override
    protected void initData() {
        if (aMap == null) {
            aMap = mapView.getMap();
            mUiSettings = aMap.getUiSettings();
            mUiSettings.setZoomControlsEnabled(false);
            setMyLocation();
        }
        //添加一个Marker用来展示海量点点击效果
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(R.mipmap.ic_launcher);
        mSelectMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromView(imageView)));

        aMap.setOnPolylineClickListener(this);
        aMap.setOnMarkerClickListener(this);
        addListener();
    }

    private void setDrawable(int p, TextView tvCrossStationSelect) {
        Drawable drawable = getResources().getDrawable(p);
        drawable.setBounds(0, 0, ScreenUtil.getRealWidth(mContext, 16), ScreenUtil.getRealWidth(mContext, 16));//第一0是距左边距离，第二0是距上边距离，30、35分别是长宽
        tvCrossStationSelect.setCompoundDrawables(drawable, null, null, null);//只放左边
    }

    /**
     * 自身定位显示
     */
    private void setMyLocation() {
        myLocationStyle = new MyLocationStyle();
        aMap.setMyLocationEnabled(true);// 是否可触发定位并显示定位层
        mUiSettings.setMyLocationButtonEnabled(true); // 是否显示默认的定位按钮
        // 自定义定位蓝点图标
        myLocationStyle.myLocationIcon(BitmapDescriptorFactory.
                fromResource(R.drawable.uz_icon));
        // 自定义精度范围的圆形边框颜色
        myLocationStyle.strokeColor(STROKE_COLOR);
        //自定义精度范围的圆形边框宽度
        myLocationStyle.strokeWidth(5);
        // 设置圆形的填充颜色
        myLocationStyle.radiusFillColor(FILL_COLOR);
        // 将自定义的 myLocationStyle 对象添加到地图上
        aMap.setMyLocationStyle(myLocationStyle);
        aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_SHOW));
    }

    private void addListener() {
        titlelayout.setRightClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_lxcx_19");
                searchWay();
            }
        });
        aMap.setOnCameraChangeListener(this);
        aMap.setOnMapTouchListener(new AMap.OnMapTouchListener() {
            @Override
            public void onTouch(MotionEvent motionEvent) {
            }
        });
        aMap.setOnMarkerClickListener(new AMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzdt_08");
                if (null == marker.getObject()) {
                    return false;
                }
                if (null != mClickMarker) {
                    mClickMarker.setVisible(true);
                }
                mClickMarker = marker;
                mClickMarker.setVisible(false);
                Log.i("amap ", "onPointClick");
                if (mSelectMarker.isRemoved()) {
                    //调用amap clear之后会移除marker，重新添加一个
                    ImageView imageView = new ImageView(mContext);
                    imageView.setImageResource(R.mipmap.ic_launcher);
                    mSelectMarker = aMap.addMarker(new MarkerOptions().icon(BitmapDescriptorFactory.fromView(imageView)));
                }
                //添加一个Marker用来展示海量点点击效果
                mSelectMarker.setPosition(marker.getPosition());
                mSelectMarker.setToTop();
                mClickMarker.setVisible(false);
                return false;
            }
        });
    }

    /**
     * 搜索路线
     */
    private void searchWay() {

        aMap.clear();
        try {
            String json_str = AssetUtil.getText(mContext, "way_data.text");
            searchWayBean = new Gson().fromJson(json_str, SearchWayBean.class);
            if (null == searchWayBean) {
                return;
            }
        } catch (Exception e) {
            LogUtils.e(getName() + "--", e.getMessage());
            return;
        }
        addPolylinessoild();
        isLoadLineData = true;
    }


    /**
     * 添加marker
     */
    private void addMarker() {
    }

    /**
     * 在地图上添加marker
     */
    private void addMarkersToMap(LatLng latlng, int resouce) {

        LogUtils.e("添加 marker");
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(resouce);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        MarkerOptions markerOption = new MarkerOptions()
                .icon(BitmapDescriptorFactory.fromView(imageView))
                .zIndex(1.5f)
                .position(latlng);
        Marker marker = aMap.addMarker(markerOption);
    }

    /**
     * 绘制 线
     */
    private void addPolylinessoild() {
        addMarker();
        if (searchWayBean != null && searchWayBean.getLine() != null && searchWayBean.getLine().size() > 0) {
            if (polylinelist != null) {
                polylinelist.clear();
                polylinelist = null;
            }
            polylinelist = new ArrayList<Polyline>();
            LogUtils.e("handler---start  ");
            if (searchWayBean.getPoints() != null && searchWayBean.getPoints().size() > 0) {
                startThread1();
            } else {
                clearMapMarkers();
                scalMap();
            }
            for (int i = 0; i < searchWayBean.getLine().size(); i++) {
                LineBean lineBean = searchWayBean.getLine().get(i);
                String[] split = lineBean.getPolyline().split(";");
                ArrayList<LatLng> list = new ArrayList<LatLng>();
                for (int j = 0; j < split.length; j++) {
                    String[] split1 = split[j].split(",");
                    double lat = Double.parseDouble(split1[0]);
                    double lon = Double.parseDouble(split1[1]);
                    LatLng latLng = new LatLng(lon, lat);//保证经纬度没有问题的时候可以填false
                    list.add(latLng);
                }
                LogUtils.e(i + "-i--data  list  size=" + list.size());
                PolylineOptions polylineOptions = (new PolylineOptions())
                        .addAll(list)
                        .width(20)
                        .setCustomTexture(BitmapDescriptorFactory.fromResource(i == 0 ? R.drawable.uz_icon : R.drawable.uz_pull_down_refresh_arrow))
                        .zIndex(i == 0 ? 0f : -0.1f);
                Polyline polyline = aMap.addPolyline(polylineOptions);
                LogUtils.e(i + "-i--data  list  size=" + list.size() + "---line id--" + polyline.getId());
                polylinelist.add(polyline);
            }
        }
    }

    private void clearMapMarkers() {
        if (markerList != null && markerList.size() > 0) {
            for (Marker marker : markerList) {
                marker.remove();
            }
            markerList.clear();
        }
    }

    @SuppressLint("CheckResult")
    private void scalMap() {
        Disposable subscribe = Observable.timer(500, TimeUnit.MILLISECONDS)
                .subscribeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<Long>() {
                    @Override
                    public void accept(Long aLong) throws Exception {
                        //缩放地图
                        List<LatLng> list1 = new ArrayList<LatLng>();
                        LineBean lineBean = searchWayBean.getLine().get(0);
                        String[] split = lineBean.getPolyline().split(";");
                        LatLng latLng = new LatLng(Double.parseDouble(split[0].split(",")[1]), Double.parseDouble(split[0].split(",")[0]));
                        LatLng latLng1 = new LatLng(Double.parseDouble(split[split.length-1].split(",")[1]), Double.parseDouble(split[split.length-1].split(",")[0]));
                        list1.add(latLng);
                        list1.add(latLng1);
                        zoomToSpan(list1);
                    }
                });
        addSubscribe(subscribe);

    }

    //开启子线程绘制地图
    private void startThread1() {
        clearMapMarkers();
        if (null == markerOptionlst) {
            markerOptionlst = new ArrayList<MarkerOptions>();
        } else {
            markerOptionlst.clear();
        }
        List<NewPointsBean> normalPointsBeanList = new ArrayList<>();
        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < searchWayBean.getPoints().size(); i++) {
                    if (isDestroy) {
                        //已经销毁地图了，退出循环
                        return;
                    }
                    NewPointsBean newPointsBean = searchWayBean.getPoints().get(i);
                    if (null == newPointsBean || TextUtils.isEmpty(newPointsBean.getLng()) || TextUtils.isEmpty(newPointsBean.getLat())) {
                        continue;
                    }
                    ImageView imageView = new ImageView(mContext);
                    imageView.setImageResource(R.mipmap.icon_map_location);
                    double lat = Double.parseDouble(newPointsBean.getLat());
                    double lon = Double.parseDouble(newPointsBean.getLng());
                    LatLng latLng = new LatLng(lat, lon, false);
                    MarkerOptions markerOption = new MarkerOptions()
                            .icon(BitmapDescriptorFactory.fromView(imageView))
                            .visible(true)
                            .title(newPointsBean.getName())
                            .snippet(newPointsBean.getSa_id())
                            .zIndex(("1".equals(newPointsBean.getIs_discount()) ? 1f : 0f))
                            .position(latLng);
                    markerOptionlst.add(markerOption);
                    normalPointsBeanList.add(newPointsBean);
                }
                markerList = aMap.addMarkers(markerOptionlst, true);
                for (int j = 0; j < markerList.size(); j++) {
                    markerList.get(j).setObject(normalPointsBeanList.get(j));
                }
                endCanvs();
            }
        }).start();
    }

    private void endCanvs() {
        scalMap();
    }

    /**
     * 根据屏幕的位置对路线进行适应屏幕的缩放
     */
    private void zoomToSpan(List<LatLng> latLngs) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (LatLng latLng : latLngs) {
            b.include(latLng);
        }
        LatLngBounds bounds = b.build();
        int top_padding = ScreenUtil.dip2px(100);
        int bottom_padding = ScreenUtil.dip2px(100);
        int left_right_padding = ScreenUtil.dip2px(100);
        aMap.moveCamera(CameraUpdateFactory
                .newLatLngBoundsRect(bounds, left_right_padding, left_right_padding, top_padding, bottom_padding));

    }

    @OnClick({R.id.rl_left, R.id.tv_right,
            R.id.iv_location})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_left:
                finish();
                break;
            case R.id.tv_right:
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_lxcx_31");
                searchWay();
                break;
            case R.id.iv_location:
                aMap.setMyLocationStyle(myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE));
                aMap.moveCamera(CameraUpdateFactory.zoomTo(12));
                isClickLocation = true;
                break;
        }
    }


    /**
     * 导航
     */
    private void guide() {
        String latitude = "";
        String longitude = "";
        String latLong = SharePreUtils.getLatLong(mContext);
        if (!TextUtils.isEmpty(latLong)) {
            String[] split = latLong.split(";");
            latitude = split[0];
            longitude = split[1];
        }
        myLoactionLatLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
        mDestLoactionLatLng = new LatLng(Double.parseDouble(searchWayBean.getPoints().get(0).getLat()), Double.parseDouble(searchWayBean.getPoints().get(15).getLng()));
        mDestToStr = searchWayBean.getPoints().get(0).getName();

        final ArrayList<String> items = new ArrayList<>();
        if (AppUtil.getInstance().isInstallByread(mContext, "com.autonavi.minimap")) {
            items.add("高德地图");
        }
        if (AppUtil.getInstance().isInstallByread(mContext, "com.baidu.BaiduMap")) {
            items.add("百度地图");
        }
        items.add("腾讯地图");
        DialogUtils.showListDialog(mContext, "", items, new DialogUtils.DialogItemClickListener() {
            @Override
            public void confirm(String result) {
                if (result.equalsIgnoreCase("高德地图")) {
                    MapRoutUtil.getInstance().selectGaode(mContext, myLoactionLatLng, "我的位置", mDestLoactionLatLng, mDestToStr);
                } else if (result.equalsIgnoreCase("百度地图")) {
                    MapRoutUtil.getInstance().selectBaidu(mContext, myLoactionLatLng, "我的位置", mDestLoactionLatLng, mDestToStr);
                } else if (result.equalsIgnoreCase("腾讯地图")) {
                    MapRoutUtil.getInstance().selectTencent(mContext, myLoactionLatLng, "我的位置", mDestLoactionLatLng, mDestToStr);
                }
            }
        });
    }


    /**
     * 显示油品种类
     */
    private void showOilSelect() {
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onResume() {
        super.onResume();
        if (null != mapView) {
            mapView.onResume();
        }
        if (searchWayBean == null) {
            searchWay();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onPause() {
        super.onPause();
        if (null != mapView) {
            mapView.onPause();
        }
    }

    /**
     * 方法必须重写
     */
    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (aMap != null) {
            aMap.setOnMultiPointClickListener(null);
            aMap.setOnMarkerClickListener(null);
            aMap.setOnCameraChangeListener(null);
            aMap.setOnPolylineClickListener(null);
            aMap.clear();
            aMap = null;
        }
        if (null != mapView) {
            mapView.onDestroy();
            mapView = null;
        }
        isDestroy = true;
    }

    @Override
    public void onPolylineClick(Polyline polyline) {
        LogUtils.e("click--polyline--" + polyline.getId());
        for (int i = 0; i < polylinelist.size(); i++) {
            if (polylinelist.get(i).getId().equals(polyline.getId())) {
                polyline.setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.uz_icon));
                polyline.setZIndex(0f);
            } else {
                polylinelist.get(i).setCustomTexture(BitmapDescriptorFactory.fromResource(R.drawable.uz_pull_down_refresh_arrow));
                polylinelist.get(i).setZIndex(-0.1f);
            }
        }

    }

    @Override
    public boolean onMarkerClick(Marker marker) {
        return false;
    }


    @Override
    public void onCameraChange(CameraPosition cameraPosition) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition cameraPosition) {
        LogUtils.d("onCameraChangeFinish");
        if (isClickLocation) {
            isClickLocation = false;
        } else {
        }
        if (isLoadLineData) {
            scalMap();
            isLoadLineData = false;
        }
    }
}
