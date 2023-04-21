package com.nucarf.exoplayerlibrary.ui;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.nucarf.exoplayerlibrary.R;
import com.nucarf.exoplayerlibrary.util.GoodsAdapter;
import com.nucarf.exoplayerlibrary.util.ScreenUtils;
import com.nucarf.exoplayerlibrary.verplayer.GoodsListener;

import java.util.ArrayList;


/**
 * create time on on 2017/5/11.
 */
public class TvGoodsLayout extends FrameLayout implements View.OnClickListener {
    private final String TAG = TvGoodsLayout.class.getSimpleName();

    private LinearLayout root;
    private LinearLayout recyRootLayout;
    private ImageView goodsgirl;
    private ImageView shop_iv;
    private ImageView close;
    private RecyclerView mGoodsRecycleView;
    private ArrayList<TaobaoGoods> mGoodsList;
    private GoodsListener listener;
    //是否为竖屏
    private boolean mIsportrait = true;


    public TvGoodsLayout(Context context) {
        super(context);
        initView();
    }

    public TvGoodsLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public TvGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public TvGoodsLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        initView();
    }

    private void initView() {
        LayoutInflater.from(getContext()).inflate(R.layout.goods_layout, this);
        root = (LinearLayout) findViewById(R.id.goods_root);
        recyRootLayout = (LinearLayout) findViewById(R.id.recyview_root_layout);
        goodsgirl = (ImageView) root.findViewById(R.id.goods_girl_iv);
        close = (ImageView) root.findViewById(R.id.goods_close);
        shop_iv = (ImageView) findViewById(R.id.shop_iv);
        mGoodsRecycleView = (RecyclerView) root.findViewById(R.id.goods_recycleview);
        close.setOnClickListener(this);
        shop_iv.setOnClickListener(this);
        root.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.goods_close) {
            if (listener != null)
                listener.onCloseGoodsView();
            goodsgirl.setImageResource(R.mipmap.push_girl);
            hide();
        } else if (i == R.id.shop_iv) {
            show();
        } else if (i == R.id.goods_root) {
            Log.e("R.id.goods_root", "R.id.goods_root");

        }
    }

    public void setGoodsListener(GoodsListener listener) {
        this.listener = listener;
    }

    public void setmGoodsList(ArrayList<TaobaoGoods> mGoodsList) {
        this.mGoodsList = mGoodsList;
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mGoodsRecycleView.setLayoutManager(layoutManager);
        GoodsAdapter adapter = new GoodsAdapter(getContext(), listener);
        mGoodsRecycleView.setAdapter(adapter);
        adapter.setList(mGoodsList);
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        root.measure(w, h);
    }

    public void setRotaionScreen(boolean pIsportrait) {
        shop_iv.setVisibility(VISIBLE);
        root.setVisibility(GONE);
        mIsportrait = pIsportrait;

    }

    public void releaseData() {
        Log.e(TAG, "releaseAD");
        root.setVisibility(View.GONE);
        if (mGoodsList != null) {
            mGoodsList.clear();
            mGoodsList = null;
        }
    }

    public void finishADLayout() {
        releaseData();
        listener = null;
    }

    public void show() {
        root.setVisibility(VISIBLE);
        int ScreenWidth = ScreenUtils.getScreenWidth(getContext());
        getContext().getSharedPreferences("starshow", Context.MODE_PRIVATE).edit().putInt("goody", ScreenUtils.dip2px(getContext(), 90)).apply();
        Log.e(TAG, "showGoods" + root.getMeasuredWidth() + "===" + root.getLeft() + "===" + ScreenUtils.getScreenWidth(getContext()));
        shop_iv.setVisibility(GONE);
        goodsgirl.setImageResource(R.mipmap.go_girl);
        PropertyValuesHolder pvhtLanternrX;
        if (root.getMeasuredWidth() > ScreenWidth) {
            pvhtLanternrX = PropertyValuesHolder.ofFloat("translationX", ScreenWidth, 0);
        } else {
            pvhtLanternrX = PropertyValuesHolder.ofFloat("translationX", root.getMeasuredWidth(), 0);
        }
        ObjectAnimator animator_show = ObjectAnimator.ofPropertyValuesHolder(root, pvhtLanternrX);
        animator_show.setInterpolator(new LinearInterpolator());
        animator_show.setDuration(2500);
        animator_show.start();
    }

    public void hide() {
        getContext().getSharedPreferences("starshow", Context.MODE_PRIVATE).edit().putInt("goody", 0).apply();
        int ScreenWidth = ScreenUtils.getScreenWidth(getContext());
        Log.e(TAG, "hideGoods" + root.getMeasuredWidth() + "==" + root.getLeft());
        goodsgirl.setImageResource(R.mipmap.push_girl);
        PropertyValuesHolder pvhtLanternrX;
        if (root.getMeasuredWidth() > ScreenWidth) {
            pvhtLanternrX = PropertyValuesHolder.ofFloat("translationX", 0, ScreenWidth + ScreenUtils.dip2px(getContext(), 70));
        } else {
            pvhtLanternrX = PropertyValuesHolder.ofFloat("translationX", 0, root.getMeasuredWidth());
        }
        ObjectAnimator animator_dissmiss = ObjectAnimator.ofPropertyValuesHolder(root, pvhtLanternrX);
        animator_dissmiss.setInterpolator(new LinearInterpolator());
        animator_dissmiss.setDuration(2500);
        animator_dissmiss.start();
        animator_dissmiss.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                shop_iv.setVisibility(VISIBLE);
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });

    }

}
