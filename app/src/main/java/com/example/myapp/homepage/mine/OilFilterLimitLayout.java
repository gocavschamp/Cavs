package com.example.myapp.homepage.mine;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.myapp.R;
import com.example.myapp.bean.StringBean;
import com.example.myapp.homepage.mine.bean.BrandsListBean;
import com.example.myapp.homepage.mine.bean.CityListAdapter;
import com.example.myapp.homepage.mine.bean.FuelsBean;
import com.example.myapp.homepage.mine.bean.FuelsListBean;
import com.example.myapp.homepage.mine.bean.OilBrandListAdapter;
import com.example.myapp.homepage.mine.bean.OilTypeListAdapter;
import com.example.myapp.homepage.mine.bean.StationLimitBean;
import com.google.android.material.tabs.TabLayout;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nucarf.base.bean.CityListBean;
import com.nucarf.base.utils.AssetUtil;
import com.nucarf.base.utils.BaseAppCache;
import com.nucarf.base.utils.LogUtils;
import com.nucarf.base.utils.ScreenUtil;
import com.nucarf.base.utils.SharePreUtils;
import com.umeng.analytics.MobclickAgent;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OilFilterLimitLayout extends LinearLayout {
    @BindView(R.id.recycleview_limit)
    RecyclerView recycleviewLimit;
    @BindView(R.id.ll_limit_insert_cotnent)
    LinearLayout llLimitInsertCotnent;
    @BindView(R.id.recycleview_limit_city)
    RecyclerView recycleviewLimitCity;
    @BindView(R.id.ll_limit_cotnent)
    LinearLayout llLimitCotnent;
    @BindView(R.id.ll_limit)
    LinearLayout llLimit;
    private OilTypeListAdapter oilTypeListAdapter;
    private View oilTypeHeaderView;
    private View oilBrandBottomView;
    private TabLayout tabLayout;
    private OnLimitChangeListener onLimitChangeListener;
    private List<BrandsListBean> brands_list;
    private List<FuelsListBean> fuels_list;
    private List<BrandsListBean> other_list;
    private List<BrandsListBean> station_type_list;
    private OilBrandListAdapter oilBrandListAdapter;
    private CityListAdapter cityListAdapter;
    private CityListAdapter cityListAdapterChild;
    private CityListBean cityModel;
    private int mProvincePosition;
    private String station_brand = "";
    private String fuel_no = "";
    private String other_id = "";
    private String station_type = "";
    private String area_code = "";
    private String mOilType = "";
    private int uiType = 0;//0油站列表筛选 1 搜索油站结果筛选 2地图筛选ui
    private Context mContext;
    private TabLayout.Tab mTab;
    private LinearLayoutManager defaultlLayoutManager;
    private GridLayoutManager oilBrandLayoutManager;
    private RecyclerView.ItemDecoration oilBrandItemDecoration;
    private LayoutParams oilBrandLayoutParams;
    private View oilBrandHeadView;
    private int insertCotnentViewPadding;


    public OilFilterLimitLayout(Context context) {
        super(context);
        initView(context);
    }

    public OilFilterLimitLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initView(context);
    }

    public OilFilterLimitLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public OilFilterLimitLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView(context);
    }

    public void setTab(TabLayout tabLayout) {
        this.tabLayout = tabLayout;
        this.area_code = "";
    }

    /**
     * //0油站列表筛选 1 搜索油站结果筛选 2地图筛选ui
     * @param uitype
     */
    public void setUiType(int uitype) {
        this.uiType = uitype;
        if (uitype == 1) {
            if (oilTypeListAdapter != null && oilTypeHeaderView != null) {
                oilTypeListAdapter.removeHeaderView(oilTypeHeaderView);
            }
        }
    }

    public void setData(StationLimitBean stationLimitBean) {
        fuels_list = stationLimitBean.getFuels_list();
        brands_list = stationLimitBean.getBrands_list();
        other_list = stationLimitBean.getOther_list();
        station_type_list = stationLimitBean.getStation_type_list();
        if (other_list != null && other_list.size() > 0) {
            other_list.get(0).setChoice(true);
        }
        if (brands_list != null && brands_list.size() > 0) {
            brands_list.get(0).setChoice(true);
        }
        if (station_type_list != null && station_type_list.size() > 0) {
            station_type_list.get(0).setChoice(true);
        }
        if (!TextUtils.isEmpty(SharePreUtils.getDefaultFuel())) {
            String[] type_fuelno = SharePreUtils.getDefaultFuel().split("&");
            fuel_no = type_fuelno[1];
            TextView tv_oil_type = oilTypeHeaderView.findViewById(R.id.tv_oil_type);
            tv_oil_type.setText(type_fuelno[0] + "");
            if (type_fuelno[0].contains("天然气") || type_fuelno[0].toLowerCase().contains("lng")) {
                mOilType = "4";
            }
        }
        llLimit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLimit();
            }
        });
        llLimitCotnent.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLimit();
            }
        });
    }

    public void setLimitListener(OnLimitChangeListener onLimitChangeListener) {
        this.onLimitChangeListener = onLimitChangeListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
//        return true;
        return super.onTouchEvent(event);
    }

    private void initView(Context context) {
        mContext = context;
        View inflate = View.inflate(context, R.layout.oilfilter_limit_layout, null);
        inflate.setLayoutParams(new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        this.addView(inflate);
        ButterKnife.bind(this, inflate);

        insertCotnentViewPadding = ScreenUtil.dip2px( 6.5f);
        oilBrandLayoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        oilBrandItemDecoration = new GridItemDecoration(13);
        oilBrandLayoutManager = new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false);
        defaultlLayoutManager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false);
        recycleviewLimit.setLayoutManager(defaultlLayoutManager);
        oilTypeListAdapter = new OilTypeListAdapter(R.layout.station_oil_type_item_layout);
        oilTypeHeaderView = View.inflate(context, R.layout.oil_type_header_view_layout, null);
        TextView tv_set_oil_type = oilTypeHeaderView.findViewById(R.id.tv_set_oil_type);
        TextView tv_oil_type = oilTypeHeaderView.findViewById(R.id.tv_oil_type);
        tv_set_oil_type.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });
        if (!TextUtils.isEmpty(SharePreUtils.getDefaultFuel())) {
            String[] type_fuelno = SharePreUtils.getDefaultFuel().split("&");
            tv_oil_type.setText(type_fuelno[0]);
            fuel_no = "2001";
        }
        llLimit = inflate.findViewById(R.id.ll_limit);
        llLimit.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                hideLimit();
            }
        });
