package com.example.HealthyCampus.common.utils;

import android.content.Context;
import android.util.TypedValue;

public class DensityUtil {
    private static DensityUtil instance;
    private Context context;

    public static final DensityUtil getInstance(Context context) {
        if (instance == null) {
            instance = getInstance(context);
        }
        return instance;
    }

    public DensityUtil(Context context) {
        this.context = context;
    }

    /**
     * Dp转PX
     *
     * @param size
     * @return
     */
    public int getDip2Px(int size) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,size,context.getResources().getDisplayMetrics());
    }
}
