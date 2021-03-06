package com.example.HealthyCampus.common.utils;

import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.TextView;

import com.example.HealthyCampus.R;

public class SnackbarUtil {

    public static final int Info = 1;
    private static final int Confirm = 2;
    private static final int Warning = 3;
    private static final int Alert = 4;


    /**
     * 短显示Snackbar，可选预设类型
     *
     * @param view    控件容器view
     * @param message
     * @param type
     * @return
     */
    public static void shortSnackbar(View view, String message, int type) {
        Snackbar snackbar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT);
        switchType(snackbar, type);
        snackbar.show();
    }

    /**
     * 设置Snackbar背景颜色
     *
     * @param snackbar
     * @param backgroundColor
     */
    private static void setSnackbarColor(Snackbar snackbar, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
    }

    /**
     * 设置Snackbar文字和背景颜色
     *
     * @param snackbar
     * @param messageColor
     * @param backgroundColor
     */
    private static void setSnackbarColor(Snackbar snackbar, int messageColor, int backgroundColor) {
        View view = snackbar.getView();
        view.setBackgroundColor(backgroundColor);
        ((TextView) view.findViewById(R.id.snackbar_text)).setTextColor(messageColor);
    }

    //选择预设类型
    private static void switchType(Snackbar snackbar, int type) {
        int green = 0xFF53DC92;
        int blue = 0xff2196f3;
        int orange = 0xffffc107;
        //ARGB
        int red = 0xfff44336;
        switch (type) {
            case Info:
                setSnackbarColor(snackbar, green);
                break;
            case Confirm:
                setSnackbarColor(snackbar, blue);
                break;
            case Warning:
                setSnackbarColor(snackbar, orange);
                break;
            case Alert:
                setSnackbarColor(snackbar, Color.YELLOW, red);
                break;
        }

    }


}