//        oilBrandHeadView = View.inflate(mContext, R.layout.oil_brand_head_view_layout, null);
//        oilBrandBottomView = View.inflate(context, R.layout.oil_brand_bottom_view_layout, null);
//        TextView tv_brand_reset = oilBrandBottomView.findViewById(R.id.tv_brand_reset); // 重置
//        TextView tv_brand_confirm = oilBrandBottomView.findViewById(R.id.tv_brand_confirm); // 确定
//        tv_brand_reset.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                for (int i = 0; i < oilBrandListAdapter.getData().size(); i++) {
//                    oilBrandListAdapter.getData().get(i).setChoice(i == 0);
//                }
//                station_brand = "";
//                oilBrandListAdapter.notifyDataSetChanged();
//                TextView textView = tabLayout.getTabAt(2).getCustomView().findViewById(R.id.tv_title);
//                textView.setText("全部品牌");
//                textView.setSelected(true);
//            }
//        });
//        tv_brand_confirm.setOnClickListener(new OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                station_brand = "";
//                String title = "";
//                if (brands_list != null) {
//                    for (int i = 0; i < brands_list.size(); i++) {
//                        if (brands_list.get(i).isChoice()) {
//                            station_brand += ("," + brands_list.get(i).getOther_id()) + "";
//                            title += ("," + brands_list.get(i).getName()) + "";
//                        }
//                    }
//                    station_brand = station_brand.replaceFirst(",", "");
//                    title = title.replaceFirst(",", "");
//                    TextView textView = tabLayout.getTabAt(2).getCustomView().findViewById(R.id.tv_title);
//                    textView.setText(TextUtils.isEmpty(title) ? "品牌" : title);
//                    textView.setSelected(false);
//                    closeAnimation(llLimitCotnent);
//                    if (onLimitChangeListener != null) {
//                        onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
//                    }
//                }
//            }
//        });
//        oilTypeListAdapter.setHeaderView(oilTypeHeaderView);
        oilBrandListAdapter = new OilBrandListAdapter(R.layout.station_oil_brand_item_layout);
        cityListAdapter = new CityListAdapter(R.layout.station_oil_brand_item_layout);
        cityListAdapter.setType(1);
        cityListAdapterChild = new CityListAdapter(R.layout.station_oil_brand_item_layout);
        cityListAdapterChild.setType(2);
        recycleviewLimitCity.setLayoutManager(new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false));
        recycleviewLimitCity.setAdapter(cityListAdapterChild);
        String json_str = AssetUtil.getText(context, "city1.text");
        Gson gson = new Gson();
        cityModel = gson.fromJson(json_str, new TypeToken<CityListBean>() {
        }.getType());
        addListener();
    }

    private void addListener() {
        oilTypeListAdapter.setOnDetailsItemClickListener(new OilTypeListAdapter.SetOnDetailsItemClick() {
            @Override
            public void ClickDetailsItem(int parentPosition, int position, FuelsBean fuelsBean) {
                closeAnimation(llLimitCotnent);
                TextView textView = tabLayout.getTabAt(1).getCustomView().findViewById(R.id.tv_title);
                textView.setText(fuels_list.get(parentPosition).getFuel_info().get(position).getName() + fuels_list.get(parentPosition).getName());
                textView.setSelected(false);
                mOilType = fuels_list.get(parentPosition).getType();
                fuel_no = fuels_list.get(parentPosition).getFuel_info().get(position).getFuel_no();
                for (int i = 0; i < fuels_list.size(); i++) {
                    FuelsListBean fuelsListBean = fuels_list.get(i);
                    List<FuelsBean> fuel_info = fuelsListBean.getFuel_info();
                    if (i == parentPosition) {
                        for (int j = 0; j < fuel_info.size(); j++) {
                            fuel_info.get(j).setChoice(j == position);
                        }
                    } else {
                        for (int j = 0; j < fuel_info.size(); j++) {
                            fuel_info.get(j).setChoice(false);
                        }
                    }
                }
                if (onLimitChangeListener != null) {
                    onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
                }
            }
        });
        oilBrandListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                for (int i = 0; i < oilBrandListAdapter.getData().size(); i++) {
                    if (tabLayout.getSelectedTabPosition() == 2) {
                        if (i == position) {
                            oilBrandListAdapter.getData().get(i).setChoice(!oilBrandListAdapter.getData().get(i).isChoice());
                        }
                    } else {
                        oilBrandListAdapter.getData().get(i).setChoice(i == position);
                    }
                }
                oilBrandListAdapter.notifyDataSetChanged();
                if (tabLayout.getSelectedTabPosition() == 2) {
                    if (position == 0 && oilBrandListAdapter.getData().get(0).isChoice()) {
                        for (int i = 0; i < oilBrandListAdapter.getData().size(); i++) {
                            if (i != 0) {
                                oilBrandListAdapter.getData().get(i).setChoice(false);
                            }
                        }
                        TextView textView = tabLayout.getTabAt(2).getCustomView().findViewById(R.id.tv_title);
                        textView.setText(brands_list.get(position).getName());
                        textView.setSelected(false);
                        station_brand = brands_list.get(position).getOther_id();
                        closeAnimation(llLimitCotnent);
                        if (onLimitChangeListener != null) {
                            onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
                        }
                    } else {
                        oilBrandListAdapter.getData().get(0).setChoice(false);
                    }
                } else if (tabLayout.getSelectedTabPosition() == 4) {
                    closeAnimation(llLimitCotnent);
                    TextView textView = tabLayout.getTabAt(4).getCustomView().findViewById(R.id.tv_title);
                    textView.setText(other_list.get(position).getName());
                    textView.setSelected(false);
                    other_id = other_list.get(position).getOther_id();
                    if (onLimitChangeListener != null) {
                        onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
                    }

                } else if (tabLayout.getSelectedTabPosition() == 3) {
                    closeAnimation(llLimitCotnent);
                    TextView textView = tabLayout.getTabAt(3).getCustomView().findViewById(R.id.tv_title);
                    textView.setText(station_type_list.get(position).getName());
                    textView.setSelected(false);
                    station_type = station_type_list.get(position).getValue();
                    if (onLimitChangeListener != null) {
                        onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
                    }
                }
            }
        });
        cityListAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                mProvincePosition = position;
                for (int i = 0; i < cityListAdapter.getData().size(); i++) {
                    cityListAdapter.getData().get(i).setChoice(i == position);
                }
                cityListAdapter.notifyDataSetChanged();
                List<StringBean> data = new ArrayList<>();
                for (int i = 0; i < cityModel.city_list().get(position).size(); i++) {
                    StringBean stringBean = new StringBean();
                    stringBean.setName(cityModel.city_list().get(position).get(i));
                    data.add(stringBean);
                }
                cityListAdapterChild.setNewData(data);
            }
        });
        cityListAdapterChild.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("city", "--" + cityModel.city_list().get(mProvincePosition).get(position) + "--code--" + cityModel.address.get(mProvincePosition).getChildren().get(position).getValue());
                TextView textView = tabLayout.getTabAt(0).getCustomView().findViewById(R.id.tv_title);
                textView.setText(cityModel.address.get(mProvincePosition).getChildren().get(position).getText());
                textView.setSelected(false);
                recycleviewLimitCity.setVisibility(View.GONE);
                closeAnimation(llLimitCotnent);
                area_code = cityModel.address.get(mProvincePosition).getChildren().get(position).getValue();
                if (onLimitChangeListener != null) {
                    onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
                }

            }
        });
    }

    public void onTabClick(TabLayout.Tab tab, boolean click) {
        this.mTab = tab;
        if (click) {
            recycleviewLimit.setVisibility(View.VISIBLE);
            starAnimation(llLimitCotnent);
        } else {
            if (llLimit.getVisibility() == View.VISIBLE) {
                closeAnimation(llLimitCotnent);
            } else {
                starAnimation(llLimitCotnent);
            }
            TextView text = mTab.getCustomView().findViewById(R.id.tv_title);
            text.setSelected(!text.isSelected());

        }
        llLimitInsertCotnent.removeView(oilBrandBottomView);
        llLimitInsertCotnent.removeView(oilBrandHeadView);
        recycleviewLimit.removeItemDecoration(oilBrandItemDecoration);
        oilBrandListAdapter.setTabPosition(tabLayout.getSelectedTabPosition());
        if (llLimitInsertCotnent.getPaddingLeft() == insertCotnentViewPadding) {
            llLimitInsertCotnent.setPadding(0, 0, 0, 0);
        }
        switch (tabLayout.getSelectedTabPosition()) {
            case 0:
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzlb_03");
                recycleviewLimitCity.setVisibility(recycleviewLimit.getVisibility() == View.VISIBLE ? View.VISIBLE : View.GONE);
                List<StringBean> data = new ArrayList<>();
                for (int i = 0; i < cityModel.province_list().size(); i++) {
                    StringBean stringBean = new StringBean();
                    stringBean.setName(cityModel.province_list().get(i));
                    if (i == 0) {
                        stringBean.setChoice(true);
                    }
                    data.add(stringBean);
                }
                mProvincePosition = 0;
                cityListAdapter.setNewData(data);
                List<StringBean> data1 = new ArrayList<>();
                for (int i = 0; i < cityModel.city_list().get(0).size(); i++) {
                    StringBean stringBean = new StringBean();
                    stringBean.setName(cityModel.city_list().get(0).get(i));
                    data1.add(stringBean);
                }
                recycleviewLimit.setLayoutManager(defaultlLayoutManager);
                llLimitInsertCotnent.setBackgroundColor(Color.TRANSPARENT);
                cityListAdapterChild.setNewData(data1);
                recycleviewLimit.setAdapter(cityListAdapter);
                break;
            case 1:
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzlb_04");
                recycleviewLimitCity.setVisibility(View.GONE);
                recycleviewLimit.setLayoutManager(defaultlLayoutManager);
                llLimitInsertCotnent.setBackgroundColor(Color.TRANSPARENT);
                recycleviewLimit.setAdapter(oilTypeListAdapter);
                oilTypeListAdapter.setNewData(fuels_list);
                break;
            case 2:
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzlb_05");
                recycleviewLimitCity.setVisibility(View.GONE);
                llLimitInsertCotnent.addView(oilBrandHeadView, 0);
                llLimitInsertCotnent.addView(oilBrandBottomView, oilBrandLayoutParams);
                llLimitInsertCotnent.setBackgroundColor(Color.WHITE);
                llLimitInsertCotnent.setPadding(insertCotnentViewPadding, insertCotnentViewPadding, insertCotnentViewPadding, insertCotnentViewPadding);
                recycleviewLimit.addItemDecoration(oilBrandItemDecoration);
                recycleviewLimit.setLayoutManager(oilBrandLayoutManager);
                recycleviewLimit.setAdapter(oilBrandListAdapter);
                oilBrandListAdapter.setNewData(brands_list);
                llLimitInsertCotnent.requestLayout();
                break;
            case 3:
                if (uiType == 0) {
                    MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzlb_10");
                } else if (uiType == 2) {
                    MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzdt_10");
                }
                recycleviewLimitCity.setVisibility(View.GONE);
                llLimitInsertCotnent.setBackgroundColor(Color.TRANSPARENT);
                recycleviewLimit.setLayoutManager(defaultlLayoutManager);
                recycleviewLimit.setAdapter(oilBrandListAdapter);
                oilBrandListAdapter.setNewData(station_type_list);
                break;
            case 4:
                MobclickAgent.onEvent(BaseAppCache.getContext(), "yz_yzlb_06");
                recycleviewLimitCity.setVisibility(View.GONE);
                recycleviewLimit.setLayoutManager(defaultlLayoutManager);
                llLimitInsertCotnent.setBackgroundColor(Color.TRANSPARENT);
                recycleviewLimit.setAdapter(oilBrandListAdapter);
                oilBrandListAdapter.setNewData(other_list);
                break;
        }
    }

    public void starAnimation(View view) {
        recycleviewLimit.setVisibility(View.VISIBLE);
        llLimit.setAlpha(1f);
        llLimit.setVisibility(VISIBLE);
        if (onUIGoneListener != null) {
            onUIGoneListener.onVisible();
        }
        ObjectAnimator give = ObjectAnimator.ofFloat(view, "translationY", -400, -300, -200, -100, 0);
        give.setDuration(250);
        give.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
//                mTab.getCustomView().findViewById(R.id.tv_title).setSelected(llLimit.getVisibility() == VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        give.start();
    }

    public void closeAnimation(View view) {
        llLimit.setAlpha(1f);
        AnimatorSet animatorSet = new AnimatorSet();
        ObjectAnimator alpha = ObjectAnimator.ofFloat(llLimit, "alpha", 1f, 0.8f, 0.5f, 0.3f, 0f);
        ObjectAnimator trans = ObjectAnimator.ofFloat(view, "translationY", 0, -100, -200, -300, -400);
        animatorSet.playTogether(alpha, trans);
        animatorSet.setDuration(250);
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                llLimit.setVisibility(GONE);
                recycleviewLimit.setVisibility(View.GONE);
                if (onUIGoneListener != null) {
                    onUIGoneListener.onGone();
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
        animatorSet.start();
    }

    @OnClick({R.id.ll_limit, R.id.ll_limit_cotnent})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.ll_limit:
            case R.id.ll_limit_cotnent:
                hideLimit();
                break;
        }
    }

    private void hideLimit() {
        if (null != tabLayout.getTabAt(tabLayout.getSelectedTabPosition())) {
            tabLayout.getTabAt(tabLayout.getSelectedTabPosition()).getCustomView().findViewById(R.id.tv_title).setSelected(false);
        }
        closeAnimation(llLimitCotnent);
    }

