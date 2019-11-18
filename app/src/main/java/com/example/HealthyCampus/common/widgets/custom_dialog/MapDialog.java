package com.example.HealthyCampus.common.widgets.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import static com.umeng.socialize.Config.dialog;

public class MapDialog extends Dialog {

    public MapDialog(@NonNull Context context) {
        super(context);
    }

    public MapDialog(Context context, View layout, int style) {

        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;


//        Display display = params.getDefaultDisplay();
//
//        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
//
//        lp.width = (int)(display.getWidth() * 0.8); //设置宽度

        window.setAttributes(params);
    }


    public MapDialog(Context context, int width, int height, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;
        params.width = width;
        params.height = height;
        window.setAttributes(params);
    }
}
