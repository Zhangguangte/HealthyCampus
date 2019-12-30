package com.example.HealthyCampus.common.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.module.Find.News.list.HealthNewsListFragment;

public class HealthNewsPagerAdapter extends FragmentStatePagerAdapter {

    private final String[] TITLES;
    private Fragment[] fragments;

    public HealthNewsPagerAdapter(FragmentManager fm, Context context) {
        super(fm);
        TITLES = context.getResources().getStringArray(R.array.health_news);
        fragments = new Fragment[TITLES.length];
    }

    @Override
    public Fragment getItem(int position) {
        if (fragments[position] == null) {
            Fragment newfragment = new HealthNewsListFragment();
            Bundle data = new Bundle();
            data.putInt("id", position);
            newfragment.setArguments(data);
            addFrag(newfragment, position);
        }
        return fragments[position];
    }


    public void addFrag(Fragment fragment, int position) {
        fragments[position] = fragment;
    }

    @Override
    public int getCount() {
        return TITLES.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return TITLES[position];
    }
}