//    public void setDefaultOilEvent(SetDefaultOilEvent setDefaultOilEvent) {
//        TextView tv_oil_type = oilTypeHeaderView.findViewById(R.id.tv_oil_type);
//        tv_oil_type.setText(setDefaultOilEvent.getOilType());
//        fuel_no = setDefaultOilEvent.getFuel_no();
//        mOilType = SharePreUtils.getDefaultFuelType();
//
//        for (int i = 0; i < fuels_list.size(); i++) {
//            FuelsListBean fuelsListBean = fuels_list.get(i);
//            List<FuelsBean> fuel_info = fuelsListBean.getFuel_info();
//            if (mOilType.equals(fuelsListBean.getType())) {
//                for (int j = 0; j < fuel_info.size(); j++) {
//                    fuel_info.get(j).setChoice(fuel_info.get(j).getFuel_no().equals(fuel_no));
//                }
//            } else {
//                for (int j = 0; j < fuel_info.size(); j++) {
//                    fuel_info.get(j).setChoice(false);
//                }
//            }
//        }
//        oilTypeListAdapter.notifyDataSetChanged();
//        if (onLimitChangeListener != null) {
//            onLimitChangeListener.limitChange(area_code, fuel_no, station_brand, station_type, other_id, mOilType);
//        }
//    }

    public void hideView() {
        hideLimit();
    }

    public void onDestroy() {
        if (null != llLimitCotnent) {
            llLimitCotnent.clearAnimation();
        }
        if (null != llLimit) {
            llLimit.clearAnimation();
        }
    }

    public interface OnLimitChangeListener {
        void limitChange(String area_code, String fuel_no, String station_brand, String station_type, String other_id, String mOilType);
    }


    private OnUIGoneListener onUIGoneListener;

    public void setOnUIGoneListener(OnUIGoneListener onUIGoneListener) {
        this.onUIGoneListener = onUIGoneListener;
    }

    /**
     * 被隐藏的监听
     */
    public interface OnUIGoneListener {
        void onVisible();
        void onGone();
    }
}
