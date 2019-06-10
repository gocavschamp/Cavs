package com.example.myapp.mian.adapter;


import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.myapp.homepage.HelloGroundFragment;
import com.example.myapp.homepage.MessgaeFragment;
import com.example.myapp.homepage.MyCenterFragment;


/**
 * 说明：首页tab适配 <br>
 * 时间：2017/7/11 23:46<br>
 * 修改记录： <br>
 */

public class ViewPagerAdapterMain extends FragmentStatePagerAdapter {
    public final int COUNT = 3;

    public ViewPagerAdapterMain(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0://首页
                return MyCenterFragment.newInstance();
            case 1://社区
                return HelloGroundFragment.newInstance();
            case 2://消息
                return MessgaeFragment.newInstance();
        }
        return null;
    }

    @Override
    public int getCount() {
        return COUNT;
    }
}
