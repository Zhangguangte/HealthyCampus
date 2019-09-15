package com.example.HealthyCampus.common.widgets.tab;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.HealthyCampus.R;
import com.example.HealthyCampus.framework.ITabFragment;

import java.util.ArrayList;

public class TabLayout extends LinearLayout implements View.OnClickListener {
    private ArrayList<Tab> tabs;
    private OnTabClickListener listener;
    private View selectView;
    private int tabCount;

    public TabLayout(Context context) {
        super(context);
        setUpView();
    }


    public TabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        setUpView();
    }

    public TabLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setUpView();
    }

    private void setUpView() {
        setOrientation(HORIZONTAL);
        setBackgroundColor(getResources().getColor(R.color.white));
    }

    public void setUpData(ArrayList<Tab> tabs, OnTabClickListener listener) {
        this.tabs = tabs;
        this.listener = listener;

        if (tabs != null && tabs.size() > 0) {
            tabCount = tabs.size();
            TabView mTabView;
            float density = getContext().getResources().getDisplayMetrics().density;
            LayoutParams params = new LayoutParams(0, (int) (50 * density));
            params.gravity = Gravity.CENTER;
            params.weight = 1;
            for (int i = 0; i < tabs.size(); i++) {
                mTabView = new TabView(getContext());
                mTabView.setTag(tabs.get(i));
                mTabView.setUpData(tabs.get(i));
                mTabView.setOnClickListener(this);
                addView(mTabView, params);
            }
        } else {
            throw new IllegalArgumentException("tabs can't be empty");
        }
    }

    public void setCurrentTab(int i) {
        if (i < tabCount && i >= 0) {
            View view = getChildAt(i);
            onClick(view);
        }
    }

    @Override
    public void onClick(View v) {
        if (selectView != v) {
            listener.onTabClick((Tab) v.getTag());

            int childCount = getChildCount();

            for (int i = 0; i < childCount; i++) {
                View child = getChildAt(i);
                if (child == v) {
                    v.setSelected(true);
                    setTintColor(child, R.color.colorPrimary);
                } else {
                    setTintColor(child, R.color.icons_tint_normal);
                }
            }

            if (selectView != null) {
                selectView.setSelected(false);
            }
            selectView = v;
        }
    }

    private void setTintColor(View view, int colorId) {
        ImageView img = view.findViewById(R.id.mTabImg);
        if (img != null) {
            Drawable originalDrawable = img.getDrawable();
            Drawable wrappedDrawable = tintDrawable(originalDrawable, ColorStateList.valueOf(getResources().getColor(colorId)));
            img.setBackgroundDrawable(wrappedDrawable);
        }

        TextView txt = view.findViewById(R.id.mTabTxt);
        if (txt != null) {
            txt.setTextColor(ColorStateList.valueOf(getResources().getColor(colorId)));
        }
    }


    private Drawable tintDrawable(Drawable drawable, ColorStateList colors) {
        final Drawable wrappedDrawable = DrawableCompat.wrap(drawable);
        DrawableCompat.setTintList(wrappedDrawable, colors);
        return wrappedDrawable;
    }

    public interface OnTabClickListener {
        void onTabClick(Tab tab);
    }

    public class TabView extends LinearLayout {
        private ImageView mTabImg;
        private TextView mTabTxt;

        public TabView(Context context) {
            super(context);
            setUpView();
        }

        public TabView(Context context, AttributeSet attrs) {
            super(context, attrs);
            setUpView();
        }

        public TabView(Context context, AttributeSet attrs, int defStyleAttr) {
            super(context, attrs, defStyleAttr);
            setUpView();
        }

        private void setUpView() {
            LayoutInflater.from(getContext()).inflate(R.layout.widget_tab_view, this, true);
            setOrientation(VERTICAL);
            setGravity(Gravity.CENTER);
            setBackgroundResource(R.drawable.md_selector_transparent);
            mTabImg = findViewById(R.id.mTabImg);
            mTabTxt = findViewById(R.id.mTabTxt);
        }

        public void setUpData(Tab tab) {
            mTabTxt.setText(tab.labelResId);
            mTabImg.setImageResource(tab.imgResId);
        }
    }

    public static class Tab {
        public int imgResId;
        public int labelResId;
        public Class<? extends ITabFragment> targetFragmentClz;

        public Tab(int imgResId, int labelResId, Class<? extends ITabFragment> targetFragmentClz) {
            this.imgResId = imgResId;
            this.labelResId = labelResId;
            this.targetFragmentClz = targetFragmentClz;
        }
    }
}
