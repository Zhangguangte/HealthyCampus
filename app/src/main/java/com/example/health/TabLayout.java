package com.example.health;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;

public class TabLayout extends LinearLayout implements View.OnClickListener {


    public TabLayout(Context context) {
        super(context);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public TabLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void onClick(View v) {

    }

    private void setUpView()
    {
        setOrientation(HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.white));
    }


    private void setUpData(ArrayList<Tab> tabs, OnTabClickListener listener) {


    }



    public interface OnTabClickListener {
        void onTabClick(Tab tab);
    }

    public static class Tab{
        public int imgResId;
        public int labelResId;
        public Class<? extends ITabFragment> targetFragmentClz;


        public Tab(Class<? extends ITabFragment> targetFragmentClz) {
            this.targetFragmentClz = targetFragmentClz;
        }

        public Tab(int imgResId, int labelResId, Class<? extends ITabFragment> targetFragmentClz) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
            this.targetFragmentClz = targetFragmentClz;
        }
    }
}
