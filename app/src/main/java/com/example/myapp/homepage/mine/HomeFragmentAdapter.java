package com.example.myapp.homepage.mine;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapp.homepage.MineFragment;
import com.example.myapp.homepage.MyCenterTestFragment;

/**
 * 优惠站点Fragment 适配器
 * 
 * Created by wuguangxin on 2021/7/9.
 */
public class HomeFragmentAdapter extends FragmentStatePagerAdapter {
    private MineFragment fragment;

    public HomeFragmentAdapter(FragmentManager fm, MineFragment fragment) {
        super(fm);
        // this.fragment = DiscountStationListFragment.newInstance();
        this.fragment = fragment;
    }

    public void setFragment(MineFragment fragment) {
        this.fragment = fragment;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Fragment getItem(int position) {
        return fragment;
    }
}