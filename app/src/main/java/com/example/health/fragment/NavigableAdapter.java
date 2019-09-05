package com.example.health.fragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.health.BaseFragment;
import com.example.health.TabLayout;

import java.util.List;

public class NavigableAdapter extends FragmentPagerAdapter {
    private List<BaseFragment> mFragmentList;

    public NavigableAdapter(FragmentManager fm, List<BaseFragment> mFragmentList) {
        super(fm);
        this.mFragmentList = mFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
        return mFragmentList.get(position);
     //   return null;
    }

    @Override
    public int getCount() {
        return mFragmentList.size();
    }
}
