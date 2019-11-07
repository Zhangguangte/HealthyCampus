package com.example.HealthyCampus.common.widgets.tab;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * OK
 */
public class TabBehavior extends CoordinatorLayout.Behavior<TabLayout> {
    public TabBehavior(Context context, AttributeSet attrs) {
        super(context, attrs);
    }



    @Override
    public boolean layoutDependsOn(@NonNull CoordinatorLayout parent, @NonNull TabLayout child, @NonNull View dependency) {
        return dependency instanceof AppBarLayout;
    }

    @Override
    public boolean onDependentViewChanged(@NonNull CoordinatorLayout parent, @NonNull TabLayout child, @NonNull View dependency) {
        float translationY = Math.abs(dependency.getTop());
        child.setTranslationY(translationY);

//        Log.e("TabBehavior123456","parent"+parent);
//        Log.e("TabBehavior123456","child"+child);
//        Log.e("TabBehavior123456","dependency"+dependency);
//        Log.e("TabBehavior123456","dependency.getTop()"+dependency.getTop());
//        Log.e("TabBehavior123456","translationY"+translationY);
//        Log.e("TabBehavior123456","******************************************");

        return true;
    }
}
