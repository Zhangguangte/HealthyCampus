package com.example.HealthyCampus.common.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class ToastUtil {
    private static Toast toast = null;

    public static void show(Context context, String str) {
        if (toast == null) {
            toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
        }
        toast.setText(str);
        toast.show();
    }

    public static void show(final Context context, final int id) {
        Activity activity = (Activity) context;
        activity.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (toast == null) {
                    toast = Toast.makeText(context, "", Toast.LENGTH_SHORT);
                }
                String string = context.getResources().getString(id);
                toast.setText(string);
                toast.show();
            }
        });
    }
}
