package com.example.myapp.homepage.homedemo.dragscallview;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.callback.ItemDragAndSwipeCallback;
import com.chad.library.adapter.base.listener.OnItemDragListener;
import com.example.myapp.R;
import com.google.android.material.appbar.AppBarLayout;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.appbar.TabScrimHelper;
import com.google.android.material.tabs.TabLayout;
import com.nucarf.base.ui.BaseActivity;
import com.nucarf.base.widget.ViewPagerSlide;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DragAndScallActivity extends BaseActivity {

    @BindView(R.id.iv_head_bg)
    ImageView ivHeadBg;
    @BindView(R.id.iv_user_head)
    ImageView ivUserHead;
    @BindView(R.id.follow_iv)
    TextView followIv;
    @BindView(R.id.head_relayout)
    LinearLayout headRelayout;
    @BindView(R.id.user_name)
    TextView userName;
    @BindView(R.id.center_bg_iv)
    ImageView centerBgIv;
    @BindView(R.id.editor_or_more_iv)
    ImageView editorOrMoreIv;
    @BindView(R.id.user_id)
    TextView userId;
    @BindView(R.id.tv_follow_count_st)
    TextView tvFollowCountSt;
    @BindView(R.id.follow_count_tv)
    TextView followCountTv;
    @BindView(R.id.center_follow_layout)
    LinearLayout centerFollowLayout;
    @BindView(R.id.tv_fans_count_st)
    TextView tvFansCountSt;
    @BindView(R.id.fans_count_tv)
    TextView fansCountTv;
    @BindView(R.id.center_fans_layout)
    LinearLayout centerFansLayout;
    @BindView(R.id.user_intro_tv)
    TextView userIntroTv;
    @BindView(R.id.user_editor_layout)
    LinearLayout userEditorLayout;
    @BindView(R.id.bg_layout)
    RelativeLayout bgLayout;
    @BindView(R.id.head_root_layout)
    RelativeLayout headRootLayout;
    @BindView(R.id.iv_back)
    TextView ivBack;
    @BindView(R.id.m_title)
    TextView mTitle;
    @BindView(R.id.tv_follow)
    TextView tvFollow;
    @BindView(R.id.rl_toolbar)
    RelativeLayout rlToolbar;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.collapsing_toolbar)
    CollapsingToolbarLayout collapsingToolbar;
    @BindView(R.id.appbar)
    AppBarLayout appbar;
    @BindView(R.id.sliding_tabs)
    TabLayout slidingTabs;
    @BindView(R.id.viewPager)
    ViewPagerSlide viewPager;
    @BindView(R.id.viewpage_layout)
    RelativeLayout viewpageLayout;
    @BindView(R.id.main_content)
    CoordinatorLayout mainContent;
    private DragAdapter mAdapter;
    private SmartPagerAdapter mPageAdapter;

    public enum Item {
        NestedInner(R.string.item_example_pager_left, SmartFragment.class),
        NestedOuter(R.string.item_example_pager_right, SmartFragment.class),
        ;
        public int nameId;
        public Class<? extends Fragment> clazz;

        Item(@StringRes int nameId, Class<? extends Fragment> clazz) {
            this.nameId = nameId;
            this.clazz = clazz;
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_drag_and_scall);
        ButterKnife.bind(this);
//        titlelayout.setTitleText("拖拽布局");

    }

    @Override
    protected void initData() {

        viewPager.setAdapter(mPageAdapter = new SmartPagerAdapter(Item.values()));
        slidingTabs.setupWithViewPager(viewPager, true);
        TabScrimHelper tabScrimHelper = new TabScrimHelper(slidingTabs, collapsingToolbar);
        appbar.addOnOffsetChangedListener(tabScrimHelper);
    }


    private class SmartPagerAdapter extends FragmentStatePagerAdapter {

        private final Item[] items;
        private final SmartFragment[] fragments;

        SmartPagerAdapter(Item... items) {
            super(DragAndScallActivity.this.getSupportFragmentManager());
            this.items = items;
            this.fragments = new SmartFragment[items.length];
        }

        @Override
        public int getCount() {
            return items.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return getString(items[position].nameId);
        }

        @Override
        public Fragment getItem(int position) {
            if (fragments[position] == null) {
                fragments[position] = new SmartFragment();
            }
            return fragments[position];
        }
    }

    public static class SmartFragment extends Fragment {

        private RecyclerView mRecyclerView;
        private DragAdapter mAdapter;

        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            return new RecyclerView(inflater.getContext());
        }

        @Override
        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            mRecyclerView = (RecyclerView) view;
            OnItemDragListener onItemDragListener = new OnItemDragListener() {
                @Override
                public void onItemDragStart(RecyclerView.ViewHolder viewHolder, int pos) {
                }

                @Override
                public void onItemDragMoving(RecyclerView.ViewHolder source, int from, RecyclerView.ViewHolder target, int to) {
                }

                @Override
                public void onItemDragEnd(RecyclerView.ViewHolder viewHolder, int pos) {
                }
            };
            mAdapter = new DragAdapter(R.layout.dragelayout_item, initData());
            ItemDragAndSwipeCallback itemDragAndSwipeCallback = new ItemDragAndSwipeCallback(mAdapter);
            ItemTouchHelper itemTouchHelper = new ItemTouchHelper(itemDragAndSwipeCallback);
            itemTouchHelper.attachToRecyclerView(mRecyclerView);
            // 开启拖拽
            mAdapter.enableDragItem(itemTouchHelper, R.id.ll_content, true);
            mAdapter.setOnItemDragListener(onItemDragListener);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView.addItemDecoration(new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL));
            mRecyclerView.setAdapter(mAdapter);
        }

        private List<String> initData() {
            List<String> strings = new ArrayList<>();
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            strings.add("");
            return strings;
        }
    }
}