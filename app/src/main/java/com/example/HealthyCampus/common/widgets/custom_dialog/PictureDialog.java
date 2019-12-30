package com.example.HealthyCampus.common.widgets.custom_dialog;

import android.app.Dialog;
import android.content.Context;
import android.support.annotation.NonNull;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.example.HealthyCampus.R;

public class PictureDialog extends Dialog {

    public PictureDialog(@NonNull Context context) {
        super(context);
    }

    public PictureDialog(Context context, View layout, int style) {

        super(context, style);
        setContentView(layout);
        Window window = getWindow();
        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();
        params.gravity = Gravity.CENTER;

        WindowManager m = window.getWindowManager();
        Display d = m.getDefaultDisplay(); // 获取屏幕宽、高用

        params.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.8
        params.height = (int) (d.getHeight() * 0.8);

        window.setAttributes(params);
//        window.setWindowAnimations(R.style.DialogAnimations);
    }


    public PictureDialog(Context context, int width, int height, View layout, int style) {

        super(context, style);

        setContentView(layout);

        Window window = getWindow();

        assert window != null;
        WindowManager.LayoutParams params = window.getAttributes();

        params.gravity = Gravity.CENTER;
        params.width = width;
        params.height = height;
        window.setAttributes(params);
    }

}
