package com.example.HealthyCampus.common.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.TypedValue;

public class DensityUtil {
    @SuppressLint("StaticFieldLeak")
    private static DensityUtil instance;
    @SuppressLint("StaticFieldLeak")
    private static Context context;

    public static DensityUtil getInstance() {
        if (instance == null) {
            instance = getInstance();
        }
        return instance;
    }

    public DensityUtil(Context context) {
        DensityUtil.context = context;
    }

    /**
     * Dp转PX
     */
    public static int getDip2Px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, size, context.getResources().getDisplayMetrics());
    }

    public static int dip2Px(int dip,Context context) {
        float density = context.getApplicationContext().getResources().getDisplayMetrics().density;
        return (int) (dip * density + 0.5f);
    }

}
